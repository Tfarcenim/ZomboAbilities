package tfar.zomboabilities.init;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
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

    public static final Block POINTED_ICE = new PointedDripstoneBlock(      BlockBehaviour.Properties.of()
            .mapColor(MapColor.ICE)
            .forceSolidOn()
            .friction(0.98F)
            .randomTicks()
            .strength(0.5F)
            .sound(SoundType.GLASS)
            .noOcclusion()
            .isValidSpawn(ModBlocks::never)
            .isRedstoneConductor(ModBlocks::never));

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static Boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity) {
        return false;
    }
}
