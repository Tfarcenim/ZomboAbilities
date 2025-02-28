package tfar.zomboabilities.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import tfar.zomboabilities.ZomboAbilities;

public class ModItems {
    public static final Item FORCE_SHIELD = registerItem(ZomboAbilities.id("force_shield"),new ShieldItem(new Item.Properties().durability(15)));

    public static Item registerItem(ResourceLocation key, Item item) {
        return Items.registerItem(ResourceKey.create(BuiltInRegistries.ITEM.key(), key), item);
    }

    public static void init() {

    }

}
