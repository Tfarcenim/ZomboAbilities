package tfar.zomboabilities.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.zomboabilities.ZomboAbilities;

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
    }
}
