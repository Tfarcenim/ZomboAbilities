package tfar.zomboabilities.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModDatagen {

    public static void gather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        generator.addProvider(true,new ModDataPackProvider(output,provider));
        generator.addProvider(true,new ModEntityTypeTagsProvider(output,provider,helper));
        generator.addProvider(true,ModLootTableProvider.create(output,provider));
        generator.addProvider(true,new ModBlockStateProvider(output,helper));
        generator.addProvider(true,new ModItemModelProvider(output,helper));
        generator.addProvider(true,new ModRecipeProvider(output,provider));

        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output,provider,helper);
        generator.addProvider(true,blockTagsProvider);
        generator.addProvider(true,new ModItemTagsProvider(output,provider,blockTagsProvider.contentsGetter(),helper));
    }
}
