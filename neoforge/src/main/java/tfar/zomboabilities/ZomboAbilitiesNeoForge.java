package tfar.zomboabilities;


import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.client.ModClientNeoForge;
import tfar.zomboabilities.commands.ModCommands;
import tfar.zomboabilities.data.ObjectRestorationData;
import tfar.zomboabilities.datagen.ModDatagen;
import tfar.zomboabilities.init.*;
import tfar.zomboabilities.utils.AbilityUtils;

@Mod(ZomboAbilities.MOD_ID)
public class ZomboAbilitiesNeoForge {


    public static final TicketController TICKET_CONTROLLER = new TicketController(ZomboAbilities.id( "chunk_loader"), null);


    public ZomboAbilitiesNeoForge(IEventBus eventBus, ModContainer container, Dist dist) {
        container.registerConfig(ModConfig.Type.SERVER,ZomboAbilitiesConfig.SERVER_SPEC);
        eventBus.addListener(PacketHandlerNeoForge::register);
        if (dist.isClient()) {
            ModClientNeoForge.init(eventBus);
        }

        NeoForge.EVENT_BUS.addListener(ServerStartedEvent.class,event -> {
            try {
                ZomboAbilities.onServerStart(event.getServer());
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        NeoForge.EVENT_BUS.addListener(LivingFallEvent.class,event -> {
            if (AbilityUtils.hasAbility(event.getEntity(),Abilities.SLIME_GENETICS)) {
                event.setDamageMultiplier(0);
            }
        });
        NeoForge.EVENT_BUS.addListener(LivingEntityUseItemEvent.Finish.class,event -> ZomboAbilities.onItemFinished(event.getEntity(),event.getItem(),event.getDuration(),event.getResultStack()));
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

        NeoForge.EVENT_BUS.addListener(EntityTickEvent.Post.class,event -> ZomboAbilities.entityTick(event.getEntity()));

        NeoForge.EVENT_BUS.addListener(AttackEntityEvent.class,event -> event.setCanceled(ZomboAbilities.onAttack(event.getEntity(),event.getTarget())));

        NeoForge.EVENT_BUS.addListener(LevelTickEvent.Pre.class,pre -> {
            if (pre.getLevel() instanceof ServerLevel serverLevel) {
                ZomboAbilities.levelTick(serverLevel);
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
        NeoForge.EVENT_BUS.addListener(LivingDamageEvent.Post.class,event -> ZomboAbilities.onDamaged(event.getEntity(),event.getSource()));
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
        NeoForge.EVENT_BUS.addListener(this::harvestCheck);
        NeoForge.EVENT_BUS.addListener(this::projectileImpact);

        eventBus.addListener(RegisterEvent.class, this::registerObjs);
        eventBus.addListener(ModDatagen::gather);
        eventBus.addListener(EntityAttributeCreationEvent.class,entityAttributeCreationEvent -> ZomboAbilities.registerAttributes(entityAttributeCreationEvent::put));
        // Use NeoForge to bootstrap the Common mod.
        eventBus.addListener(RegisterTicketControllersEvent.class,event -> event.register(TICKET_CONTROLLER));
        ZomboAbilities.init();

    }

    void projectileImpact(ProjectileImpactEvent event) {
        Entity entity = event.getEntity();
        Projectile projectile = event.getProjectile();
        boolean sandMan = AbilityUtils.hasAbility(entity,Abilities.SAND_BODY);
        if (sandMan && (projectile instanceof AbstractArrow || projectile instanceof Fireball)) {
            event.setCanceled(true);
        }
        if (projectile instanceof AbstractArrow && AbilityUtils.hasAbility(entity,Abilities.SLIME_GENETICS)) {
            projectile.setDeltaMovement(projectile.getDeltaMovement().scale(-1));
            projectile.hurtMarked = true;
            event.setCanceled(true);
        }
    }

    void drops(BlockDropsEvent event) {
        Entity entity = event.getBreaker();
        if (entity != null) {
            if (AbilityUtils.hasAbility(entity, Abilities.GENIUS)) {
                event.setDroppedExperience(event.getDroppedExperience() * 2);
            } else if (AbilityUtils.hasAbility(entity, Abilities.OBJECT_RESTORATION)) {
                if (event.getDrops().isEmpty()) {
                    entity.setData((AttachmentType<ObjectRestorationData>)CommonDataAttachments.BLOCK_RESTORATION.getAttachment(),new ObjectRestorationData(event.getPos(),event.getState()));
                }
            }
        }
    }

    void harvestCheck(PlayerEvent.HarvestCheck event) {
        Entity entity = event.getEntity();
        if (AbilityUtils.hasAbility(entity, Abilities.OBJECT_RESTORATION)) {
            if (!event.canHarvest()) {
                entity.setData((AttachmentType<ObjectRestorationData>)CommonDataAttachments.BLOCK_RESTORATION.getAttachment(), new ObjectRestorationData(event.getPos(), event.getTargetBlock()));
            }
        }
    }

    void xpDrops(LivingExperienceDropEvent event) {
        Player playerAttacker = event.getAttackingPlayer();
        if (playerAttacker != null && AbilityUtils.hasAbility(playerAttacker,Abilities.GENIUS)) {
            event.setDroppedExperience(event.getDroppedExperience() *2);
        }
    }

    public void registerObjs(RegisterEvent event) {
        if (event.getRegistry() == BuiltInRegistries.BLOCK) {
            ModMobEffects.boot();
            CommonDataAttachments.init();
            ModBlocks.init();
            ModItems.init();
            ModEntityTypes.init();
            ModRecipeSerializers.init();
            ModFluids.init();
        }

        event.register(NeoForgeRegistries.FLUID_TYPES.key(),ZomboAbilities.id("acid"), ModFluids.ACID::getFluidType);

        event.register(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS.key(),ZomboAbilities.id("resolvable_profile"),() -> ModEntityDataSerializers.RESOLVABLE_PROFILE);
    }
}