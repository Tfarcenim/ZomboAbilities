package tfar.zomboabilities.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.zomboabilities.PlayerDuck;

@Mixin(Player.class)
public class PlayerMixin implements PlayerDuck {

    int lives = 10;

    @Override
    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Inject(method = "addAdditionalSaveData",at = @At("RETURN"))
    private void addData(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("lives",lives);
    }

    @Inject(method = "readAdditionalSaveData",at = @At("RETURN"))
    private void readData(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("lives", Tag.TAG_INT)) {
            lives = compound.getInt("lives");
        }
    }

}
