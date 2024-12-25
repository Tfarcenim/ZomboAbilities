package tfar.zomboabilities.abilities;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ObjectDuplicationAbility extends Ability{

    //Object Duplication
//
//Pressing R - This Allows you to duplicate any item currently in the persons hand, All other items will cost 1 Exp to Duplicate, these items wont
//
//Diamond - Cost 5
//Diamond Block - Cost 10
//Netherite - Cost 20
//Netheire block - Cost 30
//Any kind of Diamond Armor - Cost 15
//Any kind of netherite Armor - Cost 30
//Totem of undying - Cost 15
//Emerald - Cost 5
//Any kind of Diamond Tool - 20
//Any kind of Netherite Tool - 20
//Bedrock - 20
//Barrier Block - 50
//

    static final Object2IntMap<Item> COST = new Object2IntOpenHashMap<>();

    static {
        addCosts();
    }

    public static void addCosts() {
        COST.put(Items.DIAMOND,5);
        COST.put(Items.DIAMOND_BLOCK,45);
        COST.put(Items.NETHERITE_INGOT,20);
        COST.put(Items.NETHERITE_BLOCK,180);

        int diamond_armor = 15;

        COST.put(Items.DIAMOND_HELMET,diamond_armor);
        COST.put(Items.DIAMOND_CHESTPLATE,diamond_armor);
        COST.put(Items.DIAMOND_LEGGINGS,diamond_armor);
        COST.put(Items.DIAMOND_BOOTS,diamond_armor);

        int netherite_armor = 30;

        COST.put(Items.NETHERITE_HELMET,netherite_armor);
        COST.put(Items.NETHERITE_CHESTPLATE,netherite_armor);
        COST.put(Items.NETHERITE_LEGGINGS,netherite_armor);
        COST.put(Items.NETHERITE_BOOTS,netherite_armor);

        COST.put(Items.TOTEM_OF_UNDYING,15);

        COST.put(Items.EMERALD,5);
        COST.put(Items.EMERALD_BLOCK,45);

        int diamond_tool = 20;

        COST.put(Items.DIAMOND_AXE,diamond_tool);
        COST.put(Items.DIAMOND_HOE,diamond_tool);
        COST.put(Items.DIAMOND_PICKAXE,diamond_tool);
        COST.put(Items.DIAMOND_SHOVEL,diamond_tool);
        COST.put(Items.DIAMOND_SWORD,diamond_tool);

        int netherite_tool = 30;

        COST.put(Items.NETHERITE_AXE,netherite_tool);
        COST.put(Items.NETHERITE_HOE,netherite_tool);
        COST.put(Items.NETHERITE_PICKAXE,netherite_tool);
        COST.put(Items.NETHERITE_SHOVEL,netherite_tool);
        COST.put(Items.NETHERITE_SWORD,netherite_tool);

        COST.put(Items.BEDROCK,20);
        COST.put(Items.BARRIER,50);

    }

    @Override
    public void primary(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();
        int cost = COST.getOrDefault(stack.getItem(),1);
        if (player.totalExperience >= cost) {
            player.addItem(stack.copyWithCount(1));
            player.giveExperiencePoints(-cost);
        }
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
