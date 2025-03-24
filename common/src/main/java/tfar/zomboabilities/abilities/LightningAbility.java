package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import tfar.zomboabilities.utils.AbilityUtils;
import tfar.zomboabilities.utils.Utils;

//Lightning - This Ability can only be used While its raining
//
//Pressing R - Player can summon Thunderbolts, this has No cooldown
//
//Pressing Y - Will summon Constant Thunderbolts in a circle around the player for 5 seconds
//Cooldown - 10 seconds
//
//Pressing T - Will make Thunder 3x more Common around the server only while its raining, this will last for 20 seconds
//
//Passive - Player gets Speed Boost 1 in Rain
//
public class LightningAbility extends Ability{

    public static final int CHANCE = 4;

    @Override
    public void primary(ServerPlayer player) {
        if (player.serverLevel().isRaining()) {
            HitResult hitResult = Utils.pickEither(player,32,32,1);
            Vec3 location = hitResult.getLocation();
            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(player.serverLevel());
            if (lightningbolt != null) {
                lightningbolt.moveTo(location);
                player.serverLevel().addFreshEntity(lightningbolt);
            }
        }
    }

    @Override
    public void secondary(ServerPlayer player) {
        AbilityUtils.setCircleLightningTimer(player,200);
    }

    @Override
    public void tertiary(ServerPlayer player) {
        AbilityUtils.setLightningChance(player.serverLevel(),AbilityUtils.getLightningChance(player.serverLevel())/CHANCE);
        AbilityUtils.setLightningTimer(player.serverLevel(),400);
    }

    @Override
    public void quaternary(ServerPlayer player) {

    }

    @Override
    public void tickAbility(ServerPlayer player) {
        super.tickAbility(player);
        if (player.isInRain()) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,10,0,false,false));
        }
        int timer = AbilityUtils.getCircleLightningTimer(player);
        if (timer > 0) {
            timer--;
            if (player.isInRain() && timer % 2 == 0) {
                double angle = Math.PI * 2 * Math.random();
                int dist = 9;
                int x = (int) (player.getX() + dist * Mth.sin((float) angle));
                int z =  (int) (player.getZ() + dist * Mth.cos((float) angle));
                int y = player.serverLevel().getHeight(Heightmap.Types.MOTION_BLOCKING,x,z);

                LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(player.serverLevel());
                if (lightningbolt != null) {
                    lightningbolt.moveTo(x,y,z);
                    player.serverLevel().addFreshEntity(lightningbolt);
                }
            }
            AbilityUtils.setCircleLightningTimer(player,timer);
        }
    }
}
