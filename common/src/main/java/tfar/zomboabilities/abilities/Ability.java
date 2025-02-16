package tfar.zomboabilities.abilities;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.PlayerDuck;

public abstract class Ability {

    public static final Codec<Ability> CODEC = Codec.STRING.xmap(Abilities.ABILITIES_BY_NAME::get, Ability::getName);

    private String name;

    public Ability() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final void tryUseAbility(ServerPlayer player, int key) {
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

    public void applyPassive(ServerPlayer player) {

    }

    public void removePassive(ServerPlayer player) {

    }

    public void tick(ServerPlayer player) {

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

    protected void applyAttributeSafely(ServerPlayer player, Holder<Attribute> attribute, AttributeModifier modifier) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance != null) {
            AttributeModifier existing = instance.getModifier(modifier.id());
            if (existing != null) {
                instance.removeModifier(existing);
            }
            instance.addPermanentModifier(modifier);//stays until death
        }
    }
}
