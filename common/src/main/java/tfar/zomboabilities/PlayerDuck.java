package tfar.zomboabilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.DuplicateClonesAbility;

import java.util.function.Consumer;

public interface PlayerDuck {

    static PlayerDuck of(Player player) {
        return (PlayerDuck) player;
    }
    default Player as() {
        return (Player) this;
    }

    void setCopiedAbility(Ability ability);
    Ability getCopiedAbility();
    Consumer<ServerPlayer> getMobAbility();
    void setMobAbility(Consumer<ServerPlayer> mobAbility);

    boolean isFunctionActive(int b);
    void setFunctionActive(boolean active, int b);

    int getCloneCount();
    void setCloneCount(int cloneCount);
    default boolean tooManyClones() {
        return getCloneCount() >= DuplicateClonesAbility.MAX;
    }

    default void incrementClone() {
        setCloneCount(getCloneCount()+1);
    }

    default void decrementClone() {
        setCloneCount(getCloneCount()-1);
    }

    default void copyFrom(ServerPlayer oldPlayer) {
        PlayerDuck old = of(oldPlayer);
        Inventory newInventory = as().getInventory();
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
