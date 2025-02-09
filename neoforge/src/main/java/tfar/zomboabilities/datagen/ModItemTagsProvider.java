package tfar.zomboabilities.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.init.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {


    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags,  @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, ZomboAbilities.MOD_ID, existingFileHelper);
    }

    //Totems
//Gold Blocks
//Golden Apples
//Enchanted Golden Apples
//Clock
//Golden Nuggets
//Golden Carrots
//Bells
//Golden Horse Armor
//Any kind of Golden Armor
//Any kind of Golden Tools
//Spectral Arrows
//Gold Pressure Plate
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.MIDAS_IMMUNE).addTag(ItemTags.PIGLIN_LOVED).add(Items.SPECTRAL_ARROW);
    }
}
