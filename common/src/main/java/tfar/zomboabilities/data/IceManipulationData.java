package tfar.zomboabilities.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record IceManipulationData(boolean frostWalkerActive) {

    public IceManipulationData() {
        this(false);
    }

    public static final Codec<IceManipulationData> CODEC = RecordCodecBuilder.create(
            IceManipulationDataInstance -> IceManipulationDataInstance.group(Codec.BOOL.fieldOf("frostWalkerActive").forGetter(IceManipulationData::frostWalkerActive))
                    .apply(IceManipulationDataInstance,IceManipulationData::new));

    public IceManipulationData setFrostWalkerActive(boolean active) {
        return active != this.frostWalkerActive ? new IceManipulationData(frostWalkerActive) : this;
    }
}
