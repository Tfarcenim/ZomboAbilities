package tfar.zomboabilities.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import tfar.zomboabilities.entity.IceSpikeEntity;
import tfar.zomboabilities.init.ModEntityTypes;
import tfar.zomboabilities.platform.Services;

import java.util.List;
import java.util.Optional;

//Pressing R - You can spawn a Ice Block in your Cross air
//
//Pressing Y - You cannot be able to walk on water with ice similar to that enchantment, You csn Press Y again to turn this off
//
//Pressing T - A line of Ice will come out fo ground, and strike forward infront of the player (Ill show you an example of this in discord)
//
//Pressing C - By pressing this, with 1 Exp you can make a arrow, (It will look light blue) It’ll be called a Ice Tipped arrow,
// It’ll give slowness and weakness to anybody it hits for 4 seconds.
//–
public class IceManipulationAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        BlockHitResult blockHitResult = (BlockHitResult)player.pick(5,1,false);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            BlockPos offset = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
            if (player.serverLevel().getBlockState(offset).canBeReplaced()) {
                player.level().setBlock(offset, Blocks.ICE.defaultBlockState(),3);
            }
        }
    }

    @Override
    public void secondary(ServerPlayer player) {
        Services.PLATFORM.setIMData(player,Services.PLATFORM.getIMData(player).setFrostWalkerActive(!
                Services.PLATFORM.getIMData(player).frostWalkerActive()));
    }

    @Override
    public void tertiary(ServerPlayer player) {
        int count = 16;
        int radius = 4;

        for (int i = 0; i<count;i++) {
            Vec3 vec3 = new Vec3(player.getX() + radius * Mth.sin((float) (2 * Math.PI* i /count)),player.getY(),
                    player.getZ() + radius * Mth.cos((float) (2 * Math.PI* i /count)));

            IceSpikeEntity iceSpikeEntity = ModEntityTypes.ICE_SPIKE.create(player.serverLevel());
            iceSpikeEntity.setOwner(player);
            iceSpikeEntity.setPos(vec3);
            player.serverLevel().addFreshEntity(iceSpikeEntity);
        }
    }

    @Override
    public void quaternary(ServerPlayer player) {
        if (player.totalExperience > 1) {
            player.giveExperiencePoints(-1);
            ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
            arrow.set(DataComponents.POTION_CONTENTS,new PotionContents(Optional.empty(),Optional.of(0x9999ff),
                    List.of(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,80*8),new MobEffectInstance(MobEffects.WEAKNESS,80*8))));
            arrow.set(DataComponents.CUSTOM_NAME, Component.literal("Ice Arrow").setStyle(Style.EMPTY.withItalic(false)));
            player.addItem(arrow);
        }
    }
}
