package tfar.zomboabilities.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.init.ModEntityTypes;
import tfar.zomboabilities.utils.AbilityUtils;

//Telekensis
//
//Holding R - Holding R on a block will allow the player to have the block float in the air, similar to the Ender hand
// from the Mutant Mobs Mod, By Clicking, you can throw the block and do some damage to the opponent.
public class TelekinesisAbility extends Ability{

    public static final double dist = 2.5;

    @Override
    public void primary(ServerPlayer player) {
        HitResult hitResult = player.pick(player.blockInteractionRange(),1,false);


        FallingBlockEntity held = AbilityUtils.getDataAttachment(player, CommonDataAttachments.TELEKINESIS);

        if (held == null) {
            if (hitResult instanceof BlockHitResult blockHitResult) {
                BlockPos pos = blockHitResult.getBlockPos();
                BlockState state =  player.level().getBlockState(pos);
                if (state.isAir()) return;
                FallingBlockEntity fallingBlockEntity = fall(player.serverLevel(), pos,state);
                fallingBlockEntity.setPos(player.getEyePosition(1).add(player.getLookAngle().scale(dist)));
                fallingBlockEntity.setNoGravity(true);
                AbilityUtils.setDataAttachment(player,CommonDataAttachments.TELEKINESIS,fallingBlockEntity);
            }
        } else {
            held.setNoGravity(false);
            held.addDeltaMovement((player.getLookAngle().scale(2)));
            AbilityUtils.setDataAttachment(player,CommonDataAttachments.TELEKINESIS,null);
        }
    }

    public static FallingBlockEntity fall(Level level, BlockPos pos, BlockState blockState) {
        FallingBlockEntity fallingBlockEntity2 = new FallingBlockEntity(ModEntityTypes.FAST_FALLING_BLOCK,level);

        Vec3 vec3 = new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

        fallingBlockEntity2.blocksBuilding = true;
        fallingBlockEntity2.setPos(vec3.x,vec3.y,vec3.z);
        fallingBlockEntity2.blockState = blockState;

        level.setBlock(pos, blockState.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(fallingBlockEntity2);
        return fallingBlockEntity2;
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
    public void tickAbility(ServerPlayer player) {
        super.tickAbility(player);
        FallingBlockEntity held = AbilityUtils.getDataAttachment(player, CommonDataAttachments.TELEKINESIS);
        if (held != null) {
            held.setPos(player.getEyePosition(1).add(player.getLookAngle().scale(dist)));
            held.setDeltaMovement(Vec3.ZERO);
            held.hurtMarked = true;
            held.time = 0;
        }
    }
}
