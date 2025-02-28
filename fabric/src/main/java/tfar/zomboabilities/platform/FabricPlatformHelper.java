package tfar.zomboabilities.platform;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.world.entity.Entity;
import tfar.zomboabilities.attachments.CommonDataAttachment;
import tfar.zomboabilities.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public <T> void registerDataAttachment(CommonDataAttachment<T> attachment) {
        AttachmentType<T> type = createType(attachment);
        attachment.setAttachment(type);
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    @Override
    public <T> T getAttachedValue(Entity entity, CommonDataAttachment<T> attachment) {
        AttachmentType<T> type = (AttachmentType<T>) attachment.getAttachment();
        return entity.getAttached(type);
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    @Override
    public <T> void setAttachedValue(Entity entity, CommonDataAttachment<T> attachment, T value) {
        AttachmentType<T> type = (AttachmentType<T>) attachment.getAttachment();
        entity.setAttached(type,value);
    }

    @SuppressWarnings("UnstableApiUsage")
    <T> AttachmentType<T> createType(CommonDataAttachment<T> attachment) {
        AttachmentRegistry.Builder<T> builder = AttachmentRegistry.builder();
        if (attachment.isCopyOnDeath()) {
            builder.copyOnDeath();
        }
        builder.initializer(() -> attachment.getDefaultValueSupplier().apply(null));
        if (attachment.getCodec() != null) {
            builder.persistent(attachment.getCodec());
        }
        return builder.buildAndRegister(attachment.getName());
    }
}
