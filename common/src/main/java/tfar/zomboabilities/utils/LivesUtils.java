package tfar.zomboabilities.utils;

import net.minecraft.world.entity.Entity;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.platform.Services;

public class LivesUtils {

    public static void setLives(Entity entity,int lives) {
        Services.PLATFORM.setAttachedValue(entity, CommonDataAttachments.LIVES,lives);
    }

    public static int getLives(Entity entity) {
        return Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.LIVES);
    }

    public static void addLives(Entity entity,int count) {
        setLives(entity,getLives(entity) +count);
    }

    public static void loseLife(Entity entity) {
        addLives(entity, -1);
    }

}
