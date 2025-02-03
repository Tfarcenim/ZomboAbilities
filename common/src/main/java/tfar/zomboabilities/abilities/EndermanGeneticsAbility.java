package tfar.zomboabilities.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import tfar.zomboabilities.platform.Services;

public class EndermanGeneticsAbility extends Ability{

    ////Pressing R - You will teleport over to a nearby random location similar to a real enderman, you’ll have the enderman teleport sound effect and visual effect.
    ////Cooldown - 3 Seconds
    ////
    ////Passxive - You will take Damage in the rain, You will also take damage in the Water.
    ////You can the Golden apple effect when you eat a chorus fruit, the regular golden apple effect,
    ////You Will automatically Dodge Arrows with your teleport

    @Override
    public void primary(ServerPlayer player) {
        if (!player.level().isClientSide() && player.isAlive()) {
            double d0 = player.getX() + (player.getRandom().nextDouble() - 0.5) * 64.0;
            double d1 = player.getY() + (double)(player.getRandom().nextInt(64) - 32);
            double d2 = player.getZ() + (player.getRandom().nextDouble() - 0.5) * 64.0;
            if (teleport(player,d0, d1, d2)) {
                applyCooldown(0,3 * 20,player);
            }
        }
    }

    public static boolean randomTeleport(ServerPlayer player) {
        double d0 = player.getX() + (player.getRandom().nextDouble() - 0.5) * 64.0;
        double d1 = player.getY() + (double)(player.getRandom().nextInt(64) - 32);
        double d2 = player.getZ() + (player.getRandom().nextDouble() - 0.5) * 64.0;
        return teleport(player,d0,d1,d2);
    }

    private static boolean teleport(ServerPlayer player, double x, double y, double z) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);

        while (blockpos$mutableblockpos.getY() > player.level().getMinBuildHeight() && !player.level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = player.level().getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            Pair<Boolean,Vec3> pair = Services.PLATFORM.teleportEvent(player, x, y, z);
            if (pair.getKey()) return false;
            Vec3 vec3 = player.position();
            Vec3 eventVec = pair.getValue();
            boolean flag2 = player.randomTeleport(eventVec.x, eventVec.y, eventVec.z, true);
            if (flag2) {
                player.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(player));
                if (!player.isSilent()) {
                    player.level().playSound(null, player.xo, player.yo, player.zo, SoundEvents.ENDERMAN_TELEPORT, player.getSoundSource(), 1.0F, 1.0F);
                    player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }
            return flag2;
        } else {
            return false;
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
