package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.utils.AbilityUtils;

//Passive - This Ability sorta allows you to be intangible, Arrows will go straight through you, You will Take Damage in Water,
// everytime you sprint, Sand sprinting particles will show on your feet
//
//Pressing R - If you are Above Sand, you can swim and Hide in it for as long as you want,
// there will be dust particles above you when your in a sand, like those particles when you sprint on sand.
//
//Press T - Summons a Sand block in your Crosshair
public class SandBodyAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        AbilityUtils.toggleSandShift(player);
    }

    @Override
    public void secondary(ServerPlayer player) {
        BlockHitResult pick = (BlockHitResult) player.pick(player.blockInteractionRange(),1,false);
        BlockItem sandBlock = (BlockItem) Items.SAND;
        sandBlock.place(new BlockPlaceContext(player, InteractionHand.MAIN_HAND,sandBlock.getDefaultInstance(), pick));
    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }

    @Override
    public void onRemoved(ServerPlayer player) {
        super.onRemoved(player);
        AbilityUtils.setDataAttachment(player, CommonDataAttachments.SAND_SHIFT,false);
    }
}
