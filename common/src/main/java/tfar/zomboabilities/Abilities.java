package tfar.zomboabilities;

import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.AntiGravityAbility;
import tfar.zomboabilities.abilities.CopyAbility;

import java.util.HashMap;
import java.util.Map;

public class Abilities {

    public static final Map<String, Ability> ABILITIES_BY_NAME = new HashMap<>();
    public static final Ability ANTI_GRAVITY = register(new AntiGravityAbility("anti_gravity"),"anti_gravity");
    public static final Ability MIMIC = register(new CopyAbility("mimic"),"mimic");

    static Ability register(Ability ability,String name) {
        ABILITIES_BY_NAME.put(name,ability);
        return ability;
    }

}
