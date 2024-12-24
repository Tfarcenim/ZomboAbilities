package tfar.zomboabilities;

import tfar.zomboabilities.abilities.*;

import java.util.HashMap;
import java.util.Map;

public class Abilities {

    public static final Map<String, Ability> ABILITIES_BY_NAME = new HashMap<>();
    public static final Ability ANTI_GRAVITY = register(new AntiGravityAbility(),"anti_gravity");
    public static final Ability MIMIC = register(new CopyAbility(),"mimic");
    public static final Ability LASER_EYES = register(new LaserEyesAbility(),"laser_eyes");
    public static final Ability MERMAN = register(new MermanAbility(),"merman");

    static Ability register(Ability ability,String name) {
        ability.setName(name);
        ABILITIES_BY_NAME.put(name,ability);
        return ability;
    }

}
