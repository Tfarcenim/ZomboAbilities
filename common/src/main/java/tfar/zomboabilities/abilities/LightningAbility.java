package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.HitResult;
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
    @Override
    public void primary(ServerPlayer player) {
        if (player.serverLevel().isRaining()) {
            HitResult hitResult = Utils.pickEither(player,32,32,1);
        }
    }

    @Override
    public void secondary(ServerPlayer player) {

    }

    @Override
    public void tertiary(ServerPlayer player) {
        AbilityUtils.setLightningChance(player.serverLevel(),AbilityUtils.getLightningChance(player.serverLevel())/4);
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
    }
}
