package tfar.zomboabilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Utils {

    public static final StreamCodec<ByteBuf,AABB> AABB_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            aabb -> aabb.minX,
            ByteBufCodecs.DOUBLE,
            aabb -> aabb.minY,
            ByteBufCodecs.DOUBLE,
            aabb -> aabb.minZ,

            ByteBufCodecs.DOUBLE,
            aabb -> aabb.maxX,
            ByteBufCodecs.DOUBLE,
            aabb -> aabb.maxY,
            ByteBufCodecs.DOUBLE,
            aabb -> aabb.maxZ,
            AABB::new
    );

    public static boolean isSunBurnTick(Player player) {
        if (player.level().isDay() && !player.level().isClientSide) {
            float f = player.getLightLevelDependentMagicValue();
            BlockPos blockpos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
            boolean flag = player.isInWaterRainOrBubble() || player.isInPowderSnow || player.wasInPowderSnow;
            if (f > 0.5F && player.getRandom().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag && player.level().canSeeSky(blockpos)) {
                return true;
            }
        }

        return false;
    }

    public static EntityHitResult pick(Entity pEntity, double pBlockInteractionRange, double pEntityInteractionRange, float pPartialTick) {
        double d0 = Math.max(pBlockInteractionRange, pEntityInteractionRange);
        double d1 = Mth.square(d0);
        Vec3 vec3 = pEntity.getEyePosition(pPartialTick);
        HitResult hitresult = pEntity.pick(d0, pPartialTick, false);
        double d2 = hitresult.getLocation().distanceToSqr(vec3);
        if (hitresult.getType() != HitResult.Type.MISS) {
            d1 = d2;
            d0 = Math.sqrt(d2);
        }

        Vec3 vec31 = pEntity.getViewVector(pPartialTick);
        Vec3 vec32 = vec3.add(vec31.x * d0, vec31.y * d0, vec31.z * d0);
        float f = 1.0F;
        AABB aabb = pEntity.getBoundingBox().expandTowards(vec31.scale(d0)).inflate(f, f, f);
        EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(
                pEntity, vec3, vec32, aabb, entity -> !entity.isSpectator() && entity.isPickable(), d1
        );
        return entityhitresult;
    }

    public static HitResult pickEither(Entity pEntity, double pBlockInteractionRange, double pEntityInteractionRange, float pPartialTick) {
        EntityHitResult entityHitResult = pick(pEntity,pBlockInteractionRange,pEntityInteractionRange,pPartialTick);
        if (entityHitResult != null) return entityHitResult;

        return pEntity.pick(pEntityInteractionRange, pPartialTick, true);
    }
    
}
