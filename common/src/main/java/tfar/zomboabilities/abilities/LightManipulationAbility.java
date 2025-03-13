package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import tfar.zomboabilities.data.LightManipulationData;
import tfar.zomboabilities.utils.AbilityUtils;
import tfar.zomboabilities.utils.Utils;

//Light Manipulation
//
//Pressing R - This Ability allows you to shoot a beam of Light at the person, the Light has a chance to give the person Blindness for 10 seconds
//Cooldown - 10 Seconds (After 3 uses)
public class LightManipulationAbility extends Ability{//todo sync light
    @Override
    public void primary(ServerPlayer player) {
        EntityHitResult result = Utils.pickEntity(player, 100, 100, 1);
        if (result != null && result.getEntity() instanceof LivingEntity livingTarget) {
            livingTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,10 * 20));
            LightManipulationData data = AbilityUtils.getLightManipulationData(player);
            data = data.decrementUses();
            AbilityUtils.setLightManipulationData(player,new LightManipulationData(10,data.uses()));
            if (data.uses() == 0) {
                applyCooldown(0,200,player);
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
    public void tickAbility(ServerPlayer player) {
        super.tickAbility(player);
        LightManipulationData data = AbilityUtils.getLightManipulationData(player);
        if (data.uses() ==  0 && AbilityUtils.getCooldowns(player)[0] == 0) {
            AbilityUtils.setLightManipulationData(player,new LightManipulationData(0,3));
        }
    }
}
