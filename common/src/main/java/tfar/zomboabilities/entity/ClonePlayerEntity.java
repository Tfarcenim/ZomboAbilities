package tfar.zomboabilities.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import tfar.zomboabilities.client.ModClient;
import tfar.zomboabilities.init.ModEntityDataSerializers;

import java.util.Objects;
import java.util.UUID;

public class ClonePlayerEntity extends PathfinderMob implements OwnableEntity {

    private static final Logger LOGGER = LogUtils.getLogger();

    protected static final EntityDataAccessor<ResolvableProfile> DATA_CLONE_ID = SynchedEntityData.defineId(ClonePlayerEntity.class, ModEntityDataSerializers.RESOLVABLE_PROFILE);

    protected UUID owner;


    public ClonePlayerEntity(EntityType<? extends PathfinderMob> $$0, Level $$1) {
        super($$0, $$1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE,1).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.MAX_HEALTH,20);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, this::shouldAttack));
      //  goalSelector.addGoal(3,new CloneFollowOwnerGoal(this,1,4,12,false));

      //  this.targetSelector.addGoal(1, new CloneOwnerHurtByTargetGoal(this));
      //  this.targetSelector.addGoal(1, new CloneOwnerHurtTargetGoal(this));
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
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        return super.interactAt(player, vec, hand);
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
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        ResolvableProfile uuid = getClone();
        if (uuid != null) {
            tag.put("clone", ResolvableProfile.CODEC.encodeStart(NbtOps.INSTANCE, uuid).getOrThrow());
        }
        if (owner != null) {
            tag.putUUID("owner",owner);
        }
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
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return owner;
    }

    public void setOwnerUUID(UUID owner) {
        this.owner = owner;
    }

}
