package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.init.ModMobEffects;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CopyAbility extends Ability {
    public CopyAbility(String name) {
        super(name);
    }

    //Pressing R - Pressing R while hitting a Player within 10 seconds, will give you the Abilities of that player for 10 minutes
//Cooldown - 5 minutes
//
    @Override
    public void primary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(ModMobEffects.COPY_ABILITY,200));
        applyCooldown(0,5 * 60 * 20,player);
    }

    @Override
    public void secondary(ServerPlayer player) {

    }

    @Override
    public void tertiary(ServerPlayer player) {
        player.removeEffect(ModMobEffects.COPY_ABILITY);
    }

    //Pressing Y - This will get rid of the ability that you currently have, and put you on Cooldown for 5 minutes
    @Override
    public void quaternary(ServerPlayer player) {
    }

    //Zombie - Slowness 2, Hunger 3, Weakness 2, Player will burn in day.
//Spider - Night Vision, Player will have Weakness 2 in the Day
//Drowned Zombie - Slowness 2, Hunger 3, Weakness 2, Water breathing, Player will burn in day
//Enderman - Player Will gain Strength 1, and Speed 1 when hit by a Mob/Player, Damage in Rain/Water.
//Creeper - Player will have Silent foot steps, Player will gain Strength 3 and Glowing when hit with a Thunder Bolt
//Warden - Player will Gain Blindness 4, Strength 3.
//Iron Golem - Player Gains Slowness 3, Strength 2, Resistance 2.
//Wither Skeleton - Fire Resistance, Wither.
//Magma Cube - Fire Resistance, Jump boost 3,
//Slime - Jump Boost 3
//Blaze - Levitation, Fire Resistance
//Guardian - Water breathing, Dolphins Grace
//Elder Guardian - Water breathing, Dolphins grace, Resistance 2.

    static final int TIME = 400;
    public static final Consumer<ServerPlayer> ZOMBIE = player -> {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,TIME,1));
        player.addEffect(new MobEffectInstance(MobEffects.HUNGER,TIME,2));
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,TIME,1));
    };

    public static final Consumer<ServerPlayer> SPIDER = player -> {
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,TIME,0));
    };

    public static final Consumer<ServerPlayer> DROWNED = ZOMBIE.andThen(player -> player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,TIME,0)));

    public static final Consumer<ServerPlayer> ENDERMAN = player -> {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,TIME,0));
    };

    public static final Consumer<ServerPlayer> CREEPER = player -> {
        player.addEffect(new MobEffectInstance(ModMobEffects.QUIET_STEP,TIME,0));
    };

    public static final Consumer<ServerPlayer> WARDEN = player -> {
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,TIME,3));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,TIME,2));
    };

    public static final Consumer<ServerPlayer> IRON_GOLEM = player -> {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,TIME,2));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,TIME,1));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,TIME,1));
    };


    public static final Consumer<ServerPlayer> WITHER = player -> {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,TIME,0));
        player.addEffect(new MobEffectInstance(MobEffects.WITHER,TIME,0));
    };

    public static final Consumer<ServerPlayer> SLIME = player -> {
        player.addEffect(new MobEffectInstance(MobEffects.JUMP,TIME,2));
    };

    public static final Consumer<ServerPlayer> MAGMA_CUBE = SLIME.andThen(player -> player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,TIME,0)));

    public static final Consumer<ServerPlayer> BLAZE = player -> {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,TIME,0));
        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION,TIME,0));
    };

    public static final Consumer<ServerPlayer> GUARDIAN = player -> {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,TIME,0));
        player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE,TIME,0));
    };

    public static final Consumer<ServerPlayer> ELDER_GUARDIAN = GUARDIAN.andThen(player -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,TIME,1)));

    public static final Map<EntityType<?>,Consumer<ServerPlayer>> MAP = new HashMap<>();
    static {
        MAP.put(EntityType.ZOMBIE,ZOMBIE);
        MAP.put(EntityType.SPIDER,SPIDER);
        MAP.put(EntityType.DROWNED,DROWNED);
        MAP.put(EntityType.ENDERMAN,ENDERMAN);
        MAP.put(EntityType.CREEPER,CREEPER);
        MAP.put(EntityType.WARDEN,WARDEN);
        MAP.put(EntityType.IRON_GOLEM,IRON_GOLEM);
        MAP.put(EntityType.WITHER_SKELETON,WITHER);
        MAP.put(EntityType.MAGMA_CUBE,MAGMA_CUBE);
        MAP.put(EntityType.SLIME,SLIME);
        MAP.put(EntityType.BLAZE,BLAZE);
    }

}
