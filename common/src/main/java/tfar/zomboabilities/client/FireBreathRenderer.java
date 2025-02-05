package tfar.zomboabilities.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import tfar.zomboabilities.entity.FireBreathEntity;

public class FireBreathRenderer extends EntityRenderer<FireBreathEntity> {
    protected FireBreathRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FireBreathEntity entity) {
        return null;
    }
}
