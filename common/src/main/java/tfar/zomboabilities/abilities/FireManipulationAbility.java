package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;

//Pressing R - This ability allows you to shoot a Fire Ball
//Cooldown - 8 Seconds
//
//Pressing T - Similar to laser Eyes the Player will be able to breath fire out there mouth, this will set fire to anything it is touching
//
//Pressing Y - Will Smelt/Cook any Raw meat or ores in your hand
//
//Crouching + Right click - Will light a fire similar to if you had a flint and steel
//
//Passives - You have Permanent Fire Resistance
//Drinking a fire Resistance Potion gives you Regular Golden apple effects
public class FireManipulationAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        Vec3 vec3 = player.getViewVector(1.0F);
        LargeFireball largefireball = new LargeFireball(player.level(), player, vec3.normalize(),1);
        largefireball.setPos(player.getX() + vec3.x * 4.0, player.getY(0.5) + 0.5, largefireball.getZ() + vec3.z * 4.0);
        player.level().addFreshEntity(largefireball);
        applyCooldown(0,8* 20,player);
    }

    @Override
    public void secondary(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        level.sendParticles()
    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }
}
