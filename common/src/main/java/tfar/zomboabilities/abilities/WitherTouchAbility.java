package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import tfar.zomboabilities.init.ModMobEffects;

////Pressing R - This Ability allows you to Destroy any item currently in your hand,
// Also everything you touch within 10 seconds will break, but it wont drop anything, if you have a flower in your hand,
// it will turn without a wither flower, if you have a skeleton head in ur hand it will turn into a wither skeleton head,
// Use this on a Sand and it will turn into Soul sand, use this on dirt and it will turn into Soul Soil,
// And type of Basic tool you try t touch will turn into the stone version, If you hit anyone also,
// they will gain the wither effect for 10 seconds.
public class WitherTouchAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(ModMobEffects.WITHER_TOUCH,35 * 20,0,false,false));
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
