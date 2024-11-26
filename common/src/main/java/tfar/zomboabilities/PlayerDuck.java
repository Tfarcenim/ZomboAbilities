package tfar.zomboabilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface PlayerDuck {

    static PlayerDuck of(Player player) {
        return (PlayerDuck) player;
    }
    default Player cast() {
        return (Player) this;
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
        Inventory newInventory = cast().getInventory();
        SavedInventory oldSavedInventory = old.getSavedInventory();
        for (int i = 0; i < newInventory.getContainerSize(); i++) {
            ItemStack stack = newInventory.getItem(i);
            if (stack.isEmpty()) {
                ItemStack savedStack = oldSavedInventory.getItem(i);
                if (!savedStack.isEmpty()) {
                    newInventory.setItem(i, savedStack);
                }
            }
        }
        oldSavedInventory.clearContent();

    }

    SavedInventory getSavedInventory();

}
