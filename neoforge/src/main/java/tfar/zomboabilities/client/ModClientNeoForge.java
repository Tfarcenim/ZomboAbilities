package tfar.zomboabilities.client;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.common.NeoForge;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.init.ModBlocks;
import tfar.zomboabilities.init.ModItems;

public class ModClientNeoForge {

    public static void init(IEventBus bus) {
        bus.addListener(ModClientNeoForge::keybinds);
        bus.addListener(ModClientNeoForge::layers);
        bus.addListener(ModClientNeoForge::renderers);
        bus.addListener(ModClientNeoForge::setup);
        NeoForge.EVENT_BUS.addListener(((ClientTickEvent.Post event) -> ModClient.clientTick()));
        NeoForge.EVENT_BUS.addListener(ModClientNeoForge::renderAfter);
    }

    static void setup(FMLClientSetupEvent event) {
        ItemProperties.register(ModItems.FORCE_SHIELD,
                ResourceLocation.withDefaultNamespace("blocking"),
                (p_174575_, p_174576_, p_174577_, p_174578_) -> p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FORCE_FIELD, RenderType.translucent());
    }

    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        ModClient.registerRenderers();
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
        if (PlayerDuck.of(player).isPrimaryActive()) {
            PlayerRenderer renderer = event.getRenderer();
            LaserEyesRenderer.renderBean(event.getPoseStack(), event.getMultiBufferSource(), (AbstractClientPlayer) player, event.getPartialTick());
        }
    }
}
