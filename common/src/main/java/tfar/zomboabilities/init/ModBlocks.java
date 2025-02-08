package tfar.zomboabilities.init;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import tfar.zomboabilities.block.ForceFieldBlock;

public class ModBlocks {
    public static final Block FORCE_FIELD = new ForceFieldBlock(BlockBehaviour.Properties.of()
            .strength(-1.0F, 3600000.8F)
            .mapColor(MapColor.NONE)
            .noLootTable()
            .noOcclusion()
            .isValidSpawn(ModBlocks::never)
            .noTerrainParticles()
            .pushReaction(PushReaction.BLOCK));


    private static Boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity) {
        return false;
    }
}
