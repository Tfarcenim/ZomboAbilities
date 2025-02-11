package tfar.zomboabilities.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.client.ModClient;
import tfar.zomboabilities.entity.ai.CloneFollowOwnerGoal;
import tfar.zomboabilities.entity.ai.CloneOwnerHurtByTargetGoal;
import tfar.zomboabilities.entity.ai.CloneOwnerHurtTargetGoal;
import tfar.zomboabilities.init.ModEntityDataSerializers;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ClonePlayerEntity extends PathfinderMob implements OwnableEntity {

    private static final Logger LOGGER = LogUtils.getLogger();

    protected static final EntityDataAccessor<ResolvableProfile> DATA_CLONE_ID = SynchedEntityData.defineId(ClonePlayerEntity.class, ModEntityDataSerializers.RESOLVABLE_PROFILE);

    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(
            ClonePlayerEntity.class, EntityDataSerializers.OPTIONAL_UUID
    );

    private boolean orderedToSit;

    public ClonePlayerEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE,1).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.MAX_HEALTH,20);
    }



    boolean shouldAttack(LivingEntity living) {
        UUID uuid1 = living.getUUID();
        UUID uuid2 = getOwnerUUID();
        return !Objects.equals(uuid1,uuid2);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CLONE_ID,new ResolvableProfile(new GameProfile(Util.NIL_UUID,"")));
        builder.define(DATA_OWNERUUID_ID, Optional.empty());
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        if (!isOwnedBy(player)) {
            return super.interactAt(player, vec, hand);
        }

        ItemStack playerHeld = player.getItemInHand(hand);
        ItemStack cloneHeld = getItemInHand(hand);

        if (player.level().isClientSide) {
            return InteractionResult.CONSUME;
        }

        if (playerHeld.isEmpty()) {
            if (cloneHeld.isEmpty()) {
                return InteractionResult.PASS;
            } else {
                player.setItemInHand(hand,cloneHeld);
                setItemInHand(hand,ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }
        } else {
            setItemInHand(hand,playerHeld);
            player.setItemInHand(hand,cloneHeld);
            return InteractionResult.SUCCESS;

        }

        //return super.interactAt(player, vec, hand);
    }

    public boolean isOwnedBy(LivingEntity entity) {
        return entity == this.getOwner();
    }


    public boolean isOrderedToSit() {
        return this.orderedToSit;
    }
    public void setOrderedToSit(boolean orderedToSit) {
        this.orderedToSit = orderedToSit;
    }

    public boolean unableToMoveToOwner() {
        return this.isOrderedToSit() || this.isPassenger() || this.mayBeLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        return super.mobInteract(player, hand);
    }

    public void setClone(ResolvableProfile clone) {
        entityData.set(DATA_CLONE_ID,clone);
    }

    public ResolvableProfile getClone() {
        return entityData.get(DATA_CLONE_ID);
    }

    public ResourceLocation getSkinTextureLocation() {
        return ModClient.getPlayerSkin(getClone());
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void setRemoved(RemovalReason removalReason) {
        super.setRemoved(removalReason);
        if (removalReason.shouldDestroy()) {
            LivingEntity owner = getOwner();
            if (owner instanceof ServerPlayer player) {
                PlayerDuck playerDuck = PlayerDuck.of(player);
                playerDuck.decrementClone();
            }
            //todo offline players?
        }
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNERUUID_ID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(uuid));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        ResolvableProfile uuid = getClone();
        if (uuid != null) {
            tag.put("clone", ResolvableProfile.CODEC.encodeStart(NbtOps.INSTANCE, uuid).getOrThrow());
        }
        if (getOwnerUUID() != null) {
            tag.putUUID("owner",getOwnerUUID());
        }
        tag.putBoolean("Sitting", this.orderedToSit);

    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("clone")) {
            ResolvableProfile.CODEC
                    .parse(NbtOps.INSTANCE, tag.get("clone"))
                    .resultOrPartial(p_332637_ -> LOGGER.error("Failed to load profile from player clone: {}", p_332637_))
                    .ifPresent(this::setClone);
        }
        if (tag.hasUUID("owner")) {
            setOwnerUUID(tag.getUUID("owner"));
        }
        this.orderedToSit = tag.getBoolean("Sitting");
    }
}
