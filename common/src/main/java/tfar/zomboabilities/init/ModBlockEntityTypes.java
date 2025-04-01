package tfar.zomboabilities.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import tfar.zomboabilities.PocketDimensionPortalBlockEntity;
import tfar.zomboabilities.ZomboAbilities;

public class ModBlockEntityTypes {

    public static final BlockEntityType<PocketDimensionPortalBlockEntity> POCKET_DIMENSION_PORTAL = register("pocket_dimension_portal",
            BlockEntityType.Builder.of(PocketDimensionPortalBlockEntity::new,ModBlocks.POCKET_DIMENSION_PORTAL));

    private static <T extends BlockEntity> BlockEntityType<T> register(String key, BlockEntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ZomboAbilities.id(key), builder.build(null));
    }
}
