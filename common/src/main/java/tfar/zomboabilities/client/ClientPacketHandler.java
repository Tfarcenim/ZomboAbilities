package tfar.zomboabilities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.network.S2CCommonDataAttachmentPacket;
import tfar.zomboabilities.network.S2CSetKeyActivePacket;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.AbilityUtils;

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

    public static <T> void handle(S2CCommonDataAttachmentPacket<T> s2CAttachmentTypePacket) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            Entity entity = level.getEntity(s2CAttachmentTypePacket.tracking);
            if (entity != null) {
                Services.PLATFORM.setAttachedValue(entity,s2CAttachmentTypePacket.type, s2CAttachmentTypePacket.data);
            }
        }
    }
}
