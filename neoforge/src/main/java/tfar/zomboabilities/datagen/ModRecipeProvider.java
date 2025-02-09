package tfar.zomboabilities.datagen;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilderCustom.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_CHESTPLATE)
                .define('c',Items.CHAIN)
                .pattern("c c")
                .pattern("ccc")
                .pattern("ccc")
                .unlockedBy("impossible", has(Items.COMMAND_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilderCustom.shaped(RecipeCategory.MISC, Items.ENDER_PEARL)
                .define('L',Items.LAPIS_BLOCK)
                .define('p',Items.POPPED_CHORUS_FRUIT)
                .define('c',Items.CHORUS_FRUIT)
                .pattern("LpL")
                .pattern("LcL")
                .pattern("LpL")
                .unlockedBy("impossible", has(Items.COMMAND_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilderCustom.shaped(RecipeCategory.MISC, Items.ELYTRA)
                .define('L',Items.PHANTOM_MEMBRANE)
                .define('p',Items.END_ROD)
                .define('c',Items.NETHER_STAR)
                .pattern("LpL")
                .pattern("LcL")
                .pattern("L L")
                .unlockedBy("impossible", has(Items.COMMAND_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilderCustom.shaped(RecipeCategory.MISC, Items.BELL)
                .define('L',Items.GOLD_INGOT)
                .define('p',Items.GOLD_NUGGET)
                .define('c',Items.STICK)
                .pattern("ccc")
                .pattern("LLL")
                .pattern("LpL")
                .unlockedBy("impossible", has(Items.COMMAND_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilderCustom.shaped(RecipeCategory.MISC, Items.ENCHANTED_GOLDEN_APPLE)
                .define('L',Items.GOLD_BLOCK)
                .define('p',Items.GOLDEN_APPLE)
                .pattern("LLL")
                .pattern("LpL")
                .pattern("LLL")
                .unlockedBy("impossible", has(Items.COMMAND_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilderCustom.shaped(RecipeCategory.MISC, Items.NAME_TAG)
                .define('p',Items.PAPER)
                .define('s',Items.STRING)
                .define('i',Items.INK_SAC)
                .pattern("s  ")
                .pattern(" i ")
                .pattern("  p")
                .unlockedBy("impossible", has(Items.COMMAND_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilderCustom.shaped(RecipeCategory.MISC, Items.SADDLE)
                .define('p',Items.LEATHER)
                .define('s',Items.STRING)
                .define('i',Items.IRON_INGOT)
                .pattern("ppp")
                .pattern("s s")
                .pattern("i i")
                .unlockedBy("impossible", has(Items.COMMAND_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilderCustom.shaped(RecipeCategory.MISC, Items.TOTEM_OF_UNDYING)
                .define('b',Items.BLAZE_POWDER)
                .define('e',Items.EMERALD_BLOCK)
                .define('G',Items.GOLD_BLOCK)
                .define('g',Items.GOLD_INGOT)
                .pattern("beb")
                .pattern("GGG")
                .pattern(" g ")
                .unlockedBy("impossible", has(Items.COMMAND_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilderCustom.shaped(RecipeCategory.MISC, Items.DIAMOND_HORSE_ARMOR)
                .define('l',Items.LEATHER)
                .define('d',Items.DIAMOND)
                .pattern("  d")
                .pattern("dld")
                .pattern("ddd")
                .unlockedBy("impossible", has(Items.COMMAND_BLOCK))
                .save(recipeOutput);
    }
}
