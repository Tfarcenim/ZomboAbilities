package tfar.zomboabilities.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.zomboabilities.*;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.AbilityControls;
import tfar.zomboabilities.abilities.CopyAbility;
import tfar.zomboabilities.network.S2CSetLaserActivePacket;
import tfar.zomboabilities.platform.Services;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDuck {

    int lives = 10;
    SavedInventory savedInventory = new SavedInventory();

    Optional<Ability> ability = Optional.empty();
    Ability copied_ability;
    final int[] cooldowns = new int[4];
    Consumer<ServerPlayer> mobAbility;
    boolean laserActive;
    final AbilityControls controls = new AbilityControls();

    int laserActiveDuration;
    int cloneCount;

    @Override
    public void setLives(int lives) {
        this.lives = lives;
    }

    @Inject(method = "dropEquipment",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;destroyVanishingCursedItems()V"))
    private void saveBedItems(CallbackInfo ci) {
        ZomboAbilities.saveBedItems((Player)(Object) this);
    }

    @Override
    public void setAbility(@Nullable Ability ability) {
        this.ability = Optional.ofNullable(ability);
    }

    @Override
    public Optional<Ability> getAbility() {
        return ability;
    }

    @Override
    public void setMobAbility(Consumer<ServerPlayer> mobAbility) {
        this.mobAbility = mobAbility;
    }

    @Override
    public Consumer<ServerPlayer> getMobAbility() {
        return mobAbility;
    }

    @Override
    public int[] getCooldowns() {
        return cooldowns;
    }

    @Override
    public void setLaserActive(boolean laserActive) {
        if (!level().isClientSide) {
            ServerPlayer player = (ServerPlayer)(Object)this;
            Services.PLATFORM.sendToTracking(new S2CSetLaserActivePacket(player.getUUID(),laserActive),player);
        }
        this.laserActive = laserActive;
    }

    @Override
    public boolean isLaserActive() {
        return laserActive;
    }

    @Override
    public int getLaserActiveDuration() {
        return laserActiveDuration;
    }

    @Override
    public void setLaserActiveDuration(int laserActiveDuration) {
        this.laserActiveDuration = laserActiveDuration;
    }

    @Override
    public void setCopiedAbility(Ability copied_ability) {
        this.copied_ability = copied_ability;
    }

    @Override
    public Ability getCopiedAbility() {
        return copied_ability;
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public int getCloneCount() {
        return cloneCount;
    }

    @Override
    public void setCloneCount(int cloneCount) {
        this.cloneCount = cloneCount;
    }

    @Override
    public SavedInventory getSavedInventory() {
        return savedInventory;
    }

    @Override
    public AbilityControls getControls() {
        return controls;
    }

    @Inject(method = "addAdditionalSaveData",at = @At("RETURN"))
    private void addData(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("lives",lives);
        compound.put("SavedInventory", savedInventory.save(new ListTag(),this.registryAccess()));
        ability.ifPresent(ability1 -> compound.putString("Ability",ability1.getName()));
        compound.putInt("cloneCount",cloneCount);
    }

    @Inject(method = "readAdditionalSaveData",at = @At("RETURN"))
    private void readData(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("lives", Tag.TAG_INT)) {
            lives = compound.getInt("lives");
        }
        ListTag listtag = compound.getList("SavedInventory", Tag.TAG_COMPOUND);
        savedInventory.load(listtag,this.registryAccess());
        if (compound.contains("Ability")) {
            ability = Optional.ofNullable(Abilities.ABILITIES_BY_NAME.get(compound.getString("Ability")));
        }
        cloneCount = compound.getInt("cloneCount");
    }

    @Override
    public boolean isSensitiveToWater() {
        return mobAbility == CopyAbility.ENDERMAN || (ability.isPresent() && ability.get() == Abilities.ENDERMAN_GENETICS);
    }

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }
}
