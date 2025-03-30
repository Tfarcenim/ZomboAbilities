package tfar.zomboabilities.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import tfar.zomboabilities.ZomboAbilities;

public class ModLevels {
    public static final ResourceKey<Level> DEATH = ResourceKey.create(Registries.DIMENSION, ZomboAbilities.id("death"));
    public static final ResourceKey<Level> POCKET_DIMENSION = ResourceKey.create(Registries.DIMENSION, ZomboAbilities.id("pocket_dimension"));
}
