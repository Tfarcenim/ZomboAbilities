package tfar.zomboabilities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import tfar.zomboabilities.entity.IceSpikeEntity;

public class IceSpikeRenderer extends EntityRenderer<IceSpikeEntity> {
    private final ItemRenderer itemRenderer;
    private final BlockRenderDispatcher blockRenderer;

    public IceSpikeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(IceSpikeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entity.getYRot()));
        ItemStack itemstack = Items.ICE.getDefaultInstance();

        double age = partialTicks + entity.tickCount;

        double height = Math.max(0,- age * age/8 + 2 * age);

        poseStack.translate(0.0F, height/4, 0);

        poseStack.scale(1, (float) height,1);

        //int j = entity.getRotation();
        //poseStack.mulPose(Axis.ZP.rotationDegrees((float)j * 360.0F / 8.0F));
        //  poseStack.scale(0.5F, 0.5F, 0.5F);
        this.itemRenderer
                .renderStatic(itemstack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), entity.getId());

        poseStack.popPose();

    }

    @Override
    public ResourceLocation getTextureLocation(IceSpikeEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
