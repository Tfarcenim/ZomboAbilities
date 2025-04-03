package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.compat.PehkuiCompat;
import tfar.zomboabilities.utils.AbilityUtils;

//Shrink
//
//Pressing R - This Ability allows you to shrink a super small size, the less Hunger bars you have, the smaller you can go,
// you need to be down 2 Hunger bars minimum to shrink.
//
//Your hearts will go down when you shrink
public class ShrinkAbility extends Ability {

    @Override
    public void primary(ServerPlayer player) {
        boolean shrink = !AbilityUtils.getDataAttachment(player,CommonDataAttachments.SHRUNK);
        AbilityUtils.toggleShrink(player);
        PehkuiCompat.shrink(player,shrink);
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
    public void onRemoved(ServerPlayer player) {
        super.onRemoved(player);
        AbilityUtils.defaultDataAttachment(player, CommonDataAttachments.SHRUNK);
    }
}
