package tfar.zomboabilities;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import tfar.zomboabilities.client.ModClientForge;
import tfar.zomboabilities.commands.ModCommands;
import tfar.zomboabilities.datagen.ModDatagen;
import tfar.zomboabilities.init.ModMobEffects;
import tfar.zomboabilities.platform.NeoForgePlatformHelper;

@Mod(ZomboAbilities.MOD_ID)
public class ZomboAbilitiesNeoForge {

    public ZomboAbilitiesNeoForge(IEventBus eventBus, Dist dist) {
        eventBus.addListener(PacketHandlerNeoForge::register);
        if (dist.isClient()) {
            ModClientForge.init(eventBus);
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
        NeoForge.EVENT_BUS.addListener(LivingDamageEvent.Post.class,event -> ZomboAbilities.onHit(event.getEntity(),event.getSource()));
        NeoForge.EVENT_BUS.addListener(MobEffectEvent.Remove.class,event -> ZomboAbilities.onEffectRemove(event.getEntity(),event.getEffectInstance()));
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW,MobEffectEvent.Expired.class,event -> ZomboAbilities.onEffectRemove(event.getEntity(),event.getEffectInstance()));
        NeoForge.EVENT_BUS.addListener(EntityStruckByLightningEvent.class,event -> ZomboAbilities.onLightning(event.getEntity()));

        eventBus.addListener(RegisterEvent.class,event -> ModMobEffects.boot());
        eventBus.addListener(ModDatagen::gather);
        // Use NeoForge to bootstrap the Common mod.
        ZomboAbilities.init();

    }

}