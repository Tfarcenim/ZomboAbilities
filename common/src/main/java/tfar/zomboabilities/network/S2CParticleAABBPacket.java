package tfar.zomboabilities.network;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import tfar.zomboabilities.utils.Utils;
import tfar.zomboabilities.client.ModClient;

public record S2CParticleAABBPacket(ParticleOptions particle,AABB aabb,int count) implements S2CModPacket<RegistryFriendlyByteBuf> {

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CParticleAABBPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ParticleTypes.STREAM_CODEC,S2CParticleAABBPacket::particle,
                    Utils.AABB_STREAM_CODEC, S2CParticleAABBPacket::aabb,
                    ByteBufCodecs.INT,S2CParticleAABBPacket::count,
                    S2CParticleAABBPacket::new
            );


    public static final Type<S2CParticleAABBPacket> TYPE = ModPacket.type(S2CParticleAABBPacket.class);

    @Override
    public void handleClient() {
        Player player = ModClient.getClientPlayer();
        Level level = player.level();
        RandomSource random = player.getRandom();
        for (int i = 0; i < count;i++) {
            double x = aabb.minX + (aabb.maxX - aabb.minX) * random.nextDouble();
            double y = aabb.minY + (aabb.maxY - aabb.minY) * random.nextDouble();
            double z = aabb.minZ + (aabb.maxZ - aabb.minZ) * random.nextDouble();
            level.addParticle(particle,x,y,z,0,0,0);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
