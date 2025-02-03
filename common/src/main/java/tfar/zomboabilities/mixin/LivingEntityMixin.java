package tfar.zomboabilities.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.PlayerDuck;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    /*@Inject(method = "isSensitiveToWater",at = @At("RETURN"),cancellable = true)
    private void waterHurts(CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof Player player) {
            boolean bool = PlayerDuck.of(player).getAbility().map(ability -> ability == Abilities.ENDERMAN_GENETICS).orElse(false);
            if (bool) {
                cir.setReturnValue(true);
            }
        }
    }*/
}
