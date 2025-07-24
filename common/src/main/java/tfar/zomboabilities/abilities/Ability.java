package tfar.zomboabilities.abilities;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.utils.AbilityUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class Ability {

    public static final Codec<Ability> CODEC = Codec.STRING.xmap(Abilities.ABILITIES_BY_NAME::get, Ability::getName);

    //    public static final StreamCodec<RegistryFriendlyByteBuf, C2SUseAbilityPacket> STREAM_CODEC =
    //            StreamCodec.composite(ByteBufCodecs.INT, C2SUseAbilityPacket::key, C2SUseAbilityPacket::new);
    //ability -> string, string -> ability
    public static final StreamCodec<FriendlyByteBuf, Ability> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8,Ability::getName,Abilities.ABILITIES_BY_NAME::get);

    private String name;
    protected final List<MobEffectInstance> effects = new ArrayList<>();
    boolean hurtByWater;

    public Ability() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ability withEffect(MobEffectInstance effect) {
        effects.add(effect);
        return this;
    }

    public Ability hurtByWater() {
        hurtByWater = true;
        return this;
    }

    public boolean isHurtByWater() {
        return hurtByWater;
    }

    public void tryUseAbility(ServerPlayer player, int key) {
        int cooldown = AbilityUtils.getCooldowns(player)[key];
        if (cooldown > 0) {
            player.displayClientMessage(Component.literal("Ability on cooldown"), true);
        } else {
            switch (key) {
                case 0 -> primary(player);
                case 1 -> secondary(player);
                case 2 -> tertiary(player);
                case 3 -> quaternary(player);
            }
        }
    }

    public void onAdded(ServerPlayer player) {
        effects.forEach(player::addEffect);
    }

    public void onRemoved(ServerPlayer player) {
        effects.forEach(instance -> player.removeEffect(instance.getEffect()));
    }

    public final void tick(ServerPlayer player) {
        tickCooldowns(player);
        tickAbility(player);
    }

    void tickCooldowns(ServerPlayer player) {
        int[] cooldowns = AbilityUtils.getCooldowns(player);
        boolean update = false;
        for (int i = 0; i < cooldowns.length; i++) {
            if (cooldowns[i] > 0) {
                cooldowns[i]--;
                update = true;
            }
        }
        if (update && ZomboAbilities.ENABLE_LOG) {
            player.displayClientMessage(Component.literal("Cooldowns: " + cooldowns[0] + " | " + cooldowns[1] + " | " + cooldowns[2] + " | " + cooldowns[3]), true);
        }
    }

    public void tickAbility(ServerPlayer player) {

    }

    /**
     * T
     *
     * @param player
     */
    public abstract void primary(ServerPlayer player);

    /**
     * R
     *
     * @param player
     */
    public abstract void secondary(ServerPlayer player);

    /**
     * C
     *
     * @param player
     */
    public abstract void tertiary(ServerPlayer player);

    /**
     * Y
     *
     * @param player
     */
    public abstract void quaternary(ServerPlayer player);

    public boolean isFriendly(Mob aggressor) {
        return false;
    }

    protected void applyCooldown(int slot, int amount, ServerPlayer player) {
        AbilityUtils.getCooldowns(player)[slot] = amount;
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

    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                '}';
    }

    public void onTouch(Player player, Entity touched) {

    }

    public boolean onAttacked(Player attacked,LivingEntity attacker) {
        return false;
    }

    public static final class NoAbility extends Ability {

        @Override
        public void primary(ServerPlayer player) {

        }

        @Override
        public void secondary(ServerPlayer player) {

        }

        @Override
        public void tertiary(ServerPlayer player) {

        }

        @Override
        public void quaternary(ServerPlayer player) {

        }

        @Override
        void tickCooldowns(ServerPlayer player) {
        }

        @Override
        public void tryUseAbility(ServerPlayer player, int key) {
            player.sendSystemMessage(Component.literal("Cannot use Abilities!"));
        }
    }
}
