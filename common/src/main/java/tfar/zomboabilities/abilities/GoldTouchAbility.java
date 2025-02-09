package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class GoldTouchAbility extends Ability {

    public static final Map<Item,Item> MIDAS_TRANSFORM = new HashMap<>();

    static {
        MIDAS_TRANSFORM.put(Items.DIAMOND_BLOCK,Items.GOLD_BLOCK);
        MIDAS_TRANSFORM.put(Items.EMERALD_BLOCK,Items.GOLD_BLOCK);
        MIDAS_TRANSFORM.put(Items.IRON_BLOCK,Items.GOLD_BLOCK);
        MIDAS_TRANSFORM.put(Items.LAPIS_BLOCK,Items.GOLD_BLOCK);
        MIDAS_TRANSFORM.put(Items.NETHERITE_BLOCK,Items.GOLD_BLOCK);
        MIDAS_TRANSFORM.put(Items.REDSTONE_BLOCK,Items.GOLD_BLOCK);

        MIDAS_TRANSFORM.put(Items.IRON_NUGGET,Items.GOLD_NUGGET);

        MIDAS_TRANSFORM.put(Items.APPLE,Items.GOLDEN_APPLE);
        MIDAS_TRANSFORM.put(Items.CARROT,Items.GOLDEN_CARROT);

        MIDAS_TRANSFORM.put(Items.LEATHER_HORSE_ARMOR,Items.GOLDEN_HORSE_ARMOR);
        MIDAS_TRANSFORM.put(Items.IRON_HORSE_ARMOR,Items.GOLDEN_HORSE_ARMOR);
        MIDAS_TRANSFORM.put(Items.DIAMOND_HORSE_ARMOR,Items.GOLDEN_HORSE_ARMOR);

        MIDAS_TRANSFORM.put(Items.WOODEN_PICKAXE,Items.GOLDEN_PICKAXE);
        MIDAS_TRANSFORM.put(Items.STONE_PICKAXE,Items.GOLDEN_PICKAXE);
        MIDAS_TRANSFORM.put(Items.IRON_PICKAXE,Items.GOLDEN_PICKAXE);
        MIDAS_TRANSFORM.put(Items.DIAMOND_PICKAXE,Items.GOLDEN_PICKAXE);
        MIDAS_TRANSFORM.put(Items.NETHERITE_PICKAXE,Items.GOLDEN_PICKAXE);

        MIDAS_TRANSFORM.put(Items.WOODEN_AXE,Items.GOLDEN_AXE);
        MIDAS_TRANSFORM.put(Items.STONE_AXE,Items.GOLDEN_AXE);
        MIDAS_TRANSFORM.put(Items.IRON_AXE,Items.GOLDEN_AXE);
        MIDAS_TRANSFORM.put(Items.DIAMOND_AXE,Items.GOLDEN_AXE);
        MIDAS_TRANSFORM.put(Items.NETHERITE_AXE,Items.GOLDEN_AXE);

        MIDAS_TRANSFORM.put(Items.WOODEN_SHOVEL,Items.GOLDEN_SHOVEL);
        MIDAS_TRANSFORM.put(Items.STONE_SHOVEL,Items.GOLDEN_SHOVEL);
        MIDAS_TRANSFORM.put(Items.IRON_SHOVEL,Items.GOLDEN_SHOVEL);
        MIDAS_TRANSFORM.put(Items.DIAMOND_SHOVEL,Items.GOLDEN_SHOVEL);
        MIDAS_TRANSFORM.put(Items.NETHERITE_SHOVEL,Items.GOLDEN_SHOVEL);

        MIDAS_TRANSFORM.put(Items.WOODEN_HOE,Items.GOLDEN_HOE);
        MIDAS_TRANSFORM.put(Items.STONE_HOE,Items.GOLDEN_HOE);
        MIDAS_TRANSFORM.put(Items.IRON_HOE,Items.GOLDEN_HOE);
        MIDAS_TRANSFORM.put(Items.DIAMOND_HOE,Items.GOLDEN_HOE);
        MIDAS_TRANSFORM.put(Items.NETHERITE_HOE,Items.GOLDEN_HOE);

        MIDAS_TRANSFORM.put(Items.WOODEN_SWORD,Items.GOLDEN_SWORD);
        MIDAS_TRANSFORM.put(Items.STONE_SWORD,Items.GOLDEN_SWORD);
        MIDAS_TRANSFORM.put(Items.IRON_SWORD,Items.GOLDEN_SWORD);
        MIDAS_TRANSFORM.put(Items.DIAMOND_SWORD,Items.GOLDEN_SWORD);
        MIDAS_TRANSFORM.put(Items.NETHERITE_SWORD,Items.GOLDEN_SWORD);

        MIDAS_TRANSFORM.put(Items.TIPPED_ARROW,Items.SPECTRAL_ARROW);
        MIDAS_TRANSFORM.put(Items.ARROW,Items.SPECTRAL_ARROW);
    }

    @Override
    public void primary(ServerPlayer player) {

    }

    @Override
    public void secondary(ServerPlayer player) {

    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }
}
//Passive - Anything that comes into the players inventory will be turned into a Gold ingot, the only things not effected are
//
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
//
//Iron Blocks, Diamond Blocks, Emerald Blocks, Netherite Blocks, Redstone blocks, Lapis Blocks, Will all turn into Gold Blocks.
//
//Regular Apples will turn into Golden Apples
//Regular Carrots will turn into Golden carrots
//Iron nuggets will turn into Gold Nuggets
//Any kind of Horse Armor will turn into Gold Horse armor
//Any kind of Basic Tools will turn into Gold tools
//Arrows will turn into Spectral arrows
//Any kind of Pressure Plate will turn into a Gold Pressure Plate
//
//-