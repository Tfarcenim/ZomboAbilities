package tfar.zomboabilities;


import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import tfar.zomboabilities.commands.ModCommands;
import tfar.zomboabilities.datagen.ModDatagen;

@Mod(ZomboAbilities.MOD_ID)
public class ZomboAbilitiesNeoForge {

    public ZomboAbilitiesNeoForge(IEventBus eventBus) {
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class,event -> ModCommands.register(event.getDispatcher()));
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW,LivingDeathEvent.class,event -> ZomboAbilities.onDeath(event.getEntity()));
        NeoForge.EVENT_BUS.addListener(PlayerEvent.Clone.class,event -> ZomboAbilities.onClone((ServerPlayer) event.getOriginal(), (ServerPlayer) event.getEntity(),!event.isWasDeath()));
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerRespawnEvent.class,event -> ZomboAbilities.onRespawn((ServerPlayer) event.getEntity(),event.isEndConquered()));
        eventBus.addListener(ModDatagen::gather);
        // Use NeoForge to bootstrap the Common mod.
        ZomboAbilities.init();

    }

}