package tfar.zomboabilities.abilities;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

//Passive - Any mob/Player the Player touches will go through stages of effects
//
//First 10 seconds Player will gain Hunger 2 for 120 Seconds
//15  more seconds, Player will gain Nausea 3 for 10 seconds
//20 more Seconds, Player will gain Weakness 2 for 80 Seconds
//20 more Seconds, Player will gain Slowness 2 for 80 Seconds
//25 More Seconds, Player will Gain Blindess 3 for 50 Seconds
//20 More Seconds Player will Get Poison 3 for 300 Seconds
//
//By Pressing Y  on a Arrow, The Player can make Poison arrows
//
//Hitting a villager will cause it to become a Zombie Villager within 20 seconds
//
//Your Ability has no Effect on
//
//Wither,Ender dragon,Iron Golem,Zombie,Drowned Zombie,
//Husk,
//Skeleton
//Stray
//That new Swamp Skeleton
public class PoisonTouchAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {

    }

    @Override
    public void secondary(ServerPlayer player) {
        ItemStack stack = Items.TIPPED_ARROW.getDefaultInstance();
        stack.set(DataComponents.POTION_CONTENTS,new PotionContents(Potions.POISON));
        player.addItem(stack);
    }

    @Override
    public void onTouch(Player player, Entity touched) {
        super.onTouch(player, touched);
        if (touched instanceof LivingEntity livingTouched) {
            livingTouched.addEffect(new MobEffectInstance(MobEffects.HUNGER,120 * 20,1));
            livingTouched.addEffect(new MobEffectInstance(MobEffects.CONFUSION,10 * 20,2));
            livingTouched.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,80 * 20,1));
            livingTouched.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,80 * 20,1));
            livingTouched.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,50 * 20,2));
            livingTouched.addEffect(new MobEffectInstance(MobEffects.POISON,180 * 20,2));
        }
    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }
}
