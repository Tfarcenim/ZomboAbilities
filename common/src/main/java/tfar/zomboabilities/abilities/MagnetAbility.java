package tfar.zomboabilities.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tfar.zomboabilities.init.ModTags;
import tfar.zomboabilities.utils.AbilityUtils;

import java.util.List;

//Pressing R - This Ability allows all Nearby Objects within 300 Blocks made out of Iron that arent Placed, to attract fast to the Player,
// It will say "Magnet On" In chat for the player and Pressing R again will deactivate it. Iron Golems will be attracted towards the player
//

//
//Passive - Any of the tool mentioned above, if another player trys to hit the player with the ability with an iron item,
// The item will automatically be placed in the inventory of the player with the ability.
public class MagnetAbility extends Ability {
    @Override
    public void primary(ServerPlayer player) {
        boolean magnet = AbilityUtils.getMagnet(player);
        magnet = !magnet;
        AbilityUtils.setMagnet(player,magnet);
        Component message = Component.literal("Magnet "+ (magnet ? "on" : "off"));
        player.sendSystemMessage(message);
    }

    @Override
    public void secondary(ServerPlayer player) {

    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }

    @Override
    public void tickAbility(ServerPlayer player) {
        super.tickAbility(player);
        double r = 16;
        List<Entity> entityList = player.serverLevel().getEntities(player,player.getBoundingBox().inflate(r),entity -> entity instanceof ItemEntity itemEntity && (itemEntity.getItem().is(ModTags.Items.ATTRACTED)) || entity instanceof IronGolem);

        for (Entity entity : entityList) {
            double power = 1 / Math.max(1,entity.distanceToSqr(player)) * .125;
            entity.addDeltaMovement(player.position().subtract(entity.position()).scale(power));
            entity.hurtMarked = true;
        }
    }

    @Override
    public boolean onAttacked(Player attacked, LivingEntity attacker) {
        if (attacker.getWeaponItem().is(ModTags.Items.ATTRACTED) && attacker instanceof ServerPlayer playerAttacker) {
            playerAttacker.drop(attacker.getWeaponItem(),true);
            attacker.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            return true;
        }
        return false;
    }
}
