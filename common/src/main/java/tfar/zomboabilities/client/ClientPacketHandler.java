package tfar.zomboabilities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.network.S2CSetKeyActivePacket;

public class ClientPacketHandler {

    public static void handle(S2CSetKeyActivePacket packet) {
        Player player = null;
        if (Minecraft.getInstance().level != null) {
            player = Minecraft.getInstance().level.getPlayerByUUID(packet.uuid());
        }
        if(player != null) {
            PlayerDuck.of(player).setFunctionActive(packet.active(),packet.slot());
        }
    }
}
