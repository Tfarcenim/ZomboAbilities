package tfar.zomboabilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public interface PlayerDuck {

    static PlayerDuck of(Player player) {
        return (PlayerDuck) player;
    }

    void setLives(int lives);
    int getLives();
    default void addLives(int lives) {
        setLives(getLives() + lives);
    }
    default void loseLife() {
        addLives(-1);
    }

    default void copyFrom(ServerPlayer oldPlayer) {
        PlayerDuck old = of(oldPlayer);
        setLives(old.getLives());
    }
}
