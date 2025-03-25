package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.AbilityUtils;

//Pressing R - By Hitting a Mob and Pressing R within 10 Seconds of hitting them, The Player will be able to transform into the mob,
// the Player will also be able to use the abilities of the Mob.the Time limit for being the Mob is 5 minutes.You can Press R again to detransform.
//Cooldown - 40 Seconds
public class ShapeShiftAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        LivingEntity morph = Services.PLATFORM.getMorph(player);

        if (morph != null) {
            Services.PLATFORM.demorph(player);
            applyCooldown(0,40 * 20,player);
        } else {
            LivingEntity lastHurtMob = player.getLastHurtMob();
            if (lastHurtMob != null) {
                Services.PLATFORM.morph(player, lastHurtMob);
                AbilityUtils.setShapeShiftTimer(player, 60 * 20 * 5);
            }
        }
    }

    @Override
    public void secondary(ServerPlayer player) {

    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }

    @Override
    public void onRemoved(ServerPlayer player) {
        super.onRemoved(player);
        AbilityUtils.setShapeShiftTimer(player,0);
        Services.PLATFORM.demorph(player);
    }

    @Override
    public void tickAbility(ServerPlayer player) {
        super.tickAbility(player);
        int timer = AbilityUtils.getShapeShiftTimer(player);
        if (timer>0) {
            timer--;
            if (timer == 0) {
                Services.PLATFORM.demorph(player);
                applyCooldown(0,40 * 20,player);
            }
            AbilityUtils.setShapeShiftTimer(player,timer);
        }
    }
}
