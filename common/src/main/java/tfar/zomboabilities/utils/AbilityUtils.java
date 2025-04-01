package tfar.zomboabilities.utils;

import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.AbilityControls;
import tfar.zomboabilities.attachments.CommonDataAttachment;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.data.ForceFieldData;
import tfar.zomboabilities.data.IceManipulationData;
import tfar.zomboabilities.data.LightManipulationData;
import tfar.zomboabilities.data.ObjectRestorationData;
import tfar.zomboabilities.network.S2CCommonDataAttachmentPacket;
import tfar.zomboabilities.platform.Services;

import java.util.Objects;

public class AbilityUtils {

    public static boolean hasAbility(Entity entity, Ability ability) {
        return getAbility(entity) == ability;
    }

    public static @NotNull Ability getAbility(Entity entity) {
        return getDataAttachment(entity, CommonDataAttachments.ABILITY);
    }

    public static void setAbility(Entity entity,@NotNull Ability ability) {
        Objects.requireNonNull(ability);
        setDataAttachment(entity,CommonDataAttachments.ABILITY,ability);
    }

    public static void removeAbility(Entity entity) {
        setAbility(entity, Abilities.NONE);
    }

    public static int[] getCooldowns(Player player) {
        return getDataAttachment(player,CommonDataAttachments.COOLDOWNS);
    }

    public static int getLaserActiveDuration(Player player) {
        return getDataAttachment(player,CommonDataAttachments.LASER_ACTIVE_DURATION);
    }

    public static void setLaserActiveDuration(Player player,int laserActiveDuration) {
        setDataAttachment(player,CommonDataAttachments.LASER_ACTIVE_DURATION,laserActiveDuration);
    }

    public static int getExplosionImmunityTimer(Player player) {
        return getDataAttachment(player,CommonDataAttachments.EXPLOSION_IMMUNITY_TIMER);
    }

    public static void setExplosionImmunityTimer(Player player,int laserActiveDuration) {
        setDataAttachment(player,CommonDataAttachments.EXPLOSION_IMMUNITY_TIMER,laserActiveDuration);
    }


    public static LightManipulationData getLightManipulationData(Player player) {
        return getDataAttachment(player,CommonDataAttachments.LIGHT_MANIPULATION);
    }

    public static void setLightManipulationData(Player player, LightManipulationData laserActiveDuration) {
        setDataAttachment(player,CommonDataAttachments.LIGHT_MANIPULATION,laserActiveDuration);
    }


    public static void setFFData(Entity entity, ForceFieldData data) {
        setDataAttachment(entity,CommonDataAttachments.FORCE_FIELD_DATA,data);
    }

    public static ForceFieldData getFFData(Entity entity) {
        return getDataAttachment(entity,CommonDataAttachments.FORCE_FIELD_DATA);
    }

    public static void setIMData(Entity entity, IceManipulationData data) {
        setDataAttachment(entity,CommonDataAttachments.ICE_MANIPULATION_DATA,data);
    }

    public static IceManipulationData getIMData(Entity entity) {
        return getDataAttachment(entity,CommonDataAttachments.ICE_MANIPULATION_DATA);
    }


    public static AbilityControls getControls(Entity entity) {
        return getDataAttachment(entity,CommonDataAttachments.ABILITY_CONTROLS);
    }


    public static void setInfinityActive(Player player, boolean infinity) {
        setDataAttachment(player,CommonDataAttachments.INFINITY,infinity);
    }


    public static boolean isInfinityActive(Entity entity) {
        return getDataAttachment(entity,CommonDataAttachments.INFINITY);
    }

    public static <T> void setDataAttachment(Object object, CommonDataAttachment<T> type,T value) {
        Services.PLATFORM.setAttachedValue(object,type,value);
        if (object instanceof Entity entity  && !entity.level().isClientSide && type.isAutoSync()) {
            syncDataAttachment(entity, type, value);
        }
    }

    public static <T> T getDataAttachment(Object object, CommonDataAttachment<T> type) {
        return Services.PLATFORM.getAttachedValue(object,type);
    }


    public static <T> void syncDataAttachment(Entity entity, CommonDataAttachment<T> type, T value) {
        if (!type.canSync())throw new NullPointerException("Data component "+type.getName()+" is not eligible for syncing!");
        Services.PLATFORM.sendToTracking(new S2CCommonDataAttachmentPacket<>(type,entity,value),entity);
    }


    public static void setORData(Entity entity, ObjectRestorationData data) {
        setDataAttachment(entity,CommonDataAttachments.BLOCK_RESTORATION,data);
    }

    
    public static ObjectRestorationData getORData(Entity entity) {
        return getDataAttachment(entity,CommonDataAttachments.BLOCK_RESTORATION);
    }

    public static void setLiveGiverState(Entity entity,BlockState state) {
        setDataAttachment(entity,CommonDataAttachments.LIVE_GIVER_STATE,state);
    }

    public static BlockState getLiveGiverState(Entity entity) {
        return Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.LIVE_GIVER_STATE);
    }

    public static void setLightningChance(Level level,int chance) {
        setDataAttachment(level,CommonDataAttachments.LIGHTNING_CHANCE,chance);
    }

    public static int getLightningChance(Level level) {
        return getDataAttachment(level,CommonDataAttachments.LIGHTNING_CHANCE);
    }

    public static void setLightningTimer(Level level,int timer) {
        setDataAttachment(level,CommonDataAttachments.INCREASED_LIGHTNING_TIMER,timer);
    }

    public static int getLightningTimer(Level level) {
        return getDataAttachment(level,CommonDataAttachments.INCREASED_LIGHTNING_TIMER);
    }

    public static void setCircleLightningTimer(Entity entity,int timer) {
        setDataAttachment(entity,CommonDataAttachments.CIRCLE_LIGHTNING_TIMER,timer);
    }

    public static int getCircleLightningTimer(Entity entity) {
        return getDataAttachment(entity,CommonDataAttachments.CIRCLE_LIGHTNING_TIMER);
    }

    public static void setMagnet(Entity entity,boolean magnet) {
        setDataAttachment(entity,CommonDataAttachments.MAGNET,magnet);
    }

    public static boolean getMagnet(Entity entity) {
        return getDataAttachment(entity,CommonDataAttachments.MAGNET);
    }

    public static void setShapeShiftTimer(Entity entity,int magnet) {
        setDataAttachment(entity,CommonDataAttachments.SHAPE_SHIFT_TIMER,magnet);
    }

    public static int getShapeShiftTimer(Entity entity) {
        return getDataAttachment(entity,CommonDataAttachments.SHAPE_SHIFT_TIMER);
    }

    public static GlobalPos getPocketDimensionReturn(Entity entity) {
        return getDataAttachment(entity,CommonDataAttachments.POCKET_DIMENSION_RETURN);
    }

    public static void setPocketDimensionReturn(Entity entity,GlobalPos pos) {
        setDataAttachment(entity,CommonDataAttachments.POCKET_DIMENSION_RETURN,pos);
    }
}
