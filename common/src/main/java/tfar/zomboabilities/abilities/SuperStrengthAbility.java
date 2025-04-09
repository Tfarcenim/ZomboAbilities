package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.init.ModMobEffects;

//Pressing R - Within 10 seconds of Pressing R , the Player will have High Knockback when hitting any Mob/Player
//Cooldown - 20 Seconds
//
//Player will have 3 extra hearts
//
//Passive - Player will have Strength 2
public class SuperStrengthAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(ModMobEffects.KNOCKBACK,200));
        applyCooldown(0,400,player);
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
    public void onAdded(ServerPlayer player) {
        super.onAdded(player);
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,MobEffectInstance.INFINITE_DURATION,1,false,false));
        applyAttributeSafely(player, Attributes.MAX_HEALTH,new AttributeModifier(ZomboAbilities.id(getName()),6, AttributeModifier.Operation.ADD_VALUE));
    }

    @Override
    public void onRemoved(ServerPlayer player) {
        super.onRemoved(player);
        player.removeEffect(MobEffects.DAMAGE_BOOST);
        player.getAttribute(Attributes.MAX_HEALTH).removeModifier(ZomboAbilities.id(getName()));
    }
}
