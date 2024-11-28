package tfar.zomboabilities.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.zomboabilities.ItemEntityDuck;

@Mixin(ItemEntity.class)
public class ItemEntityMixin implements ItemEntityDuck {

    int floating;

    @Override
    public void setFloatTime(int floatTime) {
        this.floating = floatTime;
    }

    @Inject(method = "tick",at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (floating > 0) {
            floating--;
        }
    }

    @Inject(method = "getDefaultGravity",at = @At("RETURN"),cancellable = true)
    private void modifyGravity(CallbackInfoReturnable<Double> cir) {
        if (floating > 0) {
            cir.setReturnValue(0.);
        }
    }
}
