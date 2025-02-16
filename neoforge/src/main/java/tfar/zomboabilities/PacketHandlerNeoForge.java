package tfar.zomboabilities;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import tfar.zomboabilities.network.PacketHandler;
import tfar.zomboabilities.network.S2CAttachmentTypePacketBoolean;
import tfar.zomboabilities.platform.NeoForgePlatformHelper;
import tfar.zomboabilities.platform.Services;

public class PacketHandlerNeoForge {

    public static void register(RegisterPayloadHandlersEvent event){
        NeoForgePlatformHelper.registrar = event.registrar(ZomboAbilities.MOD_ID);
        PacketHandler.registerPackets();
        Services.PLATFORM.registerClientPlayPacket(S2CAttachmentTypePacketBoolean.TYPE,S2CAttachmentTypePacketBoolean.STREAM_CODEC);
    }

    public static void sendToClient(CustomPacketPayload packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player,packet);
    }

    public static void sendToServer(CustomPacketPayload packet) {
        PacketDistributor.sendToServer(packet);
    }
}
