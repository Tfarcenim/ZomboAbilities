package tfar.zomboabilities.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.abilities.AbilityControls;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.data.ForceFieldData;
import tfar.zomboabilities.data.IceManipulationData;
import tfar.zomboabilities.data.ObjectRestorationData;
import tfar.zomboabilities.network.S2CAttachmentTypePacketBoolean;
import tfar.zomboabilities.platform.Services;

import java.util.Objects;
import java.util.Optional;

public class AbilityUtils {

    public static boolean hasAbility(Entity entity, Ability ability) {
        return getAbility(entity) == ability;
    }

    public static @NotNull Ability getAbility(Entity entity) {
        return Services.PLATFORM.getAttachedValue(entity, CommonDataAttachments.ABILITY);
    }

    public static void setAbility(Entity entity,@NotNull Ability ability) {
        Objects.requireNonNull(ability);
        Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.ABILITY,ability);
    }

    public static void removeAbility(Entity entity) {
        setAbility(entity, Abilities.NONE);
    }

    public static int[] getCooldowns(Player player) {
        return Services.PLATFORM.getAttachedValue(player,CommonDataAttachments.COOLDOWNS);
    }
    
    public static void setFFData(Entity entity, ForceFieldData data) {
        Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.FORCE_FIELD_DATA,data);
    }

    public static ForceFieldData getFFData(Entity entity) {
        return Services.PLATFORM.getAttachedValue(entity,CommonDataAttachments.FORCE_FIELD_DATA);
    }

    public static void setIMData(Entity entity, IceManipulationData data) {
        Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.ICE_MANIPULATION_DATA,data);
    }

    public static IceManipulationData getIMData(Entity entity) {
        return Services.PLATFORM.getAttachedValue(entity,CommonDataAttachments.ICE_MANIPULATION_DATA);
    }


    public static AbilityControls getControls(Entity entity) {
        return Services.PLATFORM.getAttachedValue(entity,CommonDataAttachments.ABILITY_CONTROLS);
    }


    public static void setInfinityActive(Player player, boolean infinity) {
        Services.PLATFORM.setAttachedValue(player,CommonDataAttachments.INFINITY,infinity);
        if (player instanceof ServerPlayer) {
            sendBooleanAttachment((ServerPlayer) player, infinity);
        }
    }


    public static boolean isInfinityActive(Entity entity) {
        return Services.PLATFORM.getAttachedValue(entity,CommonDataAttachments.INFINITY);
    }


    public static void sendBooleanAttachment(ServerPlayer player, boolean b) {
        Services.PLATFORM.sendToTracking(new S2CAttachmentTypePacketBoolean(CommonDataAttachments.INFINITY,player.getId(),b),player);
    }


    public static void setORData(Entity entity, ObjectRestorationData data) {
        Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.BLOCK_RESTORATION,data);
    }

    
    public static ObjectRestorationData getORData(Entity entity) {
        return Services.PLATFORM.getAttachedValue(entity,CommonDataAttachments.BLOCK_RESTORATION);
    }

    public static void setLiveGiverState(Entity entity,BlockState state) {
        Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.LIVE_GIVER_STATE,state);
    }

    public static BlockState getLiveGiverState(Entity entity) {
        return Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.LIVE_GIVER_STATE);
    }
}
