package tfar.zomboabilities.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.recipe.Serializer2;

public class ModRecipeSerializers {
    public static final Serializer2 GENIUS = register("genius",new Serializer2());

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String key, S recipeSerializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ZomboAbilities.id(key), recipeSerializer);
    }

    public static void init() {
    }
}
