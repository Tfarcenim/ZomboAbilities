package tfar.zomboabilities.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.entity.*;

public class ModEntityTypes {

    public static final EntityType<AttackingClonePlayerEntity> CLONE_PLAYER = register("clone_player",
            EntityType.Builder.of(AttackingClonePlayerEntity::new, MobCategory.MISC).sized(.8f,1.8f));
    public static final EntityType<IllusionPlayerEntity> ILLUSION_PLAYER = register("illusion_player",EntityType.Builder.of(IllusionPlayerEntity::new, MobCategory.MISC).sized(.8f,1.8f));

    public static final EntityType<FireBreathEntity> FIRE_BREATH = register("fire_breath",EntityType.Builder.<FireBreathEntity>of(FireBreathEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F));
    public static final EntityType<IceSpikeEntity> ICE_SPIKE = register("ice_spike",EntityType.Builder.of(IceSpikeEntity::new, MobCategory.MISC).sized(0.5F, 2F));

    private static <T extends Entity> EntityType<T> register(String key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, ZomboAbilities.id(key), builder.build(key));
    }

    public static void init() {

    }
}
