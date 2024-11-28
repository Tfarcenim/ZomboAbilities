package tfar.zomboabilities.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

public class ModClientForge {

    public static void init(IEventBus bus) {
        bus.addListener(ModClientForge::keybinds);
        NeoForge.EVENT_BUS.addListener(((ClientTickEvent.Post event) -> ModClient.clientTick()));
    }

    static void keybinds(RegisterKeyMappingsEvent event) {
        event.register(ModKeybinds.BIND_1);
        event.register(ModKeybinds.BIND_2);
        event.register(ModKeybinds.BIND_3);
        event.register(ModKeybinds.BIND_4);
    }
}
