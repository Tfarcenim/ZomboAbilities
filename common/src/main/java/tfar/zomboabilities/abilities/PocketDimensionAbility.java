package tfar.zomboabilities.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.init.ModLevels;

//Pressing R - The Player will go into a Pitch black pocket dimension,
// there floor will be made out of Oak Planks,
// the Wall will be Made out of White Concrete and The Ceiling will be made out of white concrete,
// it will be a 23x25 Area Basically it’ll look like a little hotel room, everytime the player does this,
// they leave a glowstone from the area they teleported from, if someone else does right clicks on that Glowstone,
// they will be able to enter the pocket dimension,
//
// if the player with the ability Presses Y, they will leave the pocket Dimension and go back to the area they teleported from,
// they will have to break the glowstone, the Glowstone will not drop anything tho.
// If the player stays in this pocket dimension for more than 300 Seconds,
// they will get the Wither effect it will say in chat for them "You are running out of Oxygen" It will go away once they come out
//
//Players cannot do /tpa while in this Pocket Dimension
public class PocketDimensionAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        MinecraftServer server = player.server;
        boolean inPocketDimension = player.serverLevel().dimension() == ModLevels.POCKET_DIMENSION;

        if (inPocketDimension) {
            ServerLevel overworld = server.overworld();
            player.teleportTo(overworld, 0, 2, 0, player.getYRot(), player.getXRot());
        }
         else {
            ServerLevel level = server.getLevel(ModLevels.POCKET_DIMENSION);
            BlockPos returnPos = player.blockPosition();

            if (player.serverLevel().isInWorldBounds(returnPos)) {
                player.teleportTo(level, 0, 2, 0, player.getYRot(), player.getXRot());
            }
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
}
