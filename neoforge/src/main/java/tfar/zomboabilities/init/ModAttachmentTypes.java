package tfar.zomboabilities.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.attachment.AttachmentType;
import tfar.zomboabilities.abilities.AbilityControls;
import tfar.zomboabilities.data.*;

public class ModAttachmentTypes {
    public static final AttachmentType<ForceFieldData> FORCE_FIELD_DATA = AttachmentType.builder(ForceFieldData::new)
            .serialize(ForceFieldData.CODEC).copyOnDeath().build();

    public static final AttachmentType<AbilityData> ABILITY_DATA = AttachmentType.builder(() -> new AbilityData())
            .serialize(AbilityData.CODEC).copyOnDeath().build();

    public static final AttachmentType<LivesData> LIVES_DATA = AttachmentType.builder(iAttachmentHolder -> {
        if (iAttachmentHolder instanceof Entity entity) {
            return new LivesData(entity.level().getGameRules().getInt(ModGameRules.RULE_STARTINGLIVES));
        }
        throw new RuntimeException("Invalid type: "+iAttachmentHolder);
            })
            .serialize(LivesData.CODEC).copyOnDeath().build();

    public static final AttachmentType<AbilityControls> ABILITY_CONTROLS = AttachmentType.builder(AbilityControls::new).build();
    public static final AttachmentType<IceManipulationData> ICE_MANIPULATION_DATA = AttachmentType.builder(() -> new IceManipulationData())
            .serialize(IceManipulationData.CODEC).build();

    public static final AttachmentType<ObjectRestorationData> BLOCK_RESTORATION = AttachmentType
            .builder(() -> new ObjectRestorationData(BlockPos.ZERO, Blocks.AIR.defaultBlockState()))
            .build();

    public static final AttachmentType<Boolean> INFINITY_ACTIVE = AttachmentType.builder(() -> false).serialize(Codec.BOOL).build();
}
