package tfar.zomboabilities.network;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.platform.Services;

import java.util.List;
import java.util.Locale;

public class PacketHandler {

    public static void registerPackets()
    {
        Services.PLATFORM.registerServerPlayPacket(C2SAbilityPacket.TYPE, C2SAbilityPacket.STREAM_CODEC);
        Services.PLATFORM.registerServerPlayPacket(C2SHoldAbilityPacket.TYPE, C2SHoldAbilityPacket.STREAM_CODEC);
        Services.PLATFORM.registerClientPlayPacket(S2CSetLaserActivePacket.TYPE, S2CSetLaserActivePacket.STREAM_CODEC);
        Services.PLATFORM.registerClientPlayPacket(S2CParticleAABBPacket.TYPE, S2CParticleAABBPacket.STREAM_CODEC);


    }

    public static void sendToServer(C2SModPacket<?> packet) {
        Services.PLATFORM.sendToServer(packet);
    }

    public static void sendTo(S2CModPacket<?> packet, ServerPlayer player) {//todo check for fake players
            Services.PLATFORM.sendToClient(packet, player);
    }

    public static void sendPacketToAllInArea(ServerLevel level,S2CModPacket<?> packet, BlockPos center, int rangesqr) {
        List<ServerPlayer> playerList = level.players();
        for (ServerPlayer player : playerList)
        {
            if (player.distanceToSqr(center.getX(), center.getY(), center.getZ()) < rangesqr)
            {
                sendTo(packet, player);
            }
        }
    }

    public static void sendPacketToAll(MinecraftServer server,S2CModPacket<?> packet)
    {
        for (ServerPlayer player : server.getPlayerList().getPlayers())
        {
            sendTo(packet, player);
        }
    }


    public static ResourceLocation packet(Class<?> clazz) {
        return ZomboAbilities.id(clazz.getName().toLowerCase(Locale.ROOT));
    }


}
