package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

//Slime genetics
//
//Pressing R - Player will have a Jump 2 Boost for 15 seconds
//Cooldown - 5 Seconds
//
//Pressing Y - Player can make Infinite slime balls
//Passive - Player cannot Take fall damage,
//Player floats when there in water
//Arrows will bounce off the player
//Player has a 20% chance to spawn 2 mini Slimes when hit
//Player will make slime noises everytime they walk and Jump
public class SlimeGenenticsAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.JUMP,300,1));
    }

    @Override
    public void secondary(ServerPlayer player) {
        player.addItem(Items.SLIME_BALL.getDefaultInstance());
    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }

    @Override
    public boolean onAttacked(Player attacked, LivingEntity attacker) {
        if (attacked.getRandom().nextDouble() < .2) {
            EntityType.SLIME.spawn((ServerLevel) attacked.level(), attacked.blockPosition(), MobSpawnType.EVENT);
        }
        return false;
    }
}
