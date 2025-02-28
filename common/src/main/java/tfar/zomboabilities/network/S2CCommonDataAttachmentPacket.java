package tfar.zomboabilities.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import tfar.zomboabilities.attachments.CommonDataAttachment;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.client.ClientPacketHandler;

public abstract class S2CCommonDataAttachmentPacket<T> implements S2CModPacket<RegistryFriendlyByteBuf> {

    public final CommonDataAttachment<T> type;
    public final int tracking;
    public final T data;

    public S2CCommonDataAttachmentPacket(CommonDataAttachment<T> type, int tracking, T data) {
        this.type = type;
        this.tracking = tracking;
        this.data = data;
    }

    public S2CCommonDataAttachmentPacket(RegistryFriendlyByteBuf buf) {
        ResourceLocation key = buf.readResourceLocation();
        type = (CommonDataAttachment<T>) CommonDataAttachments.lookup(key);
        tracking = buf.readInt();
        data = dataCodec().decode(buf);
    }

    public abstract StreamCodec<RegistryFriendlyByteBuf, T> dataCodec();

    public void toPacket(RegistryFriendlyByteBuf buf) {
        buf.writeResourceLocation(type.getName());
        buf.writeInt(tracking);
        dataCodec().encode(buf,data);
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
