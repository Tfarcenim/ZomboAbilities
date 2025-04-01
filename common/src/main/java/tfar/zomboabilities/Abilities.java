package tfar.zomboabilities;

import tfar.zomboabilities.abilities.*;

import java.util.HashMap;
import java.util.Map;

public class Abilities {

    public static final Map<String, Ability> ABILITIES_BY_NAME = new HashMap<>();
    public static final Ability NONE = register(new Ability.NoAbility(),"none");
    public static final Ability ANTI_GRAVITY = register(new AntiGravityAbility(),"anti_gravity");
    public static final Ability MIMIC = register(new CopyAbility(),"mimic");
    public static final Ability LASER_EYES = register(new LaserEyesAbility(),"laser_eyes");
    public static final Ability MERMAN = register(new MermanAbility(),"merman");
    public static final Ability BOOGIE_WOOGIE = register(new BoogieWoogieAbility(),"boogie_woogie");
    public static final Ability CONSTRUCTION = register(new ConstructionAbility(),"construction");
    public static final Ability OBJECT_DUPLICATION = register(new ObjectDuplicationAbility(),"object_duplication");
    public static final Ability DUPLICATE_CLONES = register(new DuplicateClonesAbility(),"duplicate_clones");
    public static final Ability ENDERMAN_GENETICS = register(new EndermanGeneticsAbility().hurtByWater(),"enderman_genetics");
    public static final Ability EXPLOSION = register(new ExplosionAbility(),"explosion");
    public static final Ability FIRE_MANIPULATION = register(new FireManipulationAbility(),"fire_manipulation");
    public static final Ability FLIGHT = register(new FlightAbility(),"flight");
    public static final Ability FORCE_FIELD = register(new ForceFieldAbility(),"force_field");
    public static final Ability GENIUS = register(new GeniusAbility(),"genius");
    public static final Ability GOLD_TOUCH = register(new GoldTouchAbility(),"gold_touch");
    public static final Ability ICE_MANIPULATION = register(new IceManipulationAbility(),"ice_manipulation");
    public static final Ability ILLUSION_CREATION = register(new IllusionCreationAbility(),"illusion_creation");
    public static final Ability INFINITY = register(new InfinityAbility(),"infinity");
    public static final Ability INTANGIBILITY = register(new IntangibilityAbility(),"intangibility");
    public static final Ability INVISIBILITY = register(new InvisibilityAbility(),"invisibility");
    public static final Ability WITHER_TOUCH = register(new WitherTouchAbility(),"wither_touch");
    public static final Ability OBJECT_RESTORATION = register(new ObjectRestorationAbility(),"object_restoration");
    public static final Ability LIFE_GIVER = register(new LiveGiverAbility(),"life_giver");
    public static final Ability LIGHT_MANIPULATION = register(new LightManipulationAbility(),"light_manipulation");
    public static final Ability LIGHTNING = register(new LightningAbility(),"lightning");
    public static final Ability MAGNETISM = register(new MagnetAbility(),"magnetism");
    public static final Ability MOB_ABSORB = register(new MobAbsorbAbility(),"mob_absorb");
    public static final Ability SHAPE_SHIFT = register(new ShapeShiftAbility(),"shape_shift");
    public static final Ability PLANT_MANIPULATION = register(new PlantManipulationAbility(),"plant_manipulation");
    public static final Ability AIR_MANIPULATION = register(new AirManipulationAbility(),"air_manipulation");
    public static final Ability ORE_ABSORB = register(new OreAbsorbAbility(),"ore_absorb");
    public static final Ability POCKET_DIMENSION = register(new PocketDimensionAbility(),"pocket_dimension");
    public static final Ability POISON_TOUCH = register(new PoisonTouchAbility(),"poison_touch");
    public static final Ability REGENERATION = register(new RegenerationAbility(),"regeneration");
    public static final Ability SAND_BODY = register(new SandBodyAbility().hurtByWater(),"sand_body");
    public static final Ability SHRINK = register(new ShrinkAbility(),"shrink");


    static Ability register(Ability ability,String name) {
        ability.setName(name);
        ABILITIES_BY_NAME.put(name,ability);
        return ability;
    }

}
