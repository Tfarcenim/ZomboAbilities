package tfar.zomboabilities.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import tfar.zomboabilities.ZomboAbilities;

public class ModTags {
    public static final TagKey<Block> GLASS_BLOCKS_CHEAP = common("glass_blocks/cheap");

    private static TagKey<Block> common(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", name));
    }


    public static class EntityTypes {
        /**
         * Entities should be included in this tag if they are not allowed to be picked up by items or grabbed in a way
         * that a player can easily move the entity to anywhere they want. Ideal for special entities that should not
         * be able to be put into a mob jar for example.
         */
        public static final TagKey<EntityType<?>> CAPTURING_NOT_SUPPORTED = common("capturing_not_supported");

        public static final TagKey<EntityType<?>> MERMAN_FRIENDLY = mod("merman_friendly");
        public static final TagKey<EntityType<?>> LIFE_GIVER_WHITELIST = mod("life_giver_whitelist");


        private static TagKey<EntityType<?>> mod(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ZomboAbilities.id(name));
        }

        private static TagKey<EntityType<?>> common(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", name));
        }

    }

    public static class Items {
        public static final TagKey<Item> MIDAS_IMMUNE = mod("midas_immune");
        public static final TagKey<Item> ATTRACTED = mod("attracted");


        private static TagKey<Item> mod(String name) {
            return TagKey.create(Registries.ITEM, ZomboAbilities.id(name));
        }
    }

}
