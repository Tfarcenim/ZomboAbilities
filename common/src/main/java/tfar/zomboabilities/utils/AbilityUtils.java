package tfar.zomboabilities.utils;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.data.AbilityData;
import tfar.zomboabilities.platform.Services;

import java.util.Optional;

public class AbilityUtils {

    public static boolean hasAbility(Entity entity, Ability ability) {
        return Services.PLATFORM.getAData(entity).ability().map(ability1 -> ability1 == ability).orElse(false);
    }

    public static Optional<Ability> getAbility(Entity entity) {
        return Services.PLATFORM.getAData(entity).ability();
    }

    public static void setAbility(Entity entity,@Nullable Ability ability) {
        Services.PLATFORM.setAData(entity,Services.PLATFORM.getAData(entity).modify(AbilityData.CHANGE_ABILITY,ability));
    }

    public static void removeAbility(Entity entity) {
        setAbility(entity,null);
    }
}
