package tfar.zomboabilities.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.abilities.AbilityControls;

public record C2SHoldAbilityPacket(boolean p,boolean s,boolean t,boolean q) implements C2SModPacket<RegistryFriendlyByteBuf> {

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SHoldAbilityPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, C2SHoldAbilityPacket::p,
                    ByteBufCodecs.BOOL, C2SHoldAbilityPacket::s,
                    ByteBufCodecs.BOOL, C2SHoldAbilityPacket::t,
                    ByteBufCodecs.BOOL, C2SHoldAbilityPacket::q,
                    C2SHoldAbilityPacket::new
            );


    public static final Type<C2SHoldAbilityPacket> TYPE = ModPacket.type(C2SHoldAbilityPacket.class);

    @Override
    public void handleServer(ServerPlayer player) {
        AbilityControls.updateControls(player,p,s,t,q);
        if (ZomboAbilities.ENABLE_LOG) {
            //System.out.println(PlayerDuck.of(player).getControls());
        }
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
