package tfar.zomboabilities.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import tfar.zomboabilities.ZomboAbilities;

import java.util.Objects;
import java.util.function.Function;

public class CommonDataAttachment<T> {

    protected final Function<Object,T> defaultValueSupplier;
    protected final ResourceLocation name;
    protected final boolean copyOnDeath;
    protected final Codec<T> codec;

    protected Object attachment;

    public CommonDataAttachment(Function<Object,T> defaultValueSupplier, ResourceLocation name, boolean copyOnDeath, Codec<T> codec) {
        this.defaultValueSupplier = defaultValueSupplier;
        this.name = name;
        this.copyOnDeath = copyOnDeath;
        this.codec = codec;
    }

    public static <T> Builder<T> create(Function<Object,T> defaultValueSupplier) {
        return new Builder<>(defaultValueSupplier);
    }

    public static <T> Builder<T> create() {
        return new Builder<>(o -> null);
    }

    public Function<Object,T> getDefaultValueSupplier() {
        return defaultValueSupplier;
    }        
    public ResourceLocation getName() {
        return name;
    }

    public Codec<T> getCodec() {
        return codec;
    }
    public boolean isCopyOnDeath() {
        return copyOnDeath;
    }
    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        Objects.requireNonNull(attachment);
        this.attachment = attachment;
    }


    public static class Builder<T> {
        protected final Function<Object,T> defaultValueSupplier;
        protected boolean copyOnDeath;
        protected Codec<T> codec;

        public Builder(Function<Object, T> defaultValueSupplier) {
            this.defaultValueSupplier = defaultValueSupplier;
        }

        public Builder<T> codec(Codec<T> codec) {
            Objects.requireNonNull(codec);
            this.codec = codec;
            return this;
        }

        public Builder<T> copyOnDeath() {
            copyOnDeath = true;
            return this;
        }

        public CommonDataAttachment<T> build(String name) {
            return build(ZomboAbilities.id(name));
        }

        public CommonDataAttachment<T> build(ResourceLocation name) {
            Objects.requireNonNull(name);
            return new CommonDataAttachment<>(defaultValueSupplier,name,copyOnDeath, codec);
        }
    }
}
