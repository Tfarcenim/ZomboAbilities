package tfar.zomboabilities.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.AbilityControls;
import tfar.zomboabilities.data.ForceFieldData;
import tfar.zomboabilities.data.IceManipulationData;
import tfar.zomboabilities.data.ObjectRestorationData;
import tfar.zomboabilities.init.ModGameRules;
import tfar.zomboabilities.platform.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommonDataAttachments {

    private static final Map<ResourceLocation,CommonDataAttachment<?>> MAP =new HashMap<>();

    public static final CommonDataAttachment<Ability> ABILITY = register(CommonDataAttachment.create(o -> Abilities.NONE)
            .codec(Ability.CODEC)
            .copyOnDeath()
            .build("ability"));

    public static final CommonDataAttachment<int[]> COOLDOWNS = register(CommonDataAttachment
            .create(o -> new int[4])
            .build("cooldowns"));

    public static final CommonDataAttachment<ForceFieldData> FORCE_FIELD_DATA = register(CommonDataAttachment
            .create(o -> new ForceFieldData())
            .codec(ForceFieldData.CODEC)
            .copyOnDeath().build("force_field"));

    public static final CommonDataAttachment<Integer> LIVES = register(CommonDataAttachment.
            create(o -> {
                if (o instanceof Entity entity) {
                    return entity.level().getGameRules().getInt(ModGameRules.RULE_STARTINGLIVES);
                }
                throw new RuntimeException("Not an entity: "+o);
            })
            .codec(Codec.INT).copyOnDeath().build("lives"));

    public static final CommonDataAttachment<AbilityControls> ABILITY_CONTROLS = register(CommonDataAttachment
            .create(o -> new AbilityControls())
            .build("ability_controls"));

    public static final CommonDataAttachment<IceManipulationData> ICE_MANIPULATION_DATA = register(CommonDataAttachment
            .create(o -> new IceManipulationData())
            .codec(IceManipulationData.CODEC).build("ice_manipulation"));

    public static final CommonDataAttachment<ObjectRestorationData> BLOCK_RESTORATION =  register(CommonDataAttachment
            .create(o -> new ObjectRestorationData(BlockPos.ZERO, Blocks.AIR.defaultBlockState()))
            .build("object_restoration"));

    public static final CommonDataAttachment<Boolean> INFINITY = register(CommonDataAttachment.create(o -> false)
            .codec(Codec.BOOL)
            .build("infinity"));


    public static CommonDataAttachment<?> lookup(ResourceLocation location) {
        return MAP.get(location);
    }

    static <T> CommonDataAttachment<T> register(CommonDataAttachment<T> type) {
        Services.PLATFORM.registerDataAttachment(type);
        Objects.requireNonNull(type.getAttachment());
        MAP.put(type.name,type);
        return type;
    }

    public static void init() {

    }
}
