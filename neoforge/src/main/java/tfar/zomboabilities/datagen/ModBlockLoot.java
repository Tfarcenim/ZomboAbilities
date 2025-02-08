package tfar.zomboabilities.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import tfar.zomboabilities.init.ModBlocks;

import java.util.List;
import java.util.Set;

public class ModBlockLoot extends BlockLootSubProvider {
    protected ModBlockLoot(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return List.of(ModBlocks.FORCE_FIELD);
    }
}
