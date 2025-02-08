package tfar.zomboabilities.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import tfar.zomboabilities.init.ModRecipeSerializers;

import javax.annotation.Nonnull;

public class GeniusRecipe extends ShapedRecipe {

    public GeniusRecipe(ShapedRecipe recipe) {
        super("genius",recipe.category(), recipe.pattern, recipe.getResultItem(null));
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return super.matches(input, level);
    }

    @Nonnull
    @Override
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider access) {
        ItemStack newBag = super.assemble(inv,access).copy();
        return newBag;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.GENIUS;
    }
}
