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
    public static final Ability BOOGIE_WOOGIE = register(new BoogieWoogieAbility(),"boogie_woogie");
    public static final Ability CONSTRUCTION = register(new ConstructionAbility(),"construction");
    public static final Ability OBJECT_DUPLICATION = register(new ObjectDuplicationAbility(),"object_duplication");
    public static final Ability DUPLICATE_CLONES = register(new DuplicateClonesAbility(),"duplicate_clones");
    public static final Ability ENDERMAN_GENETICS = register(new EndermanGeneticsAbility(),"enderman_genetics");


    static Ability register(Ability ability,String name) {
        ability.setName(name);
        ABILITIES_BY_NAME.put(name,ability);
        return ability;
    }

}
