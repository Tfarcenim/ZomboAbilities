package tfar.zomboabilities.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import tfar.zomboabilities.client.ClientPacketHandlerNeoforge;

public abstract class S2CAttachmentTypePacket<T> implements S2CModPacket<RegistryFriendlyByteBuf> {

    public final AttachmentType<T> type;
    public final int tracking;
    public final T data;

    public S2CAttachmentTypePacket(AttachmentType<T> type, int tracking, T data) {
        this.type = type;
        this.tracking = tracking;
        this.data = data;
    }

    public S2CAttachmentTypePacket(RegistryFriendlyByteBuf buf) {
        ResourceKey<AttachmentType<?>> key = buf.readResourceKey(NeoForgeRegistries.ATTACHMENT_TYPES.key());
        type = (AttachmentType<T>) NeoForgeRegistries.ATTACHMENT_TYPES.get(key);
        tracking = buf.readInt();
        data = dataCodec().decode(buf);
    }

    public abstract StreamCodec<RegistryFriendlyByteBuf, T> dataCodec();

    public void toPacket(RegistryFriendlyByteBuf buf) {
        ResourceKey<AttachmentType<?>> key = NeoForgeRegistries.ATTACHMENT_TYPES.getResourceKey(type).get();
        buf.writeResourceKey(key);
        buf.writeInt(tracking);
        dataCodec().encode(buf,data);
    }

    @Override
    public void handleClient() {
        ClientPacketHandlerNeoforge.handle(this);
    }

    @Override
    public String toString() {
        return "S2CAttachmentTypePacket[" +
                "type=" + type + ", " +
                "tracking=" + tracking + ", " +
                "data=" + data + ']';
    }

}
