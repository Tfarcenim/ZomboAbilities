package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.EntityHitResult;
import tfar.zomboabilities.utils.Utils;

//Light Manipulation
//
//Pressing R - This Ability allows you to shoot a beam of Light at the person, the Light has a chance to give the person Blindness for 10 seconds
//Cooldown - 10 Seconds (After 3 uses)
public class LightManipulationAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        EntityHitResult result = Utils.pickEntity(player, 100, 100, 1);
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
