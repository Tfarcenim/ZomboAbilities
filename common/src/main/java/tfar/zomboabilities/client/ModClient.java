package tfar.zomboabilities.client;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ResolvableProfile;
import tfar.zomboabilities.abilities.IceSpikeRenderer;
import tfar.zomboabilities.entity.ClonePlayerEntity;
import tfar.zomboabilities.init.ModEntityTypes;
import tfar.zomboabilities.network.C2SAbilityPacket;
import tfar.zomboabilities.network.C2SHoldAbilityPacket;
import tfar.zomboabilities.platform.Services;

import java.util.Map;

public class ModClient {

    private static final Map<PlayerSkin.Model, EntityRendererProvider<ClonePlayerEntity>> CLONE_PROVIDERS = ImmutableMap.of(
            PlayerSkin.Model.WIDE, (context) -> new ClonePlayerEntityRenderer(context, false),
            PlayerSkin.Model.SLIM, (context) -> new ClonePlayerEntityRenderer(context, true));

    public static Map<PlayerSkin.Model, EntityRenderer<ClonePlayerEntity>> createCloneRenderers(EntityRendererProvider.Context context) {
        ImmutableMap.Builder<PlayerSkin.Model, EntityRenderer<ClonePlayerEntity>> builder = ImmutableMap.builder();
        CLONE_PROVIDERS.forEach((s, provider) -> {
            try {
                builder.put(s, provider.create(context));
            } catch (Exception var5) {
                throw new IllegalArgumentException("Failed to create player model for " + s, var5);
            }
        });
        return builder.build();
    }

    public static void clientTick() {
        if (Minecraft.getInstance().level != null && !Minecraft.getInstance().isPaused()) {
            boolean holding_p = ModKeybinds.BIND_1.isDown();
            boolean holding_s = ModKeybinds.BIND_2.isDown();
            boolean holding_t = ModKeybinds.BIND_3.isDown();
            boolean holding_q = ModKeybinds.BIND_4.isDown();
            while (ModKeybinds.BIND_1.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SAbilityPacket(0));
            }
            while (ModKeybinds.BIND_2.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SAbilityPacket(1));
            }
            while (ModKeybinds.BIND_3.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SAbilityPacket(2));
            }
            while (ModKeybinds.BIND_4.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SAbilityPacket(3));
            }
            Services.PLATFORM.sendToServer(new C2SHoldAbilityPacket(holding_p, holding_s, holding_t, holding_q));
        }
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static<T extends Entity> void registerRenderers() {
        EntityRenderers.register(ModEntityTypes.FIRE_BREATH, FireBreathRenderer::new);
        EntityRenderers.register(ModEntityTypes.ICE_SPIKE, IceSpikeRenderer::new);
    }


    public static ResourceLocation getPlayerSkin(ResolvableProfile gameProfile) {
        SkinManager skinmanager = Minecraft.getInstance().getSkinManager();
        return skinmanager.getInsecureSkin(gameProfile.gameProfile()).texture();
    }
}
