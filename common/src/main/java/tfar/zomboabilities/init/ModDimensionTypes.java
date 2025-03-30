package tfar.zomboabilities.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;
import tfar.zomboabilities.ZomboAbilities;

public class ModDimensionTypes {
    public static final ResourceKey<DimensionType> DEATH = ResourceKey.create(Registries.DIMENSION_TYPE, ZomboAbilities.id("death"));
    public static final ResourceKey<DimensionType> POCKET_DIMENSION = ResourceKey.create(Registries.DIMENSION_TYPE, ZomboAbilities.id("pocket_dimension"));

}
