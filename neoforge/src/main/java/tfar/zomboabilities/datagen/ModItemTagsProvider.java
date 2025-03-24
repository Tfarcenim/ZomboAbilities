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

    ////Any Iron Tools,Any Iron Armor,Iron Blocks,Anvils,Buckets,Compass
    ////Cauldron
    ////Iron Pressure Plate,Flint and Steel,Iron Trap door
    ////Iron bar
    ////Iron Nugget
    ////Iron Door
    ////Minecart
    ////Sheers
    ////Shields
    ////Rails
    ////Hopper
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.MIDAS_IMMUNE).addTag(ItemTags.PIGLIN_LOVED).add(Items.SPECTRAL_ARROW);
        tag(ModTags.Items.ATTRACTED).add(Items.IRON_AXE,Items.IRON_HOE,Items.IRON_PICKAXE,Items.IRON_SHOVEL,Items.IRON_SWORD,
                Items.IRON_HELMET,Items.IRON_CHESTPLATE,Items.IRON_LEGGINGS,Items.IRON_BOOTS,
                Items.IRON_BLOCK,Items.ANVIL,Items.CHIPPED_ANVIL,Items.DAMAGED_ANVIL,Items.BUCKET,Items.COMPASS,
                Items.CAULDRON,Items.HEAVY_WEIGHTED_PRESSURE_PLATE,Items.FLINT_AND_STEEL,Items.IRON_TRAPDOOR,
                Items.IRON_INGOT,Items.IRON_NUGGET,Items.IRON_DOOR,Items.MINECART,Items.SHEARS,Items.SHIELD,Items.RAIL
                ,Items.HOPPER
        );
    }
}
