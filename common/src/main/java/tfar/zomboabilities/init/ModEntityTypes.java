package tfar.zomboabilities.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tfar.zomboabilities.entity.ClonePlayerEntity;
import tfar.zomboabilities.entity.FireBreathEntity;
import tfar.zomboabilities.entity.IceSpikeEntity;

public class ModEntityTypes {

    public static final EntityType<ClonePlayerEntity> CLONE_PLAYER = EntityType.Builder.of(ClonePlayerEntity::new, MobCategory.MISC).sized(.8f,1.8f).build("");
    public static final EntityType<FireBreathEntity> FIRE_BREATH = EntityType.Builder.<FireBreathEntity>of(FireBreathEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build("");
    public static final EntityType<IceSpikeEntity> ICE_SPIKE = EntityType.Builder.<IceSpikeEntity>of(IceSpikeEntity::new, MobCategory.MISC).sized(0.5F, 2F).build("");

}
