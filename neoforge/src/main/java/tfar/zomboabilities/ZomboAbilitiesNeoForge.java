package tfar.zomboabilities;


import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;
import tfar.zomboabilities.client.ModClientNeoForge;
import tfar.zomboabilities.commands.ModCommands;
import tfar.zomboabilities.datagen.ModDatagen;
import tfar.zomboabilities.init.ModAttachmentTypes;
import tfar.zomboabilities.init.ModEntityDataSerializers;
import tfar.zomboabilities.init.ModMobEffects;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.AbilityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod(ZomboAbilities.MOD_ID)
public class ZomboAbilitiesNeoForge {

    public static Map<Registry<?>, List<Pair<ResourceLocation, Supplier<Object>>>> registerLater = new HashMap<>();

    public static final TicketController TICKET_CONTROLLER = new TicketController(ZomboAbilities.id( "chunk_loader"), null);


    public ZomboAbilitiesNeoForge(IEventBus eventBus, Dist dist) {
        eventBus.addListener(PacketHandlerNeoForge::register);
        if (dist.isClient()) {
            ModClientNeoForge.init(eventBus);
        }
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class,event -> ModCommands.register(event.getDispatcher()));
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW,LivingDeathEvent.class,event -> ZomboAbilities.onDeath(event.getEntity()));
        NeoForge.EVENT_BUS.addListener(PlayerEvent.Clone.class,event -> ZomboAbilities.onClone((ServerPlayer) event.getOriginal(), (ServerPlayer) event.getEntity(),!event.isWasDeath()));
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerRespawnEvent.class,event -> ZomboAbilities.onRespawn((ServerPlayer) event.getEntity(),event.isEndConquered()));
        NeoForge.EVENT_BUS.addListener(PlayerTickEvent.Pre.class,pre -> {
            if (pre.getEntity() instanceof ServerPlayer serverPlayer) {
                ZomboAbilities.playerTick(serverPlayer);
            }
        });
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.EntityInteractSpecific.class,event -> ZomboAbilities.interactMob(event.getEntity(),event.getTarget()));
        NeoForge.EVENT_BUS.addListener(ItemEntityPickupEvent.Pre.class,event -> {
            if (event.getPlayer().hasEffect(ModMobEffects.FLOATING_ITEMS)) {
                ItemEntityDuck.of(event.getItemEntity()).setFloatTime(200);
                event.getItemEntity().addDeltaMovement(new Vec3(0,.05,0));
                event.setCanPickup(TriState.FALSE);
            }
        });
        NeoForge.EVENT_BUS.addListener(EntityInvulnerabilityCheckEvent.class, event -> event.setInvulnerable(ZomboAbilities.checkInvulnerable(event.getEntity(),event.getSource(),event.isInvulnerable())));
        NeoForge.EVENT_BUS.addListener(LivingIncomingDamageEvent.class, event -> event.setCanceled(ZomboAbilities.onIncomingDamage(event.getEntity(),event.getSource(),event.getAmount())));
        NeoForge.EVENT_BUS.addListener(LivingDamageEvent.Post.class,event -> ZomboAbilities.onHit(event.getEntity(),event.getSource()));
        NeoForge.EVENT_BUS.addListener(MobEffectEvent.Remove.class,event -> ZomboAbilities.onEffectRemove(event.getEntity(),event.getEffect()));
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW,MobEffectEvent.Expired.class,event -> ZomboAbilities.onEffectExpire(event.getEntity(),event.getEffectInstance()));
        NeoForge.EVENT_BUS.addListener(EntityStruckByLightningEvent.class,event -> ZomboAbilities.onLightning(event.getEntity()));
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.RightClickItem.class,event -> {
            InteractionResult interactionResult = ZomboAbilities.onRightClickItem(event.getLevel(), event.getEntity(), event.getHand(), event.getItemStack());
            if (interactionResult != null) {
                event.setCancellationResult(interactionResult);
            }
        });

        NeoForge.EVENT_BUS.addListener(this::drops);
        NeoForge.EVENT_BUS.addListener(this::xpDrops);

        eventBus.addListener(RegisterEvent.class, this::registerObjs);
        eventBus.addListener(FMLCommonSetupEvent.class,fmlCommonSetupEvent -> registerLater.clear());
        eventBus.addListener(ModDatagen::gather);
        eventBus.addListener(EntityAttributeCreationEvent.class,entityAttributeCreationEvent -> ZomboAbilities.registerAttributes(entityAttributeCreationEvent::put));
        // Use NeoForge to bootstrap the Common mod.
        eventBus.addListener(RegisterTicketControllersEvent.class,event -> event.register(TICKET_CONTROLLER));
        ((MappedRegistry<?>)BuiltInRegistries.BLOCK).unfreeze();
        ((MappedRegistry<?>)BuiltInRegistries.ITEM).unfreeze();
        ((MappedRegistry<?>)BuiltInRegistries.ENTITY_TYPE).unfreeze();
        ((MappedRegistry<?>)BuiltInRegistries.RECIPE_SERIALIZER).unfreeze();
        Services.PLATFORM.registerAll(ModAttachmentTypes.class,NeoForgeRegistries.ATTACHMENT_TYPES,ZomboAbilities.dirtyCast(AttachmentType.class));
        ZomboAbilities.init();

    }

    void drops(BlockDropsEvent event) {
        Entity entity = event.getBreaker();
        if (entity != null && AbilityUtils.hasAbility(entity,Abilities.GENIUS)) {
            event.setDroppedExperience(event.getDroppedExperience() *2);
        }
    }

    void xpDrops(LivingExperienceDropEvent event) {
        Player playerAttacker = event.getAttackingPlayer();
        if (playerAttacker != null && AbilityUtils.hasAbility(playerAttacker,Abilities.GENIUS)) {
            event.setDroppedExperience(event.getDroppedExperience() *2);
        }
    }

    public void registerObjs(RegisterEvent event) {
        ModMobEffects.boot();
        Registry<?> registry = event.getRegistry();
        List<Pair<ResourceLocation,Supplier<Object>>> list = registerLater.get(registry);
        if (list != null) {
            for (Pair<ResourceLocation,Supplier<Object>> pair : list) {
                event.register((ResourceKey<? extends Registry<Object>>)registry.key(),pair.getLeft(), pair.getValue());
            }
        }
        event.register(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS.key(),ZomboAbilities.id("resolvable_profile"),() -> ModEntityDataSerializers.RESOLVABLE_PROFILE);
    }
}