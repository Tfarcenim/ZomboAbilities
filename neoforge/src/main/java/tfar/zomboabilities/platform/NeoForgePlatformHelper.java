package tfar.zomboabilities.platform;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.commons.lang3.tuple.Pair;
import tfar.zomboabilities.abilities.AbilityControls;
import tfar.zomboabilities.data.AbilityData;
import tfar.zomboabilities.data.ForceFieldData;
import tfar.zomboabilities.PacketHandlerNeoForge;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.ZomboAbilitiesNeoForge;
import tfar.zomboabilities.data.LivesData;
import tfar.zomboabilities.datagen.ModDatagen;
import tfar.zomboabilities.init.ModAttachmentTypes;
import tfar.zomboabilities.network.C2SModPacket;
import tfar.zomboabilities.network.S2CModPacket;
import tfar.zomboabilities.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public <F> void registerAll(Map<String, ? extends F> map, Registry<F> registry, Class<? extends F> filter) {
        List<Pair<ResourceLocation, Supplier<Object>>> list = ZomboAbilitiesNeoForge.registerLater.computeIfAbsent(registry, k -> new ArrayList<>());
        for (Map.Entry<String, ? extends F> entry : map.entrySet()) {
            list.add(Pair.of(ZomboAbilities.id(entry.getKey()), entry::getValue));
        }
    }

    public static PayloadRegistrar registrar;

    @Override
    public <MSG extends S2CModPacket<?>> void registerClientPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf,MSG> streamCodec) {
        registrar.playToClient(type, streamCodec, (p, t) -> p.handleClient());
    }

    @Override
    public <MSG extends C2SModPacket<?>> void registerServerPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec) {
        registrar.playToServer(type, streamCodec, (p, t) -> p.handleServer((ServerPlayer) t.player()));
    }


    @Override
    public void sendToClient(S2CModPacket<?> msg, ServerPlayer player) {
        PacketHandlerNeoForge.sendToClient(msg, player);
    }

    @Override
    public void sendToServer(C2SModPacket<?> msg) {
        PacketHandlerNeoForge.sendToServer(msg);
    }

    @Override
    public void sendToTracking(S2CModPacket<?> msg, Entity entity) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity,msg);
    }

    @Override
    public Holder<Attribute> getSwimSpeed() {
        return NeoForgeMod.SWIM_SPEED;
    }

    @Override
    public Pair<Boolean, Vec3> teleportEvent(LivingEntity entity, double targetX, double targetY, double targetZ) {
        EntityTeleportEvent.EnderEntity event = net.neoforged.neoforge.event.EventHooks.onEnderTeleport(entity, targetX, targetY, targetZ);

        return Pair.of(event.isCanceled(),new Vec3(event.getTargetX(),event.getTargetY(),event.getTargetZ()));
    }

    @Override
    public void setFFData(Entity entity, ForceFieldData data) {
        entity.setData(ModAttachmentTypes.FORCE_FIELD_DATA,data);
    }

    @Override
    public ForceFieldData getFFData(Entity entity) {
        return entity.getData(ModAttachmentTypes.FORCE_FIELD_DATA);
    }

    @Override
    public void setAData(Entity entity, AbilityData data) {
        entity.setData(ModAttachmentTypes.ABILITY_DATA,data);
    }

    @Override
    public AbilityData getAData(Entity entity) {
        return entity.getData(ModAttachmentTypes.ABILITY_DATA);
    }

    @Override
    public LivesData getLData(Entity entity) {
        return entity.getData(ModAttachmentTypes.LIVES_DATA);
    }

    @Override
    public void setLData(Entity entity, LivesData data) {
        entity.setData(ModAttachmentTypes.LIVES_DATA,data);
    }



    @Override
    public AbilityControls getControls(Entity entity) {
        return entity.getData(ModAttachmentTypes.ABILITY_CONTROLS);
    }
}