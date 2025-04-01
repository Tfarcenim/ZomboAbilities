package tfar.zomboabilities.world;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Nullable;
import tfar.zomboabilities.ZomboAbilities;

public class ZomboAbilitiesLevelData extends SavedData {

    boolean firstLoad = true;

    public static ZomboAbilitiesLevelData getOrCreate(MinecraftServer server) {
        ZomboAbilitiesLevelData tankSavedData = get(server);
        if (tankSavedData != null) {
            return tankSavedData;
        }

        ServerLevel overworld = server.overworld();
        return overworld.getDataStorage()
                .computeIfAbsent(ZomboAbilitiesLevelData.factory(), ZomboAbilities.MOD_ID);
    }

    public boolean isFirstLoad() {
        return firstLoad;
    }

    public void markFirstLoad() {
        firstLoad = false;
        setDirty();
    }

    @Nullable
    public static ZomboAbilitiesLevelData get(MinecraftServer server) {
        ServerLevel overworld = server.overworld();
        return overworld.getDataStorage()
                .get(ZomboAbilitiesLevelData.factory(), ZomboAbilities.MOD_ID);
    }

    public static SavedData.Factory<ZomboAbilitiesLevelData> factory() {
        return new SavedData.Factory<>(ZomboAbilitiesLevelData::new, ZomboAbilitiesLevelData::loadStatic, null);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putBoolean("first_load",firstLoad);
        return tag;
    }

    protected void load(CompoundTag compoundTag, HolderLookup.Provider levelRegistry) {
        firstLoad = compoundTag.getBoolean("first_load");
    }

    public static ZomboAbilitiesLevelData loadStatic(CompoundTag compoundTag, HolderLookup.Provider registries) {
        ZomboAbilitiesLevelData tankSavedData = new ZomboAbilitiesLevelData();
        tankSavedData.load(compoundTag,registries);
        return tankSavedData;
    }

}
