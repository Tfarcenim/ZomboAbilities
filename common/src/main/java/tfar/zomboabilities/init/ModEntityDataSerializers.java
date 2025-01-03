package tfar.zomboabilities.init;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.item.component.ResolvableProfile;
import tfar.zomboabilities.platform.Services;

public class ModEntityDataSerializers {

    public static final EntityDataSerializer<ResolvableProfile> RESOLVABLE_PROFILE = EntityDataSerializer.forValueType(ResolvableProfile.STREAM_CODEC);

    static {
        if (Services.PLATFORM.getPlatformName().equals("Fabric")) {
            EntityDataSerializers.registerSerializer(RESOLVABLE_PROFILE);
        }
    }
}
