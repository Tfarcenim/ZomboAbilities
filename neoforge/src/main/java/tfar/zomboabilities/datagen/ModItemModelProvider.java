package tfar.zomboabilities.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.init.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ZomboAbilities.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        ModelFile blockingFile = withExistingParent("force_shield_blocking",modLoc("item/baked_shield_blocking"))
                .texture("1",modLoc("item/force_shield"));

        getBuilder("force_shield")
                .parent(getExistingFile(modLoc("item/baked_shield")))
                .texture("1",modLoc("item/force_shield"))
                .override()
                .predicate(mcLoc("blocking"),1)
                .model(blockingFile)
                .end();

        generatedItem(ModItems.MOB_CAPSULE,ResourceLocation.withDefaultNamespace("item/heart_of_the_sea"));
    }

    private void generatedItem(Item item , ResourceLocation texture) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        singleTexture(path, ResourceLocation.withDefaultNamespace("item/generated"),
                "layer0", texture);
    }

    private void generatedItem(Item item) {
        generatedItem(item,modLoc("item/"+BuiltInRegistries.ITEM.getKey(item).getPath()));
    }
}
