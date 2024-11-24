package tfar.zomboabilities;


import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import tfar.zomboabilities.commands.ModCommands;

@Mod(ZomboAbilities.MOD_ID)
public class ZomboAbilitiesNeoForge {

    public ZomboAbilitiesNeoForge(IEventBus eventBus) {
        eventBus.addListener(RegisterCommandsEvent.class,event -> ModCommands.register(event.getDispatcher()));
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW,LivingDeathEvent.class,event -> ZomboAbilities.onDeath(event.getEntity()));
        // Use NeoForge to bootstrap the Common mod.
        ZomboAbilities.init();

    }

}