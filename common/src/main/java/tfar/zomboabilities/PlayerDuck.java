package tfar.zomboabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.AbilityControls;
import tfar.zomboabilities.abilities.DuplicateClonesAbility;
import tfar.zomboabilities.ducks.AbstractFurnaceBlockEntityDuck;
import tfar.zomboabilities.entity.FireBreathEntity;
import tfar.zomboabilities.init.Tags;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.AbilityUtils;
import tfar.zomboabilities.utils.Utils;

import java.util.function.Consumer;

public interface PlayerDuck {

    static PlayerDuck of(Player player) {
        return (PlayerDuck) player;
    }
    default Player as() {
        return (Player) this;
    }

    void setCopiedAbility(Ability ability);
    Ability getCopiedAbility();
    Consumer<ServerPlayer> getMobAbility();
    void setMobAbility(Consumer<ServerPlayer> mobAbility);

    int[] getCooldowns();
    boolean isPrimaryActive();
    void setPrimaryActive(boolean active);

    boolean isFunctionActive(int b);
    void setFunctionActive(boolean active, int b);

    int getLaserActiveDuration();
    void setLaserActiveDuration(int duration);

    int getExplosionImmunityTimer();
    void setExplosionImmunityTimer(int duration);

    int getCloneCount();
    void setCloneCount(int cloneCount);
    default boolean tooManyClones() {
        return getCloneCount() >= DuplicateClonesAbility.MAX;
    }

    default void incrementClone() {
        setCloneCount(getCloneCount()+1);
    }

    default void decrementClone() {
        setCloneCount(getCloneCount()-1);
    }

    int MAX = 200;

    default void tickServer() {
        ServerPlayer player = (ServerPlayer) as();
        AbilityUtils.getAbility(player).ifPresent(ability -> {
            if (player.tickCount %20 == 0) {
                ability.tickPassive(player);
            }
        });
        if (isPrimaryActive() && AbilityUtils.hasAbility(as(),Abilities.LASER_EYES)) {
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
                        if (state.is(Tags.GLASS_BLOCKS_CHEAP)) {
                            player.hurt(player.damageSources().indirectMagic(player, null), 2);
                        } else if (be instanceof AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
                            ((AbstractFurnaceBlockEntityDuck)abstractFurnaceBlockEntity).setLaserTimer(40);
                        } else if (player.level().isEmptyBlock(offset)) {
                            player.level().setBlock(offset,Blocks.FIRE.defaultBlockState(),3);
                        }
                    }
                    player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS,1,1);
                }
            }
            int laserDuration = getLaserActiveDuration();
            laserDuration++;
            boolean stayActive = Services.PLATFORM.getControls(player).holding_primary && laserDuration <= MAX;
            if (stayActive) {
                setLaserActiveDuration(laserDuration);
            } else {
                setPrimaryActive(false);
                setLaserActiveDuration(0);
                if (ZomboAbilities.ENABLE_LOG) {
                    System.out.println("Laser Disabled");
                }
            }
        }

        if (Services.PLATFORM.getControls(player).holding_secondary && AbilityUtils.hasAbility(as(),Abilities.FIRE_MANIPULATION)) {
            ServerLevel serverLevel = player.serverLevel();
            Vec3 look = player.getLookAngle();
            FireBreathEntity fireBreathEntity = FireBreathEntity.shootFromEyes(player,look,serverLevel);
            serverLevel.addFreshEntity(fireBreathEntity);
        }

        tickCooldowns();
        if (getExplosionImmunityTimer() > 0) {
            setExplosionImmunityTimer(getExplosionImmunityTimer() - 1);
        }
    }

    default void tickCooldowns() {
        int[] cooldowns = getCooldowns();
        boolean update = false;
        for (int i = 0; i < cooldowns.length;i++) {
            if (cooldowns[i] > 0) {
                cooldowns[i]--;
                update = true;
            }
        }
        if (update && ZomboAbilities.ENABLE_LOG) {
            as().displayClientMessage(Component.literal("Cooldowns: "+cooldowns[0] +" | "+cooldowns[1] +" | "+cooldowns[2] +" | "+cooldowns[3] ),true);
        }
    }

    default void setCooldown(int slot, int value) {
        getCooldowns()[slot] = value;
    }

    default void copyFrom(ServerPlayer oldPlayer) {
        PlayerDuck old = of(oldPlayer);
        Inventory newInventory = as().getInventory();
        SavedInventory oldSavedInventory = old.getSavedInventory();
        for (int i = 0; i < newInventory.getContainerSize(); i++) {
            ItemStack stack = newInventory.getItem(i);
            if (stack.isEmpty()) {
                ItemStack savedStack = oldSavedInventory.getItem(i);
                if (!savedStack.isEmpty()) {
                    newInventory.setItem(i, savedStack);
                }
            }
        }
        oldSavedInventory.clearContent();
    }

    SavedInventory getSavedInventory();
}
