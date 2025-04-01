package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

//Passive - The Player will have High Regeneration, Regeneration 3, the only thing is that the Player will take a
//Huge Amount of Damage in the Day time, The Regeneration will turn the Regen 2 if the Player is Under Half there Hunger bars,
//and there Regeneration powers will go away if they have 0 Hunger bars.
public class RegenerationAbility extends Ability {
    @Override
    public void primary(ServerPlayer player) {

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
        if (player.tickCount % 100 ==0) {
            int food = player.getFoodData().getFoodLevel();
            if (food > 10) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,6 * 20,2,false,false));
            } else if (food > 0) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,6 * 20,1,false,false));
            }
        }
    }
}
