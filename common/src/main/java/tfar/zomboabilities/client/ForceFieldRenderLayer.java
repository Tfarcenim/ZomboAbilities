package tfar.zomboabilities.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import tfar.zomboabilities.init.ModBlocks;
import tfar.zomboabilities.platform.Services;

public class ForceFieldRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public ForceFieldRenderLayer(EntityRendererProvider.Context context, RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
        super(renderer);
        blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (Services.PLATFORM.isInfinityActive(livingEntity)) {
             poseStack.pushPose();

            poseStack.translate(-0.75, -0.75, -0.75);

            //int j = entity.getRotation();
            //poseStack.mulPose(Axis.ZP.rotationDegrees((float)j * 360.0F / 8.0F));
            poseStack.scale(1.5F, 2.5F, 1.5F);
            this.blockRenderDispatcher.renderSingleBlock(ModBlocks.FORCE_FIELD.defaultBlockState(), poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);

            poseStack.popPose();
        }
    }
}
