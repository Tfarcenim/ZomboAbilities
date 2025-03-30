package tfar.zomboabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tfar.zomboabilities.init.ModBlockEntityTypes;

public class PocketDimensionPortalBlockEntity extends BlockEntity {
    public PocketDimensionPortalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public PocketDimensionPortalBlockEntity(BlockPos pos, BlockState blockState) {
        this(ModBlockEntityTypes.POCKET_DIMENSION_PORTAL, pos, blockState);
    }
}
