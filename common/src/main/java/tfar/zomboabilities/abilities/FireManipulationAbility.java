package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.phys.Vec3;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.entity.FireBreathEntity;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.AbilityUtils;

//Pressing R - This ability allows you to shoot a Fire Ball
//Cooldown - 8 Seconds
//
//Pressing T - Similar to laser Eyes the Player will be able to breath fire out there mouth, this will set fire to anything it is touching
//
//Pressing Y - Will Smelt/Cook any Raw meat or ores in your hand
//
//Crouching + Right click - Will light a fire similar to if you had a flint and steel
//
//Passives - You have Permanent Fire Resistance
//Drinking a fire Resistance Potion gives you Regular Golden apple effects
public class FireManipulationAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        Vec3 vec3 = player.getViewVector(1.0F);
        LargeFireball largefireball = new LargeFireball(player.level(), player, vec3.normalize(),1);
        largefireball.setPos(player.getX() + vec3.x * 4.0, player.getY(0.5) + 0.5, largefireball.getZ() + vec3.z * 4.0);
        player.level().addFreshEntity(largefireball);
        applyCooldown(0,8* 20,player);
    }

    @Override
    public void secondary(ServerPlayer player) {

    }

    private final RecipeManager.CachedCheck<SingleRecipeInput, SmeltingRecipe> quickCheck = RecipeManager.createCheck(RecipeType.SMELTING);

    @Override
    public void tertiary(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();
        RecipeHolder<SmeltingRecipe> recipeholder = quickCheck.getRecipeFor(new SingleRecipeInput(stack), player.serverLevel()).orElse(null);
        if (recipeholder != null) {
            ItemStack cooked = recipeholder.value().assemble(new SingleRecipeInput(stack), player.registryAccess());
            player.setItemInHand(InteractionHand.MAIN_HAND,cooked.copyWithCount(stack.getCount() * cooked.getCount()));
        }
    }

    @Override
    public void quaternary(ServerPlayer player) {

    }

    @Override
    public void tick(ServerPlayer player) {
        super.tick(player);
        if (Services.PLATFORM.getControls(player).holding_secondary) {
            ServerLevel serverLevel = player.serverLevel();
            Vec3 look = player.getLookAngle();
            FireBreathEntity fireBreathEntity = FireBreathEntity.shootFromEyes(player,look,serverLevel);
            serverLevel.addFreshEntity(fireBreathEntity);
        }
    }
}
