package tfar.zomboabilities.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.ducks.AbstractFurnaceBlockEntityDuck;
import tfar.zomboabilities.init.ModTags;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.Utils;

public class LaserEyesAbility extends Ability{
    public static final int MAX = 200;

    public LaserEyesAbility() {
        super();
    }

    @Override
    public void primary(ServerPlayer player) {
        PlayerDuck duck = PlayerDuck.of(player);
        duck.setFunctionActive(true,0);
        if (ZomboAbilities.ENABLE_LOG) System.out.println("Laser Active");
        applyCooldown(0,15 * 20,player);
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
    public void tick(ServerPlayer player) {
        super.tick(player);
        PlayerDuck duck = PlayerDuck.of(player);
        if (duck.isFunctionActive(0)) {
            HitResult pick = Utils.pickEither(player, player.blockInteractionRange() * 5, player.entityInteractionRange() * 5, 0);
            if (pick.getType() != HitResult.Type.MISS) {
                if (player.tickCount % 10 == 0) {
                    if (pick instanceof EntityHitResult entityPick) {
                        Entity entity = entityPick.getEntity();
                        if (entity.isAttackable()) {
                            entity.hurt(player.damageSources().indirectMagic(player, null), 2);
                            if (entity.getRandom().nextBoolean()) {
                                entity.igniteForSeconds(2);
                            }
                        }
                    } else if (pick instanceof BlockHitResult blockPick) {
                        BlockPos pos = blockPick.getBlockPos();
                        BlockPos offset = pos.relative(blockPick.getDirection());
                        BlockState state = player.level().getBlockState(pos);
                        BlockEntity be = player.level().getBlockEntity(pos);
                        if (state.is(ModTags.GLASS_BLOCKS_CHEAP)) {
                            player.hurt(player.damageSources().indirectMagic(player, null), 2);
                        } else if (be instanceof AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
                            ((AbstractFurnaceBlockEntityDuck)abstractFurnaceBlockEntity).setLaserTimer(40);
                        } else if (player.level().isEmptyBlock(offset)) {
                            player.level().setBlock(offset, Blocks.FIRE.defaultBlockState(),3);
                        }
                    }
                    player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS,1,1);
                }
            }
            int laserDuration = duck.getLaserActiveDuration();
            laserDuration++;
            boolean stayActive = Services.PLATFORM.getControls(player).holding_primary && laserDuration <= MAX;
            if (stayActive) {
                duck.setLaserActiveDuration(laserDuration);
            } else {
                duck.setFunctionActive(false,0);
                duck.setLaserActiveDuration(0);
                if (ZomboAbilities.ENABLE_LOG) {
                    System.out.println("Laser Disabled");
                }
            }
        }
    }
}
