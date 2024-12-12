package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.PlayerDuck;

public class LaserEyesAbility extends Ability{
    public LaserEyesAbility(String name) {
        super(name);
    }

    @Override
    public void primary(ServerPlayer player) {
        PlayerDuck duck = PlayerDuck.of(player);
        duck.setLaserActive(true);
        System.out.println("Laser Active");
        applyCooldown(0,15 * 20,player);
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
