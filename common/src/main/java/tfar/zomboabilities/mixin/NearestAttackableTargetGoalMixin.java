package tfar.zomboabilities.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.zomboabilities.Hooks;

import java.util.function.Predicate;

@Mixin(NearestAttackableTargetGoal.class)
public class NearestAttackableTargetGoalMixin<T extends LivingEntity> {
    @Shadow protected TargetingConditions targetConditions;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V",at = @At("RETURN"))
    private void modifyGoal(Mob mob, Class<T> targetType, int randomInterval, boolean mustSee, boolean mustReach, Predicate<T> targetPredicate, CallbackInfo ci) {
        Hooks.modifyGoal(this.targetConditions,mob,targetType,randomInterval,mustSee,mustReach,targetPredicate);
    }
}
