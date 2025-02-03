package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import tfar.zomboabilities.PlayerDuck;

//Pressing R - This Ability allows you to blow up Similar to a creeper, you have a chance not to die when you blow up
//
//Pressing Y - This will Cause a even bigger explosion that will for sure kill you
//Cooldown - 5 seconds
//
//Passive - If you are Struck by Lightning, you will be able to explode without dying for 10 seconds
//You have Silent foot steps
public class ExplosionAbility extends Ability {
    @Override
    public void primary(ServerPlayer player) {
        player.level().explode(player,player.getX(),player.getY(),player.getZ(),3, Level.ExplosionInteraction.MOB);
    }

    @Override
    public void secondary(ServerPlayer player) {
        PlayerDuck playerDuck = PlayerDuck.of(player);
        boolean immune = playerDuck.getExplosionImmunityTimer()>0;
        player.level().explode(immune ? player : null,player.getX(),player.getY(),player.getZ(),5, Level.ExplosionInteraction.TNT);
        applyCooldown(1,5 * 20,player);
    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }
}
