package tfar.zomboabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tfar.zomboabilities.ducks.AbstractFurnaceBlockEntityDuck;
import tfar.zomboabilities.init.ModTags;
import tfar.zomboabilities.utils.AbilityUtils;

import java.util.function.Predicate;

public class Hooks {
    public static void onTick(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity) {
        AbstractFurnaceBlockEntityDuck duck = (AbstractFurnaceBlockEntityDuck) blockEntity;
        blockEntity.litTime = Math.max(duck.getLaserTimer(),blockEntity.litTime);

        if (duck.getLaserTimer() > 0) {
            duck.setLaserTimer(duck.getLaserTimer() - 1);
        }
    }

    public static Predicate<LivingEntity> MERMAN = living -> living instanceof Player player && isMerman(player);

    public static<T extends LivingEntity> void modifyGoal(TargetingConditions conditions,Mob mob, Class<T> targetType, int randomInterval, boolean mustSee, boolean mustReach, Predicate<T> targetPredicate) {
            if (targetType == Player.class || targetType == ServerPlayer.class || targetType == LivingEntity.class) {
                Predicate<LivingEntity> existing = conditions.selector;
                Predicate<LivingEntity> stack = livingEntity -> !AbilityUtils.getAbility(livingEntity).isFriendly(mob);
                if (existing == null) {
                    conditions.selector(stack);
                } else {
                    conditions.selector(existing.and(stack));
                }
        }
    }

    public static boolean isMermanFriendly(Mob mob) {
        return mob.getType().is(ModTags.EntityTypes.MERMAN_FRIENDLY);
    }

    static boolean isMerman(Player player) {
        return AbilityUtils.hasAbility(player,Abilities.MERMAN);
    }
}
