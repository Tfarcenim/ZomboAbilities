package tfar.zomboabilities.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;

public record ForceFieldData(HashSet<BlockPos> fieldPositions, int size) {

    public ForceFieldData() {
        this(new HashSet<>(),0);
    }

    public static final Codec<ForceFieldData> CODEC = RecordCodecBuilder.create(forceFieldDataInstance -> forceFieldDataInstance.group(
              BlockPos.CODEC.listOf().xmap(HashSet::new, ArrayList::new).fieldOf("fieldPositions").forGetter(ForceFieldData::fieldPositions),
            Codec.INT.fieldOf("size").forGetter(ForceFieldData::size)
      ).apply(forceFieldDataInstance,ForceFieldData::new));

}
