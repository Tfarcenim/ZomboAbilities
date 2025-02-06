package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import tfar.zomboabilities.init.ModMobEffects;

public class FlightAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(ModMobEffects.FLIGHT,15 * 20,0,false,false));
        applyCooldown(0,40 * 20,player);
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


}
