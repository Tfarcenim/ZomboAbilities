package tfar.zomboabilities.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class IllusionPlayerEntity extends ClonePlayerEntity{
    public IllusionPlayerEntity(EntityType<? extends PathfinderMob> $$0, Level $$1) {
        super($$0, $$1);
    }


    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide) {
            level().broadcastEntityEvent(this, EntityEvent.POOF);
            discard();
        }
        return false;
    }
}
