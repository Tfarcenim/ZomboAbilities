package tfar.zomboabilities.network;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import tfar.zomboabilities.client.ClientPacketHandler;

import java.util.UUID;

public record S2CSetLaserActivePacket(UUID uuid,boolean active) implements S2CModPacket<RegistryFriendlyByteBuf>{

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSetLaserActivePacket> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC,S2CSetLaserActivePacket::uuid,
                    ByteBufCodecs.BOOL,S2CSetLaserActivePacket::active,
                    S2CSetLaserActivePacket::new);

    public static final CustomPacketPayload.Type<S2CSetLaserActivePacket> TYPE = ModPacket.type(S2CSetLaserActivePacket.class);

    @Override
    public void handleClient() {
        ClientPacketHandler.handle(this);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
