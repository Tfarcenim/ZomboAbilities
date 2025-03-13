package tfar.zomboabilities.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record LightManipulationData(int lightFlashTimer, int uses) {
    public static final Codec<LightManipulationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("lightFlashTimer").forGetter(LightManipulationData::lightFlashTimer),
            Codec.INT.fieldOf("lightFlashTimer").forGetter(LightManipulationData::uses))
                    .apply(instance, LightManipulationData::new)
            );

    public static final StreamCodec<RegistryFriendlyByteBuf,LightManipulationData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,LightManipulationData::lightFlashTimer,
            ByteBufCodecs.INT,LightManipulationData::uses,
            LightManipulationData::new
    );

    public LightManipulationData decrementTimer() {
        return new LightManipulationData(lightFlashTimer-1,uses);
    }

    public LightManipulationData decrementUses() {
        return new LightManipulationData(lightFlashTimer,uses-1);
    }

}
