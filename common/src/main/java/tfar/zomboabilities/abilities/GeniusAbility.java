package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.menu.GeniusCraftingMenu;

public class GeniusAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        player.openMenu(GeniusCraftingMenu.PROVIDER);
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
}
