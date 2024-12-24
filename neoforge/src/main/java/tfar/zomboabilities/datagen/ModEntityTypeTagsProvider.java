package tfar.zomboabilities.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.init.Tags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, ZomboAbilities.MOD_ID, existingFileHelper);
    }

    ////Dolphins, Guardians, Elder Guardians, Drowned Zombies, Will not attack the player.
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.MERMAN_FRIENDLY).add(EntityType.DOLPHIN,EntityType.GUARDIAN,EntityType.ELDER_GUARDIAN,EntityType.DROWNED);
    }
}
