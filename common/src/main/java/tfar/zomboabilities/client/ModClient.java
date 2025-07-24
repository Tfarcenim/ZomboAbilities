package tfar.zomboabilities.client;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ResolvableProfile;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.entity.ClonePlayerEntity;
import tfar.zomboabilities.init.ModEntityTypes;
import tfar.zomboabilities.network.C2SUseAbilityPacket;
import tfar.zomboabilities.network.C2SHoldAbilityPacket;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.AbilityUtils;

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
        if (Minecraft.getInstance().level != null) {
            if (!Minecraft.getInstance().isPaused()) {
                boolean holding_p = ModKeybinds.BIND_1.isDown();
                boolean holding_s = ModKeybinds.BIND_2.isDown();
                boolean holding_t = ModKeybinds.BIND_3.isDown();
                boolean holding_q = ModKeybinds.BIND_4.isDown();
                while (ModKeybinds.BIND_1.consumeClick()) {
                    Services.PLATFORM.sendToServer(new C2SUseAbilityPacket(0));
                }
                while (ModKeybinds.BIND_2.consumeClick()) {
                    Services.PLATFORM.sendToServer(new C2SUseAbilityPacket(1));
                }
                while (ModKeybinds.BIND_3.consumeClick()) {
                    Services.PLATFORM.sendToServer(new C2SUseAbilityPacket(2));
                }
                while (ModKeybinds.BIND_4.consumeClick()) {
                    Services.PLATFORM.sendToServer(new C2SUseAbilityPacket(3));
                }
                Services.PLATFORM.sendToServer(new C2SHoldAbilityPacket(holding_p, holding_s, holding_t, holding_q));
                LocalPlayer player = Minecraft.getInstance().player;
                boolean shouldClimb = player.horizontalCollision && AbilityUtils.hasAbility(player, Abilities.SPIDER_GENETICS);
                AbilityUtils.setDataAttachment(player, CommonDataAttachments.CLIMBING,shouldClimb);
            }
        }
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static<T extends Entity> void registerRenderers() {
        EntityRenderers.register(ModEntityTypes.FIRE_BREATH, FireBreathRenderer::new);
        EntityRenderers.register(ModEntityTypes.ICE_SPIKE, IceSpikeRenderer::new);
        EntityRenderers.register(ModEntityTypes.FAST_FALLING_BLOCK, FallingBlockRenderer::new);
    }


    public static ResourceLocation getPlayerSkin(ResolvableProfile gameProfile) {
        SkinManager skinmanager = Minecraft.getInstance().getSkinManager();
        return skinmanager.getInsecureSkin(gameProfile.gameProfile()).texture();
    }
}
