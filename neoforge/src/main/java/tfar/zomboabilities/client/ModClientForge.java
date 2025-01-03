package tfar.zomboabilities.client;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.common.NeoForge;
import tfar.zomboabilities.PlayerDuck;

public class ModClientForge {

    public static void init(IEventBus bus) {
        bus.addListener(ModClientForge::keybinds);
        bus.addListener(ModClientForge::layers);
        bus.addListener(ModClientForge::renderers);
        NeoForge.EVENT_BUS.addListener(((ClientTickEvent.Post event) -> ModClient.clientTick()));
        NeoForge.EVENT_BUS.addListener(ModClientForge::renderAfter);
    }

    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        ModClient.registerRenderers(event::registerEntityRenderer);
    }

    static void layers(EntityRenderersEvent.AddLayers event) {

    }

    static void keybinds(RegisterKeyMappingsEvent event) {
        event.register(ModKeybinds.BIND_1);
        event.register(ModKeybinds.BIND_2);
        event.register(ModKeybinds.BIND_3);
        event.register(ModKeybinds.BIND_4);
    }

    static void renderAfter(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        if (PlayerDuck.of(player).isLaserActive()) {
            PlayerRenderer renderer = event.getRenderer();
            LaserEyesRenderer.renderBean(event.getPoseStack(), event.getMultiBufferSource(), (AbstractClientPlayer) player, event.getPartialTick());
        }
    }
}
