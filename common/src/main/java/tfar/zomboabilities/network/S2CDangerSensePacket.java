package tfar.zomboabilities.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class S2CDangerSensePacket implements S2CModPacket<RegistryFriendlyByteBuf> {
    @Override
    public void handleClient() {

    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}
