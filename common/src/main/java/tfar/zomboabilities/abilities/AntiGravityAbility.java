package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import tfar.zomboabilities.init.ModMobEffects;

public class AntiGravityAbility extends Ability{
    public AntiGravityAbility(String name) {
        super(name);
    }

    //Pressing T - By Pressing this, You will have 10 Seconds of Levitation.
    //Cooldown- 20 Seconds
    @Override
    public void primary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION,200));
        applyCooldown(0,20 * 20,player);
    }

    //Pressing R - By Pressing R, Within the 10 seconds, any Mob/player you touch will float in the air with the levitation effect for 10 seconds.
    //Cooldown - 15 Seconds
    @Override
    public void secondary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(ModMobEffects.FLOATING_TOUCH,200));
        applyCooldown(1,20 * 10,player);
    }

    //Pressing Y - Within the 10 seconds of Pressing This, All Items that the player walks over will begin to float in the air for 10 seconds and drop down.
    // Cooldown - 15 Seconds
    @Override
    public void tertiary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(ModMobEffects.FLOATING_ITEMS,200));
        applyCooldown(2,300,player);
    }

    //Pressing C - Player will gain Slow falling for 10 seconds
//Cooldown - 15 Seconds
    @Override
    public void quaternary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,200));
        applyCooldown(2,300,player);
    }
}
