package tfar.zomboabilities.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.zomboabilities.ZomboAbilities;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "runLocationChangedEffects(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V"
    ,at = @At("HEAD"))
    private static void toggleFrostWalker(ServerLevel level, LivingEntity entity, CallbackInfo ci) {
        ZomboAbilities.locationChanged(level,entity);
    }
}
