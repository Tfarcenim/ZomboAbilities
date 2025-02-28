package tfar.zomboabilities.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import tfar.zomboabilities.attachments.CommonDataAttachment;

public class S2CAttachmentTypePacketBoolean extends S2CCommonDataAttachmentPacket<Boolean> {

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CAttachmentTypePacketBoolean> STREAM_CODEC =
            StreamCodec.ofMember(S2CCommonDataAttachmentPacket::toPacket,S2CAttachmentTypePacketBoolean::new);


    public static final Type<S2CAttachmentTypePacketBoolean> TYPE = ModPacket.type(S2CAttachmentTypePacketBoolean.class);

    public S2CAttachmentTypePacketBoolean(CommonDataAttachment<Boolean> type, int tracking, Boolean data) {
        super(type, tracking, data);
    }

    public S2CAttachmentTypePacketBoolean(RegistryFriendlyByteBuf buf) {
        super(buf);
    }


    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Boolean> dataCodec() {
        return (StreamCodec<RegistryFriendlyByteBuf, Boolean>)(Object)ByteBufCodecs.BOOL;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
