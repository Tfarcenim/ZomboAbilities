package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import tfar.zomboabilities.init.ModMobEffects;

//Pressing R - Player will gain Speed 3 for 20 seconds, Player will have Auto jump so they dont get stuck on blocks,
// Player will also have the Hunger 5 Effect for the 20 seconds.
//Cooldown - 15 seconds
//
//Pressing Y - The Player will Go even faster for 20 seconds, Like a turbo boost, while doing this, the Player can run on water,
// if the player stops while running on the water, they will lose momentum and Sink. They can do this on Lava but it will Do damage to you Overtime.
//Cooldown - 30 Seconds
public class SuperSpeedAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,400,2));
        player.addEffect(new MobEffectInstance(MobEffects.HUNGER,400,4));
        applyCooldown(0,300,player);
    }

    @Override
    public void secondary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,400,4));
        player.addEffect(new MobEffectInstance(ModMobEffects.HYPER_SPEED,400,0));
        applyCooldown(0,600,player);
    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }
}
