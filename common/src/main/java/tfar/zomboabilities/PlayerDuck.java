package tfar.zomboabilities;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.DuplicateClonesAbility;
import tfar.zomboabilities.entity.FireBreathEntity;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.AbilityUtils;

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

    int[] getCooldowns();

    boolean isFunctionActive(int b);
    void setFunctionActive(boolean active, int b);

    int getLaserActiveDuration();
    void setLaserActiveDuration(int duration);

    int getExplosionImmunityTimer();
    void setExplosionImmunityTimer(int duration);

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

    default void tickServer() {
        ServerPlayer player = (ServerPlayer) as();
        AbilityUtils.getAbility(player).ifPresent(ability -> ability.tick(player));

        tickCooldowns();
        if (getExplosionImmunityTimer() > 0) {
            setExplosionImmunityTimer(getExplosionImmunityTimer() - 1);
        }
    }

    default void tickCooldowns() {
        int[] cooldowns = getCooldowns();
        boolean update = false;
        for (int i = 0; i < cooldowns.length;i++) {
            if (cooldowns[i] > 0) {
                cooldowns[i]--;
                update = true;
            }
        }
        if (update && ZomboAbilities.ENABLE_LOG) {
            as().displayClientMessage(Component.literal("Cooldowns: "+cooldowns[0] +" | "+cooldowns[1] +" | "+cooldowns[2] +" | "+cooldowns[3] ),true);
        }
    }

    default void setCooldown(int slot, int value) {
        getCooldowns()[slot] = value;
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
