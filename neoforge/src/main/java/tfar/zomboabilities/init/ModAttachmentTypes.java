package tfar.zomboabilities.init;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import tfar.zomboabilities.abilities.AbilityControls;
import tfar.zomboabilities.data.AbilityData;
import tfar.zomboabilities.data.ForceFieldData;
import tfar.zomboabilities.data.LivesData;

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
}
