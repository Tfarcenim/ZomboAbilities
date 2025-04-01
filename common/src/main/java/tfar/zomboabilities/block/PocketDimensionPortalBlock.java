package tfar.zomboabilities.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import tfar.zomboabilities.init.ModLevels;

public class PocketDimensionPortalBlock extends Block{
    public PocketDimensionPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ServerLevel destination = player.getServer().getLevel(ModLevels.POCKET_DIMENSION);
            ((ServerPlayer)player).teleportTo(destination, 12, 3, 12, player.getYRot(), player.getXRot());
            return InteractionResult.CONSUME;
        }
    }

    @Override//return 0 to prevent
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        float destroyProgress = super.getDestroyProgress(state, player, level, pos);
        return destroyProgress;
    }
}
