package tfar.zomboabilities.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import tfar.zomboabilities.init.ModEntityTypes;

import javax.annotation.Nullable;

public class FireBreathEntity extends AbstractHurtingProjectile {
    public FireBreathEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    protected FireBreathEntity(EntityType<? extends AbstractHurtingProjectile> entityType, double x, double y, double z, Level level) {
       super(entityType, x, y, z, level);
    }

    public FireBreathEntity(EntityType<? extends AbstractHurtingProjectile> entityType, double x, double y, double z, Vec3 movement, Level level) {
        super(entityType, x, y, z, movement, level);
    }

    public FireBreathEntity(EntityType<? extends AbstractHurtingProjectile> entityType, LivingEntity owner, Vec3 movement, Level level) {
        super(entityType, owner, movement, level);
    }

    public static FireBreathEntity shootFromEyes(LivingEntity owner, Vec3 movement, Level level) {
        FireBreathEntity fireBreathEntity = new FireBreathEntity(ModEntityTypes.FIRE_BREATH,owner,movement,level);
        fireBreathEntity.setPos(fireBreathEntity.position().add(0,owner.getEyeHeight(),0));
        return fireBreathEntity;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level() instanceof ServerLevel serverlevel) {
            Entity target = result.getEntity();
            Entity owner = this.getOwner();
            int $$5 = target.getRemainingFireTicks();
            target.igniteForSeconds(5.0F);
            DamageSource $$6 = this.damageSources().source(DamageTypes.FIREBALL,this, owner);
            if (!target.hurt($$6, 5.0F)) {
                target.setRemainingFireTicks($$5);
            } else {
                EnchantmentHelper.doPostAttackEffects(serverlevel, target, $$6);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            Entity entity = this.getOwner();
           // if (!(entity instanceof Mob) || net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level(), entity)) {
                BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
                if (this.level().isEmptyBlock(blockpos)) {
                    this.level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level(), blockpos));
                }
          //  }
        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Nullable
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }
}
