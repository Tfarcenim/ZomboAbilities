package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import tfar.zomboabilities.init.ModMobEffects;
//Pressing R - By Pressing R the player will be able to walk Through Blocks for 10 seconds,
// When there are 5 seconds left to turn tangible again it will say it in the Players Hot bar,
// While intagible player cannot be hit, normal can they hit anyone, any item they hold in there hand will just drop out there inventory..

public class InvisibilityAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,35 * 20,0,false,false));
        applyCooldown(0,50 * 20,player);
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
