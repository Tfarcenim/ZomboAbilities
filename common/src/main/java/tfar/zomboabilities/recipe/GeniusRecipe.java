package tfar.zomboabilities.recipe;

import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import tfar.zomboabilities.init.ModRecipeSerializers;
import tfar.zomboabilities.menu.GeniusCraftingContainer;

public class GeniusRecipe extends ShapedRecipe {

    public GeniusRecipe(ShapedRecipe recipe) {
        super("genius",recipe.category(), recipe.pattern, recipe.getResultItem(null));
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input instanceof GeniusCraftingContainer.GeniusCraftingInput) {
            return super.matches(input, level);
        }
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.GENIUS;
    }
}
