package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import tfar.zomboabilities.init.ModBlocks;

public class AcidAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        BlockHitResult hitResult = (BlockHitResult) player.pick(player.blockInteractionRange(),1,false);
        player.level().setBlock(hitResult.getBlockPos(), ModBlocks.ACID.defaultBlockState(), Block.UPDATE_ALL);
        applyCooldown(0,400,player);
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
