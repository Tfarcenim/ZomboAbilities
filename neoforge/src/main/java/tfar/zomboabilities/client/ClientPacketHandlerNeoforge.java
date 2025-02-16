package tfar.zomboabilities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import tfar.zomboabilities.network.S2CAttachmentTypePacket;

public class ClientPacketHandlerNeoforge {
    public static <T> void handle(S2CAttachmentTypePacket<T> s2CAttachmentTypePacket) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            Entity entity = level.getEntity(s2CAttachmentTypePacket.tracking);
            if (entity != null) {
                entity.setData(s2CAttachmentTypePacket.type, s2CAttachmentTypePacket.data);
            }
        }
    }
}
