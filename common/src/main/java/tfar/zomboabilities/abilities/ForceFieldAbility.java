package tfar.zomboabilities.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import tfar.zomboabilities.ForceFieldData;
import tfar.zomboabilities.init.ModBlocks;
import tfar.zomboabilities.init.ModItems;
import tfar.zomboabilities.platform.Services;

import java.util.HashSet;
import java.util.Set;

//Force Field
//
//Pressing R - You know the Border Blocks, Something looks like that, Will cover the player in a 4 block radius,
// By Pressing R again player can Expand there Force field by 1 block with the cost of 2 Exp
//
//Pressing T - This will give the player a shield made out of Border Block, This Shield has the Durability to take 14 Hits and thats it.
//
//By pressing Y - The Force Field will be taken down
public class ForceFieldAbility extends Ability{

    //149,591,800
    @Override
    public void primary(ServerPlayer player) {

        if (player.totalExperience >=2) {
            player.giveExperiencePoints(-2);
        } else {
            return;
        }

        ForceFieldData forceFieldData = Services.PLATFORM.getFFData(player);
        int size = forceFieldData.size();
        if (forceFieldData.size() == 0) {
            size=4;
        } else {
            if (size < 12) {
                size++;
            }
        }

        long start = System.nanoTime();

        for (BlockPos pos : forceFieldData.fieldPositions()) {
            player.level().removeBlock(pos,false);
        }

        HashSet<BlockPos> sphere = createSphere(player.serverLevel(),player.blockPosition(),size);
        for (BlockPos pos : sphere) {
            player.level().setBlock(pos, ModBlocks.FORCE_FIELD.defaultBlockState(),3);
        }

        Services.PLATFORM.setFFData(player,new ForceFieldData(sphere,size));
        long time = System.nanoTime() - start;
        System.out.println("Time taken: "+time/1_000_000d +" ms");
    }

    public static HashSet<BlockPos> createSphere(ServerLevel level,BlockPos pos, int radius) {
        HashSet<BlockPos> set = new HashSet<>();
        int radiusSq = radius * radius;
        int radiusSqMinus1 = (radius - 1) * (radius - 1);
        for (BlockPos itr : BlockPos.betweenClosed(pos.offset(-radius,-radius,-radius),pos.offset(radius,radius,radius))) {
            BlockState state = level.getBlockState(itr);
            if (state.canBeReplaced()) {
                double distSq = itr.distSqr(pos);
                if (radiusSqMinus1 < distSq && distSq <= radiusSq) {
                    set.add(itr.immutable());
                }
            }
        }
        return set;
    }

    @Override
    public void secondary(ServerPlayer player) {
        if (player.getOffhandItem().isEmpty()) {
            player.setItemInHand(InteractionHand.OFF_HAND,ModItems.FORCE_SHIELD.getDefaultInstance());
        }
    }

    @Override
    public void tertiary(ServerPlayer player) {
        Set<BlockPos> poss = Services.PLATFORM.getFFData(player).fieldPositions();
        for (BlockPos pos : poss) {
            player.level().removeBlock(pos,false);
        }
        Services.PLATFORM.setFFData(player,new ForceFieldData());
    }

    @Override
    public void quaternary(ServerPlayer player) {

    }
}
