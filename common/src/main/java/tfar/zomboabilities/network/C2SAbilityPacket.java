package tfar.zomboabilities.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.PlayerDuck;

public record C2SAbilityPacket(int key) implements C2SModPacket<RegistryFriendlyByteBuf> {

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SAbilityPacket> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.INT,C2SAbilityPacket::key,C2SAbilityPacket::new);


    public static final CustomPacketPayload.Type<C2SAbilityPacket> TYPE = ModPacket.type(C2SAbilityPacket.class);

    @Override
    public void handleServer(ServerPlayer player) {
        PlayerDuck.of(player).getAbility().ifPresent(ability -> ability.tryUseAbility(player,key));
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
