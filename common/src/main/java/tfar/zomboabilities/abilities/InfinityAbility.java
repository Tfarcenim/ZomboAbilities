package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.platform.Services;

//Pressing R - This ability allows you to have a force field around yourself, This ability will require to take 2 Exp every 5 seconds it is used, When you press R , it will tell you in chat if the Infinity is on or Off, The Infinity will take 2 Exp to activate and 2 Exp to deactivate, Player will be Immune to all damage, the Only thing that Would be able to Kill the player is the Void, and /kill. Whne this ability activate all armor currently on will drop out the players inventory
//
//Watch this Video to get a better idea. https://www.youtube.com/watch?v=EtNwydjXTO0
public class InfinityAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        if (player.totalExperience > 1) {
            Services.PLATFORM.setInfinityActive(player, !Services.PLATFORM.isInfinityActive(player));
        }
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

    static int TIME = 50;

    @Override
    public void tick(ServerPlayer player) {
        super.tick(player);
        if (Services.PLATFORM.isInfinityActive(player)) {
            if (player.tickCount % TIME == 0) {
                player.giveExperiencePoints(-1);
                if (player.totalExperience ==0) {
                    Services.PLATFORM.setInfinityActive(player,false);
                }
            }
        }
    }
}
