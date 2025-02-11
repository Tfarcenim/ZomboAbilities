package tfar.zomboabilities.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class IceSpikeEntity extends Projectile {

    public static final int LIFE = 20;
    public IceSpikeEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount > LIFE && !level().isClientSide) {
            discard();
        }
    }

    @Override
    public void push(Entity entity) {
        super.push(entity);

        Entity owner = getOwner();
        if (owner instanceof Player) {
            entity.hurt(damageSources().playerAttack((Player) owner),4);
        } else if (owner instanceof LivingEntity) {
            entity.hurt(damageSources().mobAttack((LivingEntity) owner),4);
        }
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

}
