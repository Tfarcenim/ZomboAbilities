package tfar.zomboabilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tfar.zomboabilities.abilities.Ability;

import java.util.Optional;
import java.util.function.Consumer;

public interface PlayerDuck {

    static PlayerDuck of(Player player) {
        return (PlayerDuck) player;
    }
    default Player as() {
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

    void setAbility(Ability ability);

    Optional<Ability> getAbility();

    void setCopiedAbility(Ability ability);
    Ability getCopiedAbility();
    Consumer<ServerPlayer> getMobAbility();
    void setMobAbility(Consumer<ServerPlayer> mobAbility);

    int[] getCooldowns();
    boolean isLaserActive();
    void setLaserActive(boolean laserActive);

    default void tickCooldowns() {
        int[] cooldowns = getCooldowns();
        for (int i = 0; i < cooldowns.length;i++) {
            if (cooldowns[i] > 0) {
                cooldowns[i]--;
            }
        }
    }

    default void setCooldown(int slot, int value) {
        getCooldowns()[slot] = value;
    }

    default void copyFrom(ServerPlayer oldPlayer) {
        PlayerDuck old = of(oldPlayer);
        setLives(old.getLives());
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
        old.getAbility().ifPresent(this::setAbility);
    }

    SavedInventory getSavedInventory();

}
