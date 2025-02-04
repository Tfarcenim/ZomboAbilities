package tfar.zomboabilities.network;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import tfar.zomboabilities.client.ClientPacketHandler;

import java.util.UUID;

public record S2CSetKeyActivePacket(UUID uuid, boolean active,int slot) implements S2CModPacket<RegistryFriendlyByteBuf>{

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSetKeyActivePacket> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC, S2CSetKeyActivePacket::uuid,
                    ByteBufCodecs.BOOL, S2CSetKeyActivePacket::active,
                    ByteBufCodecs.INT, S2CSetKeyActivePacket::slot,
                    S2CSetKeyActivePacket::new);

    public static final CustomPacketPayload.Type<S2CSetKeyActivePacket> TYPE = ModPacket.type(S2CSetKeyActivePacket.class);

    @Override
    public void handleClient() {
        ClientPacketHandler.handle(this);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
