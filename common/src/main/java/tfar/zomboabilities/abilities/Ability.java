package tfar.zomboabilities.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.PlayerDuck;

public abstract class Ability {
    private final String name;

    public Ability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public final void tryUseAbility(ServerPlayer player,int key) {
        int cooldown = PlayerDuck.of(player).getCooldowns()[key];
        if (cooldown > 0) {
            player.displayClientMessage(Component.literal("Ability on cooldown"),true);
        } else {
            switch (key) {
                case 0 -> primary(player);
                case 1 -> secondary(player);
                case 2 -> tertiary(player);
                case 3 -> quaternary(player);
            }
        }
    }

    /**
     * T
     * @param player
     */
    public abstract void primary(ServerPlayer player);

    /**
     * R
     * @param player
     */
    public abstract void secondary(ServerPlayer player);

    /**
     * C
     * @param player
     */
    public abstract void tertiary(ServerPlayer player);

    /**
     * Y
     * @param player
     */
    public abstract void quaternary(ServerPlayer player);
    protected void applyCooldown(int slot,int amount, ServerPlayer player) {
        PlayerDuck.of(player).setCooldown(slot,amount);
    }

}
