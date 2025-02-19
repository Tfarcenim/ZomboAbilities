package tfar.zomboabilities.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public record ObjectRestorationData(BlockPos pos, BlockState state) {

    public static final Codec<ObjectRestorationData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(BlockPos.CODEC.fieldOf("pos").forGetter(ObjectRestorationData::pos),
              BlockState.CODEC.fieldOf("state").forGetter(ObjectRestorationData::state))
                    .apply(instance, ObjectRestorationData::new));

}
