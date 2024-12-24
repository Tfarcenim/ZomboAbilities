package tfar.zomboabilities.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import tfar.zomboabilities.ZomboAbilities;

public class Tags {
    public static final TagKey<Block> GLASS_BLOCKS_CHEAP = tag("glass_blocks/cheap");
    public static final TagKey<EntityType<?>> MERMAN_FRIENDLY = modTag("merman_friendly");

    private static TagKey<Block> tag(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", name));
    }

    private static TagKey<EntityType<?>> modTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ZomboAbilities.id(name));
    }

}
