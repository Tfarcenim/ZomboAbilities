package tfar.zomboabilities.utils;

import net.minecraft.world.entity.Entity;
import tfar.zomboabilities.platform.Services;

public class LivesUtils {

    public static void setLives(Entity entity,int lives) {
        Services.PLATFORM.setLData(entity,Services.PLATFORM.getLData(entity).withLives(lives));
    }

    public static int getLives(Entity entity) {
        return Services.PLATFORM.getLData(entity).lives();
    }

    public static void addLives(Entity entity,int count) {
        setLives(entity,getLives(entity) +count);
    }

    public static void loseLife(Entity entity) {
        addLives(entity, -1);
    }

}
