package tfar.zomboabilities.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.SavedInventory;
import tfar.zomboabilities.ZomboAbilities;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDuck {

    int lives = 10;
    SavedInventory savedInventory = new SavedInventory();


    @Override
    public void setLives(int lives) {
        this.lives = lives;
    }

    @Inject(method = "dropEquipment",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;destroyVanishingCursedItems()V"))
    private void saveBedItems(CallbackInfo ci) {
        ZomboAbilities.saveBedItems((Player)(Object) this);
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
    }

    @Inject(method = "readAdditionalSaveData",at = @At("RETURN"))
    private void readData(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("lives", Tag.TAG_INT)) {
            lives = compound.getInt("lives");
        }
        ListTag listtag = compound.getList("SavedInventory", Tag.TAG_COMPOUND);
        savedInventory.load(listtag,this.registryAccess());
    }

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }
}
