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
import tfar.zomboabilities.init.Tags;
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

    static Predicate<LivingEntity> MERMAN = living -> living instanceof Player player && isMerman(player);

    public static<T extends LivingEntity> void modifyGoal(TargetingConditions conditions,Mob mob, Class<T> targetType, int randomInterval, boolean mustSee, boolean mustReach, Predicate<T> targetPredicate) {
        if (isMermanFriendly(mob)) {
            if (targetType == Player.class || targetType == ServerPlayer.class || targetType == LivingEntity.class) {
                Predicate<T> existing = (Predicate<T>) conditions.selector;
                if (existing == null) {
                    conditions.selector(MERMAN.negate());
                } else {
                    conditions.selector((Predicate<LivingEntity>) existing.and(MERMAN.negate()));
                }
            }
        }
    }

    static boolean isMermanFriendly(Mob mob) {
        return mob.getType().is(Tags.MERMAN_FRIENDLY);
    }

    static boolean isMerman(Player player) {
        return AbilityUtils.hasAbility(player,Abilities.MERMAN);
    }
}
