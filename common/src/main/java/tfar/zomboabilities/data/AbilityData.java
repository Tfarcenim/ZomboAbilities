package tfar.zomboabilities.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.Nullable;
import tfar.zomboabilities.abilities.Ability;

import java.util.Optional;
import java.util.function.BiFunction;

public record AbilityData(Optional<Ability> ability) {
    public static final Codec<AbilityData> CODEC = RecordCodecBuilder.create(abilityDataInstance -> abilityDataInstance.group(
            Ability.CODEC.optionalFieldOf("ability").forGetter(AbilityData::ability)
    ).apply(abilityDataInstance,AbilityData::new));

    public AbilityData() {
        this(Optional.empty());
    }

    public static final BiFunction<AbilityData,Ability,AbilityData> CHANGE_ABILITY = AbilityData::withAbility;

    public AbilityData withAbility(@Nullable Ability ability) {
        return new AbilityData(Optional.ofNullable(ability));
    }

    public <T> AbilityData modify(BiFunction<AbilityData,T,AbilityData> function,T t) {
        return function.apply(this,t);
    }

}
