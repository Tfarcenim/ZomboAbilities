package tfar.zomboabilities.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import tfar.zomboabilities.ZomboAbilities;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> ACID = ResourceKey.create(Registries.DAMAGE_TYPE, ZomboAbilities.id("acid"));
}
