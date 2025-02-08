package tfar.zomboabilities.init;

import net.neoforged.neoforge.attachment.AttachmentType;
import tfar.zomboabilities.ForceFieldData;

public class ModAttachmentTypes {
    public static final AttachmentType<ForceFieldData> FORCE_FIELD_DATA = AttachmentType.builder(() -> new ForceFieldData())
            .serialize(ForceFieldData.CODEC).build();
}
