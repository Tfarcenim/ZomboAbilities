package tfar.zomboabilities.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tfar.zomboabilities.client.ModClient;
import tfar.zomboabilities.entity.ClonePlayerEntity;

import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    private Map<PlayerSkin.Model, EntityRenderer<ClonePlayerEntity>> cloneRenderers = ImmutableMap.of();


    @SuppressWarnings("unchecked")
    @Inject(at = @At("HEAD"), method = "getRenderer",cancellable = true)
    private <T extends Entity> void init(T entity, CallbackInfoReturnable<EntityRenderer<? super T>> cir) {
        if (entity instanceof ClonePlayerEntity clonePlayerEntity) {
            GameProfile clone = clonePlayerEntity.getClone().gameProfile();
            PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(clone.getId());
            if (playerInfo != null) {
                cir.setReturnValue((EntityRenderer<? super T>) cloneRenderers.get(playerInfo.getSkin().model()));
            } else {
                cir.setReturnValue((EntityRenderer<? super T>) cloneRenderers.get(PlayerSkin.Model.WIDE));
            }
        }
    }

    @Inject(method = "onResourceManagerReload",at = @At("RETURN"),locals = LocalCapture.CAPTURE_FAILHARD)
    private void resourceHook(ResourceManager $$0, CallbackInfo ci, EntityRendererProvider.Context context) {
        cloneRenderers = ModClient.createCloneRenderers(context);
    }
}