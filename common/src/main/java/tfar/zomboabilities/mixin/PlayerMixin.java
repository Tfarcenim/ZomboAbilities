package tfar.zomboabilities.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
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

import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDuck {

    int lives = 10;
    SavedInventory savedInventory = new SavedInventory();

    Optional<Ability> ability = Optional.empty();
    final int[] cooldowns = new int[4];

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
    public int[] getCooldowns() {
        return cooldowns;
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public SavedInventory getSavedInventory() {
        return savedInventory;
    }

    @Inject(method = "addAdditionalSaveData",at = @At("RETURN"))
    private void addData(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("lives",lives);
        compound.put("SavedInventory", savedInventory.save(new ListTag(),this.registryAccess()));
        ability.ifPresent(ability1 -> compound.putString("Ability",ability1.getName()));
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
    }

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }
}
