package tfar.zomboabilities.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.zomboabilities.Hooks;
import tfar.zomboabilities.ducks.AbstractFurnaceBlockEntityDuck;

@Mixin(AbstractFurnaceBlockEntity.class)
@Debug(export = true)
public class AbstractFurnaceBlockEntityMixin implements AbstractFurnaceBlockEntityDuck {

    @Shadow
    public int litTime;
    int laserTimer;

    @Override
    public int getLaserTimer() {
        return laserTimer;
    }

    @Override
    public void setLaserTimer(int laser) {
        laserTimer = laser;
      //  this.litTime = Math.max(laserTimer,litTime);
    }

    @Inject(method = "serverTick",at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;get(I)Ljava/lang/Object;",ordinal = 0))
    private static void onTick(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        Hooks.onTick(level, pos, state, blockEntity);
    }
}
