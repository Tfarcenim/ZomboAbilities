package tfar.zomboabilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.AbilityControls;

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

    int getLaserActiveDuration();
    void setLaserActiveDuration(int duration);

    int MAX = 200;

    default void tickServer() {
        ServerPlayer player = (ServerPlayer) as();
        if (isLaserActive()) {
            EntityHitResult pick = Utils.pick(player, player.blockInteractionRange(), player.entityInteractionRange(), 0);
            if (pick != null && player.tickCount %20 ==0) {
                Entity entity = pick.getEntity();
                if (entity.isAttackable()) {
                    entity.hurt(player.damageSources().indirectMagic(player,null),2);
                }
            }
            int laserDuration = getLaserActiveDuration();
            laserDuration++;
            boolean stayActive = getControls().holding_primary && laserDuration <= MAX;
            if (stayActive) {
                setLaserActiveDuration(laserDuration);
            } else {
                setLaserActive(false);
                setLaserActiveDuration(0);
                if (ZomboAbilities.ENABLE_LOG) {
                    System.out.println("Laser Disabled");
                }
            }
        }
        tickCooldowns();
    }

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
    AbilityControls getControls();

}
