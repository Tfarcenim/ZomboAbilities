package tfar.zomboabilities.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public boolean noPhysics;

    @Shadow private EntityDimensions dimensions;

    @Shadow public abstract Vec3 getEyePosition();

    @Shadow public abstract Level level();

    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean isInWall() {
        if (this.noPhysics) {
            return false;
        } else {
            float f = this.dimensions.width() * 0.8F;
            AABB aabb = AABB.ofSize(this.getEyePosition(), f, 1.0E-6, f);
            return BlockPos.betweenClosedStream(aabb)
                    .anyMatch(
                            pos -> {
                                BlockState blockstate = this.level().getBlockState(pos);
                                return !blockstate.isAir()
                                        && blockstate.isSuffocating(this.level(), pos)
                                        && Shapes.joinIsNotEmpty(
                                        blockstate.getCollisionShape(this.level(), pos, CollisionContext.of((Entity)(Object)this))
                                                .move(pos.getX(), pos.getY(), pos.getZ()),
                                        Shapes.create(aabb),
                                        BooleanOp.AND
                                );
                            }
                    );
        }
    }
}
