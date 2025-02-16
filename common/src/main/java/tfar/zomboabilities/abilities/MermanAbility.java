package tfar.zomboabilities.abilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.entity.EntityTypeTest;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.platform.Services;

import java.util.List;

public class MermanAbility extends Ability{

    public MermanAbility() {
        super();
    }

    //Merman
//
//Passives - This Ability Allows you to breathe underwater permanently.You wll also be able to swim faster automatically,
//
// you’ll be able mine blocks at normal speed underwater.
//
//Dolphins, Guardians, Elder Guardians, Drowned Zombies, Will not attack the player.
//
//Pressing R - You can command Nearby Dolphins to attack the nearest Player within 300 Blocks of you.
//

    final ResourceLocation ID = ZomboAbilities.id("merman");

    @Override
    public void applyPassive(ServerPlayer player) {
        applyAttributeSafely(player, Attributes.SUBMERGED_MINING_SPEED,
                new AttributeModifier(ID,4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        applyAttributeSafely(player, Services.PLATFORM.getSwimSpeed(),
                new AttributeModifier(ID,1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    @Override
    public void removePassive(ServerPlayer player) {
        player.getAttribute(Attributes.SUBMERGED_MINING_SPEED).removeModifier(ID);
        player.getAttribute(Services.PLATFORM.getSwimSpeed()).removeModifier(ID);
    }

    @Override
    public void tick(ServerPlayer player) {
        if (player.tickCount %20 == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 25, 0, false, false));
        }
    }

    @Override
    public void primary(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        List<? extends Dolphin> dolphins = level.getEntities(EntityTypeTest.forClass(Dolphin.class), dolphin -> true);
        List<? extends Zombie> targets = level.getEntities(EntityTypeTest.forClass(Zombie.class), zombie -> true);
        if (!targets.isEmpty()) {
            Zombie zombie = targets.getFirst();
            dolphins.forEach(dolphin -> dolphin.setTarget(zombie));
        }

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
