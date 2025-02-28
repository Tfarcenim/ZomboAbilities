package tfar.zomboabilities.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import tfar.zomboabilities.data.ObjectRestorationData;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.AbilityUtils;

////Object Restoration
////
////Pressing R - This Ability will restore any object that has completely disappeared from existence within 10 minutes of it breaking,
// For Example, Glass breaking, Breaking stone with your fist, basically any object that doesnt drop anything, it will restore,
// Player has to be near where the object disappeared
////
////Holding Y - This Ability allows you to repair nearly broken tools for free Including armor to.
////
////Optional, Not sure if this is possible, An alternate way to do this ability, it’ll take the block out of the inventories
// of people to repair the area.not just blocks that drop nothing
public class ObjectRestorationAbility extends Ability {
    @Override
    public void primary(ServerPlayer player) {
        ObjectRestorationData orData = AbilityUtils.getORData(player);
        if (!orData.state().isAir() && player.level().getBlockState(orData.pos()).canBeReplaced()) {
            player.level().setBlock(orData.pos(),orData.state(), Block.UPDATE_ALL);
            AbilityUtils.setORData(player,new ObjectRestorationData(BlockPos.ZERO, Blocks.AIR.defaultBlockState()));
        }
    }

    @Override
    public void secondary(ServerPlayer player) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.has(DataComponents.DAMAGE)) {
                stack.set(DataComponents.DAMAGE,0);
            }
        }
    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }
}
