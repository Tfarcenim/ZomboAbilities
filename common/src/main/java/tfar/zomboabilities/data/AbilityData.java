package tfar.zomboabilities.data;

import com.mojang.serialization.Codec;
import tfar.zomboabilities.abilities.Ability;

import java.util.Optional;

public record AbilityData(Optional<Ability> ability) {
    public static final Codec<AbilityData> CODEC = null;

    public AbilityData() {
        this(Optional.empty());
    }


}
