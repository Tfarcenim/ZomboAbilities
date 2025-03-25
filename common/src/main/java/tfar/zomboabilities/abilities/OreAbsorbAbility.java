package tfar.zomboabilities.abilities;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//Pressing R - By Pressing R while you have a Ore in your inventory, your durability will increase, depending on what Ore you Absorb,
// if what durability you’ll get, this particles will surround the player
//Cooldown - 15 Seconds after the 20 seconds is over
//
//Copper - Orange Particles - Lower Durability
//Redstone - Red Particles - Lower Durability - This will give you Speed 1 for 20 seconds
//Coal - Black Particles - Lower Durability
//Lapis - Dark Blue Particles - Medium Durability - Will cause Glowing for 20 seconds
//Emerald - Green Particles Medium Durability - This will give you Hero of the village for 20 secs
//Gold - Yellow particles - Medium Durability
//Iron - White Particles - Medium High duarbility
//DIamond - Light Blue Particles - High Durability
//Netherite - Grey Particles - High Durability
//
//Each of these will last up to 20 seconds
public class OreAbsorbAbility extends Ability{


    static final Map<Item,Data> map = new HashMap<>();

    static {
        map.put(Items.COPPER_ORE,new Data(0xff5f00,8,Optional.empty()));
        map.put(Items.DEEPSLATE_COPPER_ORE,new Data(0xff5f00,8,Optional.empty()));

        map.put(Items.REDSTONE_ORE,new Data(0xff0000,8,Optional.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,400))));
        map.put(Items.DEEPSLATE_REDSTONE_ORE,new Data(0xff0000,8,Optional.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,400))));

        map.put(Items.COAL_ORE,new Data(0x111111,8,Optional.empty()));
        map.put(Items.DEEPSLATE_COAL_ORE,new Data(0x111111,8,Optional.empty()));

        map.put(Items.LAPIS_ORE,new Data(0x1111ff,16,Optional.of(new MobEffectInstance(MobEffects.GLOWING,400))));
        map.put(Items.DEEPSLATE_LAPIS_ORE,new Data(0x1111ff,16,Optional.of(new MobEffectInstance(MobEffects.GLOWING,400))));

        map.put(Items.EMERALD_ORE,new Data(0x00ee00,16,Optional.of(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE,400))));
        map.put(Items.DEEPSLATE_EMERALD_ORE,new Data(0x00ee00,16,Optional.of(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE,400))));

        map.put(Items.GOLD_ORE,new Data(0xffff11,16,Optional.empty()));
        map.put(Items.DEEPSLATE_GOLD_ORE,new Data(0xffff11,16,Optional.empty()));

        map.put(Items.IRON_ORE,new Data(0xeeeeee,32,Optional.empty()));
        map.put(Items.DEEPSLATE_IRON_ORE,new Data(0xeeeeee,32,Optional.empty()));

        map.put(Items.DIAMOND_ORE,new Data(0x11ffff,64,Optional.empty()));
        map.put(Items.DEEPSLATE_DIAMOND_ORE,new Data(0x11ffff,64,Optional.empty()));

        map.put(Items.ANCIENT_DEBRIS,new Data(0x777777,64,Optional.empty()));
    }

    record Data(int color, int durability, Optional<MobEffectInstance> effectInstance) {}

    @Override
    public void primary(ServerPlayer player) {
        Data data = findAndUseOre(player);
        if (data != null) {
            ParticleOptions options = ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, FastColor.ARGB32.color(0xff,data.color));
            player.serverLevel().sendParticles(options, player.getRandomX(0.5), player.getRandomY(), player.getRandomZ(0.5),1,
                    1.0, 1.0, 1.0,0);

            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = player.getItemBySlot(slot);
                if (!stack.isEmpty() && stack.isDamaged()) {
                    stack.setDamageValue(stack.getDamageValue() - data.durability);
                }
            }

            data.effectInstance.ifPresent(player::addEffect);
        }
    };

    @Nullable
    Data findAndUseOre(ServerPlayer player) {
        for (ItemStack stack : player.getInventory().items) {
            Item item = stack.getItem();
            Data data = map.get(item);
            if (data != null) {
                stack.shrink(1);
                return data;
            }
        }
        return null;
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
}
