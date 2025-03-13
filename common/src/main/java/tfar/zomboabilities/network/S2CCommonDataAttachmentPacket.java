package tfar.zomboabilities.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import tfar.zomboabilities.attachments.CommonDataAttachment;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.client.ClientPacketHandler;

public class S2CCommonDataAttachmentPacket<T> implements S2CModPacket<RegistryFriendlyByteBuf> {

    public final CommonDataAttachment<T> type;
    public final int tracking;
    public final T data;


    public static final StreamCodec<RegistryFriendlyByteBuf, S2CCommonDataAttachmentPacket> STREAM_CODEC =
            StreamCodec.ofMember(S2CCommonDataAttachmentPacket::toPacket, S2CCommonDataAttachmentPacket::new);

    public static final Type<S2CCommonDataAttachmentPacket> TYPE = ModPacket.type(S2CCommonDataAttachmentPacket.class);


    public S2CCommonDataAttachmentPacket(CommonDataAttachment<T> type, Entity entity, T data) {
        this.type = type;
        this.tracking = entity.getId();
        this.data = data;
    }

    public S2CCommonDataAttachmentPacket(RegistryFriendlyByteBuf buf) {
        ResourceLocation key = buf.readResourceLocation();
        type = (CommonDataAttachment<T>) CommonDataAttachments.lookup(key);
        tracking = buf.readInt();
        data = type.getStreamCodec().decode(buf);
    }

    public void toPacket(RegistryFriendlyByteBuf buf) {
        buf.writeResourceLocation(type.getName());
        buf.writeInt(tracking);
        type.getStreamCodec().encode(buf,data);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handleClient() {
        ClientPacketHandler.handle(this);
    }

    @Override
    public String toString() {
        return "S2CCommonDataAttachmentPacket[" +
                "type=" + type + ", " +
                "tracking=" + tracking + ", " +
                "data=" + data + ']';
    }

}
