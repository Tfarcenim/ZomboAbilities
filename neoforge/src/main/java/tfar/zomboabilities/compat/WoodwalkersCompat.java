package tfar.zomboabilities.compat;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import tocraft.walkers.api.PlayerShape;
import tocraft.walkers.api.variant.ShapeType;

public class WoodwalkersCompat {
    public static void morph(ServerPlayer player, @Nullable LivingEntity living) {
        ShapeType<LivingEntity> type = ShapeType.from(living);

        // Swap to other Shape
        if (type != null) {
            // update Player
            PlayerShape.updateShapes(player,
                    type.create(player.level(), player));
        } else {
            // Swap back to player if server allows it
            PlayerShape.updateShapes(player, null);
        }

        // Refresh player dimensions
        player.refreshDimensions();
    }

    @Nullable
    public static LivingEntity getMorph(Player player) {
        return PlayerShape.getCurrentShape(player);
    }
}
