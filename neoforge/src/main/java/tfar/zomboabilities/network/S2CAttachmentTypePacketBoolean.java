package tfar.zomboabilities.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.attachment.AttachmentType;

public class S2CAttachmentTypePacketBoolean extends S2CAttachmentTypePacket<Boolean> {

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CAttachmentTypePacketBoolean> STREAM_CODEC =
            StreamCodec.ofMember(S2CAttachmentTypePacket::toPacket,S2CAttachmentTypePacketBoolean::new);


    public static final Type<S2CAttachmentTypePacketBoolean> TYPE = ModPacket.type(S2CAttachmentTypePacketBoolean.class);

    public S2CAttachmentTypePacketBoolean(AttachmentType<Boolean> type, int tracking, Boolean data) {
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
