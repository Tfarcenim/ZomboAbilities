package tfar.zomboabilities.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
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
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.block.ForceFieldBlock;
import tfar.zomboabilities.block.PocketDimensionPortalBlock;

public class ModBlocks {
    public static final Block FORCE_FIELD = register("force_field",new ForceFieldBlock(BlockBehaviour.Properties.of()
            .strength(-1.0F, 3600000.8F)
            .mapColor(MapColor.NONE)
            .noLootTable()
            .noOcclusion()
            .isValidSpawn(ModBlocks::never)
            .noTerrainParticles()
            .pushReaction(PushReaction.BLOCK)));

    public static final Block POINTED_ICE = register("pointed_ice",new PointedDripstoneBlock(      BlockBehaviour.Properties.of()
            .mapColor(MapColor.ICE)
            .forceSolidOn()
            .friction(0.98F)
            .randomTicks()
            .strength(0.5F)
            .sound(SoundType.GLASS)
            .noOcclusion()
            .isValidSpawn(ModBlocks::never)
            .isRedstoneConductor(ModBlocks::never)));

    public static final Block POCKET_DIMENSION_PORTAL = register("pocket_dimension_portal",new PocketDimensionPortalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .randomTicks()
            .strength(0.5F)
            .explosionResistance(1200)
            .sound(SoundType.GLASS)
            .isValidSpawn(ModBlocks::never)
            .isRedstoneConductor(ModBlocks::never)));

    public static Block register(String key, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, ZomboAbilities.id(key), block);
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static Boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    public static void init() {

    }
}
