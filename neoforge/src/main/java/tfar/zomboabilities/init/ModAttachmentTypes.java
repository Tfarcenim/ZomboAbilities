package tfar.zomboabilities.init;

import net.neoforged.neoforge.attachment.AttachmentType;
import tfar.zomboabilities.data.AbilityData;
import tfar.zomboabilities.data.ForceFieldData;

public class ModAttachmentTypes {
    public static final AttachmentType<ForceFieldData> FORCE_FIELD_DATA = AttachmentType.builder(ForceFieldData::new)
            .serialize(ForceFieldData.CODEC).build();

    public static final AttachmentType<AbilityData> ABILITY_DATA = AttachmentType.builder(() -> new AbilityData())
            .serialize(AbilityData.CODEC).build();
}
