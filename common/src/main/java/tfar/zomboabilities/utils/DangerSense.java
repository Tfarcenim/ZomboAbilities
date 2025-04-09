package tfar.zomboabilities.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import tfar.zomboabilities.attachments.CommonDataAttachments;

import java.util.function.Predicate;

public enum DangerSense {
    PLAYER(Player.class::isInstance,ChatFormatting.RED),
    BOSS(livingEntity -> livingEntity instanceof Warden || livingEntity instanceof WitherBoss,ChatFormatting.BLUE),//Warden, Wither
    MOB(livingEntity -> livingEntity instanceof Enemy,ChatFormatting.YELLOW);

    public final ChatFormatting chatFormatting;
    final Predicate<LivingEntity> checker;

    DangerSense(Predicate<LivingEntity>checker,ChatFormatting chatFormatting) {
        this.checker = checker;
        this.chatFormatting = chatFormatting;
    }

    @Nullable
    public static DangerSense getSense(Player player,LivingEntity check) {
        if (player == check || AbilityUtils.getDataAttachment(player, CommonDataAttachments.IGNORE_PLAYERS).contains(check.getUUID())) {
            return null;
        }
        for (DangerSense sense : DangerSense.values()) {
            if (sense.checker.test(check)) {
                if (check instanceof Player) return sense;
                if (check instanceof Mob mob) {
                    if (mob.getTarget() == player) {
                        return sense;
                    }
                }
            }
        }
        return null;
    }
}
