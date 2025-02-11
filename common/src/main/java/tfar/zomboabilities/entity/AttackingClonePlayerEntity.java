package tfar.zomboabilities.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;
import tfar.zomboabilities.entity.ai.CloneFollowOwnerGoal;
import tfar.zomboabilities.entity.ai.CloneOwnerHurtByTargetGoal;
import tfar.zomboabilities.entity.ai.CloneOwnerHurtTargetGoal;

public class AttackingClonePlayerEntity extends ClonePlayerEntity{
    public AttackingClonePlayerEntity(EntityType<? extends PathfinderMob> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, this::shouldAttack));
        goalSelector.addGoal(3,new CloneFollowOwnerGoal(this,1,4,12,false));

        this.targetSelector.addGoal(1, new CloneOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new CloneOwnerHurtTargetGoal(this));
    }


}
