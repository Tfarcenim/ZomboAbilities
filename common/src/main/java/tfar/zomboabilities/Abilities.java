package tfar.zomboabilities;

import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.AntiGravityAbility;

import java.util.HashMap;
import java.util.Map;

public class Abilities {

    public static final Map<String, Ability> ABILITIES_BY_NAME = new HashMap<>();
    public static final Ability ANTI_GRAVITY = register(new AntiGravityAbility("anti_gravity"),"anti_gravity");

    static Ability register(Ability ability,String name) {
        ABILITIES_BY_NAME.put(name,ability);
        return ability;
    }

}
