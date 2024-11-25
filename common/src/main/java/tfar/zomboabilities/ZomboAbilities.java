package tfar.zomboabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.zomboabilities.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class ZomboAbilities {

    public static final String MOD_ID = "zomboabilities";
    public static final String MOD_NAME = "ZomboAbilities";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final ResourceKey<DimensionType> DEATH_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,id("death"));
    public static final ResourceKey<Level> DEATH_DIM = ResourceKey.create(Registries.DIMENSION,id("death"));

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID,"death");
    }

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {


        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.

    }

    static void onDeath(LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            PlayerDuck playerDuck = PlayerDuck.of(player);
            int lives = playerDuck.getLives();
            playerDuck.loseLife();
            if (lives<=1) {
                MinecraftServer server = player.server;
                //ServerLevel level = server.getLevel(DEATH_DIM);
                player.setRespawnPosition(DEATH_DIM,new BlockPos(0,2,0),0,true,false);
                //player.teleportTo(level,0,2,0,player.getYRot(),player.getXRot());
                player.setGameMode(GameType.CREATIVE);
            }
        }
    }

    static void onClone(ServerPlayer oldPlayer,ServerPlayer newPlayer,boolean alive) {
        PlayerDuck.of(newPlayer).copyFrom(oldPlayer);
    }

    static void onRespawn(ServerPlayer player,boolean fromEnd) {
        if (!fromEnd) {
            player.displayClientMessage(getLivesInfo(player),false);
        }
    }



    public static Component getLivesInfo(ServerPlayer player) {
        int lives = PlayerDuck.of(player).getLives();
        if (lives >0) {
            if (lives == 1) {
                return Component.literal("1 life remaining");
            } else {
                return Component.literal(lives+" lives remaining");
            }
        } else {
            return Component.literal("No lives remaining");
        }
    }

}