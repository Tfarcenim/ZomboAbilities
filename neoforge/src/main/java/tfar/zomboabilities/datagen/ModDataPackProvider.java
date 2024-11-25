package tfar.zomboabilities.datagen;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import tfar.zomboabilities.ZomboAbilities;

import java.util.List;
import java.util.OptionalLong;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDataPackProvider extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, ModDataPackProvider::dimensionType);


    public ModDataPackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ZomboAbilities.MOD_ID));
    }

    public static void dimensionType(BootstrapContext<DimensionType> context) {
        context.register(ZomboAbilities.DEATH_DIM_TYPE,
                new DimensionType(
                        OptionalLong.of(18000L),
                        false,
                        false,
                        false,
                        false,
                        1,
                        false,
                        false,
                        0,
                        256,
                        128,
                        BlockTags.INFINIBURN_OVERWORLD,
                        BuiltinDimensionTypes.NETHER_EFFECTS,
                        0.1F,
                        new DimensionType.MonsterSettings(false, false, ConstantInt.of(7), 0)
                )
        );
    }

}
