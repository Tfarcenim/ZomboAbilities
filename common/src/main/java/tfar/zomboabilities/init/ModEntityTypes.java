package tfar.zomboabilities.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tfar.zomboabilities.entity.ClonePlayerEntity;

public class ModEntityTypes {

    public static final EntityType<ClonePlayerEntity> CLONE_PLAYER = EntityType.Builder.of(ClonePlayerEntity::new, MobCategory.MISC).sized(.8f,1.8f).build("");

}
