package tfar.zomboabilities.abilities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import tfar.zomboabilities.utils.Utils;
import tfar.zomboabilities.network.S2CParticleAABBPacket;
import tfar.zomboabilities.platform.Services;

public class BoogieWoogieAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        EntityHitResult pick = Utils.pick(player, player.blockInteractionRange(), player.entityInteractionRange(), 0);
        if (pick != null) {
            Entity entity = pick.getEntity();
            Vec3 pos1 = player.position();
            float pitch1 = player.getXRot();
            float yaw1 = player.getYRot();

            Vec3 pos2 = entity.position();
            float pitch2 = entity.getXRot();
            float yaw2 = entity.getYRot();

            player.connection.teleport(pos2.x,pos2.y,pos2.z,pitch2,yaw2);
            entity.setPos(pos1);
            entity.setXRot(pitch1);
            entity.setYRot(yaw1);
            applyCooldown(0,60,player);
            Services.PLATFORM.sendToClient(new S2CParticleAABBPacket(ParticleTypes.DOLPHIN,player.getBoundingBox(),100),player);
            Services.PLATFORM.sendToClient(new S2CParticleAABBPacket(ParticleTypes.DOLPHIN,entity.getBoundingBox(),100),player);
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
