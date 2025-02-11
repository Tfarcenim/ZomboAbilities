package tfar.zomboabilities.platform.services;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import tfar.zomboabilities.abilities.AbilityControls;
import tfar.zomboabilities.data.AbilityData;
import tfar.zomboabilities.data.ForceFieldData;
import tfar.zomboabilities.data.IceManipulationData;
import tfar.zomboabilities.data.LivesData;
import tfar.zomboabilities.network.C2SModPacket;
import tfar.zomboabilities.network.S2CModPacket;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    default  <F> void registerAll(Class<?> clazz, Registry<F> registry, Class<? extends F> filter) {
        Map<String, F> map = new HashMap<>();
        for (Field field : clazz.getFields()) {
            try {
                Object o = field.get(null);
                if (filter.isInstance(o)) {
                    map.put(field.getName().toLowerCase(Locale.ROOT), (F) o);
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
        registerAll(map, registry, filter);
    }

    <F> void registerAll(Map<String,? extends F> map, Registry<F> registry, Class<? extends F> filter);

    <MSG extends S2CModPacket<?>> void registerClientPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf,MSG> streamCodec);
    <MSG extends C2SModPacket<?>> void registerServerPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf,MSG> streamCodec);

    void sendToClient(S2CModPacket<?> msg, ServerPlayer player);
    void sendToServer(C2SModPacket<?> msg);
    void sendToTracking(S2CModPacket<?> msg, Entity entity);

    Holder<Attribute> getSwimSpeed();

    Pair<Boolean, Vec3> teleportEvent(LivingEntity entity, double targetX, double targetY, double targetZ);

    void setFFData(Entity entity, ForceFieldData data);
    ForceFieldData getFFData(Entity entity);

    void setAData(Entity entity, AbilityData data);
    AbilityData getAData(Entity entity);

    void setLData(Entity entity, LivesData data);
    LivesData getLData(Entity entity);

    void setIMData(Entity entity, IceManipulationData data);
    IceManipulationData getIMData(Entity entity);

    AbilityControls getControls(Entity entity);

}