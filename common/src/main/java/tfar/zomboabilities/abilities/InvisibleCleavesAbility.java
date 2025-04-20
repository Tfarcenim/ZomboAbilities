package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import tfar.zomboabilities.utils.Utils;

//Invisible Cleaves
//
//Pressing R - This Ability allows you to basically hit someone with an invisible sword, it will do as much as an iron sword,
// it won't even show the player moving there hands, Your basically Sukuna.
public class InvisibleCleavesAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        EntityHitResult entityHitResult = Utils.pickEntity(player,player.blockInteractionRange(),player.entityInteractionRange(),1);
        if (entityHitResult != null) {
            Entity entity = entityHitResult.getEntity();
            if (entity.isAttackable()) {
                player.attack(entity);
            }
        }
    }

    @Override
    public void secondary(ServerPlayer player) {

    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }
}
