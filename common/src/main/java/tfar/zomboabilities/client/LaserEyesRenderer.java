package tfar.zomboabilities.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class LaserEyesRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public LaserEyesRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
        super(renderer);
    }

    private static final ResourceLocation BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/end_gateway_beam.png");


    private static final ResourceLocation GUARDIAN_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/guardian.png");
    private static final ResourceLocation GUARDIAN_BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/guardian_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_LOCATION);

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        long gameTime = livingEntity.level().getGameTime();
        poseStack.pushPose();
        Vec3 look = livingEntity.getLookAngle();
        //poseStack.translate(-.5,-.75,-.25);
        //poseStack.mulPose(Axis.XP.rotationDegrees(-90));
        //poseStack.mulPose(Axis.XP.rotationDegrees(headPitch));
        //BeaconRenderer.renderBeaconBeam(poseStack,bufferSource,BEAM_LOCATION,partialTick,1,gameTime,0,100,0xff0000,1/32f,1/32f);
        //poseStack.translate(0,livingEntity.getEyeHeight(),0);
    //    renderVector(poseStack, bufferSource.getBuffer(RenderType.lines()), new Vector3f(0.0F, 0, 0.0F), livingEntity.getViewVector(partialTick)
      //          .scale(2.0), 0xff00ff00);

        renderBean(poseStack,bufferSource,livingEntity,partialTick);

        poseStack.popPose();
    }

    private static Vec3 getPosition(LivingEntity livingEntity, double yOffset, float partialTick) {
        return getPosition(livingEntity.xOld,livingEntity.yOld,livingEntity.zOld,livingEntity.position(),yOffset,partialTick);
    }

    private static Vec3 getPosition(double xOld,double yOld,double zOld, Vec3 pos,double yOffset, float partialTick) {
        double d0 = Mth.lerp(partialTick, xOld, pos.x);
        double d1 = Mth.lerp(partialTick, yOld, pos.y) + yOffset;
        double d2 = Mth.lerp(partialTick, zOld, pos.z);
        return new Vec3(d0, d1, d2);
    }

    public static void renderBean(PoseStack poseStack, MultiBufferSource buffer, AbstractClientPlayer entity, float partialTicks) {
        LivingEntity livingentity = entity.getLastHurtMob();
        float f = .5f;//entity.getAttackAnimationScale(partialTicks);
        float f1 = 1;//entity.getClientSideAttackTime() + partialTicks;
        float f2 = f1 * 0.5F % 1.0F;
        float f3 = entity.getEyeHeight();
        poseStack.pushPose();
        poseStack.translate(0.0F, f3, 0.0F);

        Vec3 look = entity.getViewVector(partialTicks).scale(3);

        Vec3 vec31 = getPosition(entity, f3, partialTicks);


        Vec3 lookOffset = vec31.add(look);

        Vec3 vec3 = getPosition(lookOffset.x,lookOffset.y,lookOffset.z,lookOffset, 0, partialTicks);
        Vec3 vec32 = vec3.subtract(vec31);
        float f4 = (float) (vec32.length() + 1.0);
        vec32 = vec32.normalize();
        float f5 = (float) Math.acos(vec32.y);
        float f6 = (float) Math.atan2(vec32.z, vec32.x);
        poseStack.mulPose(Axis.YP.rotationDegrees(((float) (Math.PI / 2) - f6) * (180.0F / (float) Math.PI)));
        poseStack.mulPose(Axis.XP.rotationDegrees(f5 * (180.0F / (float) Math.PI)));
        int i = 1;
        float f7 = f1 * 0.05F * -1.5F;
        float f8 = f * f;
        int j = 64 + (int) (f8 * 191.0F);
        int k = 32 + (int) (f8 * 191.0F);
        int l = 128 - (int) (f8 * 64.0F);
        float f9 = 0.2F;
        float f10 = 0.282F;
        float f11 = Mth.cos(f7 + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
        float f12 = Mth.sin(f7 + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
        float f13 = Mth.cos(f7 + (float) (Math.PI / 4)) * 0.282F;
        float f14 = Mth.sin(f7 + (float) (Math.PI / 4)) * 0.282F;
        float f15 = Mth.cos(f7 + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
        float f16 = Mth.sin(f7 + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
        float f17 = Mth.cos(f7 + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
        float f18 = Mth.sin(f7 + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
        float f19 = Mth.cos(f7 + (float) Math.PI) * 0.2F;
        float f20 = Mth.sin(f7 + (float) Math.PI) * 0.2F;
        float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
        float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
        float f23 = Mth.cos(f7 + (float) (Math.PI / 2)) * 0.2F;
        float f24 = Mth.sin(f7 + (float) (Math.PI / 2)) * 0.2F;
        float f25 = Mth.cos(f7 + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
        float f26 = Mth.sin(f7 + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
        float f27 = 0.0F;
        float f28 = 0.4999F;
        float f29 = -1.0F + f2;
        float f30 = f4 * 2.5F + f29;
        VertexConsumer vertexconsumer = buffer.getBuffer(BEAM_RENDER_TYPE);
        PoseStack.Pose posestack$pose = poseStack.last();
        vertex(vertexconsumer, posestack$pose, f19, f4, f20, j, k, l, 0.4999F, f30);
        vertex(vertexconsumer, posestack$pose, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
        vertex(vertexconsumer, posestack$pose, f21, 0.0F, f22, j, k, l, 0.0F, f29);
        vertex(vertexconsumer, posestack$pose, f21, f4, f22, j, k, l, 0.0F, f30);
        vertex(vertexconsumer, posestack$pose, f23, f4, f24, j, k, l, 0.4999F, f30);
        vertex(vertexconsumer, posestack$pose, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
        vertex(vertexconsumer, posestack$pose, f25, 0.0F, f26, j, k, l, 0.0F, f29);
        vertex(vertexconsumer, posestack$pose, f25, f4, f26, j, k, l, 0.0F, f30);
        float f31 = 0.0F;
        if (entity.tickCount % 2 == 0) {
            f31 = 0.5F;
        }

        vertex(vertexconsumer, posestack$pose, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
        vertex(vertexconsumer, posestack$pose, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
        vertex(vertexconsumer, posestack$pose, f17, f4, f18, j, k, l, 1.0F, f31);
        vertex(vertexconsumer, posestack$pose, f15, f4, f16, j, k, l, 0.5F, f31);
        poseStack.popPose();
    }


    private static void vertex(
            VertexConsumer consumer,
            PoseStack.Pose pose,
            float x,
            float y,
            float z,
            int red,
            int green,
            int blue,
            float u,
            float v
    ) {
        consumer.addVertex(pose, x, y, z)
                .setColor(red, green, blue, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(15728880)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }



private static void renderVector(PoseStack poseStack, VertexConsumer buffer, Vector3f startPos, Vec3 vector, int color) {
    PoseStack.Pose posestack$pose = poseStack.last();
    buffer.addVertex(posestack$pose, startPos)
            .setColor(color)
            .setNormal(posestack$pose, (float)vector.x, (float)vector.y, (float)vector.z);
    buffer.addVertex(
                    posestack$pose,
                    (float)((double)startPos.x() + vector.x),
                    (float)((double)startPos.y() + vector.y),
                    (float)((double)startPos.z() + vector.z)
            )
            .setColor(color)
            .setNormal(posestack$pose, (float)vector.x, (float)vector.y, (float)vector.z);
    }

}
