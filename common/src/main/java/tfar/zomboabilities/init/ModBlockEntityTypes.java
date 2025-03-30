package tfar.zomboabilities.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import tfar.zomboabilities.PocketDimensionPortalBlockEntity;
import tfar.zomboabilities.ZomboAbilities;

public class ModBlockEntityTypes {

    public static final BlockEntityType<PocketDimensionPortalBlockEntity> POCKET_DIMENSION_PORTAL = register("clone_player",
            BlockEntityType.Builder.of(PocketDimensionPortalBlockEntity::new,));

    private static <T extends Entity> BlockEntityType<T> register(String key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, ZomboAbilities.id(key), builder.build(key));
    }

}
