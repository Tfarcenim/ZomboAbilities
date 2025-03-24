package tfar.zomboabilities.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import tfar.zomboabilities.init.ModTags;
import tfar.zomboabilities.utils.AbilityUtils;

import java.util.ArrayList;
import java.util.List;

//Life Giver
//
//Shifting + Right click - This Ability allows you to turn any block that you shift and right click into a random Mob,
// you can turn the mob back into a block by shifting and right clicking on the again.
//
//The only mobs that you cannot get through it are, Villagers, Zombie Villagers, Ender Dragon,
//
//You can turn Zombie Villagers into normal villagers by shifting and right clicking on them, it’ll do the same effects as if you did it with a golden apple.
//
//-
public class LiveGiverAbility extends Ability {
    @Override
    public void primary(ServerPlayer player) {
        HitResult result = player.pick(5,1,false);
        if (result instanceof BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = player.serverLevel().getBlockState(pos);
            if (state.getDestroySpeed(player.serverLevel(),pos)>=0) {
                List<EntityType<?>> possibilities = new ArrayList<>();
                for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
                    if (type.is(ModTags.EntityTypes.LIFE_GIVER_WHITELIST)) {
                        possibilities.add(type);
                    }
                }

                if (!possibilities.isEmpty()) {
                    EntityType<?> type = possibilities.get(player.getRandom().nextInt(possibilities.size()));
                    Entity spawn = type.spawn(player.serverLevel(), pos, MobSpawnType.EVENT);
                    if (spawn != null) {
                        AbilityUtils.setLiveGiverState(spawn,state);
                        player.serverLevel().removeBlock(pos, false);
                    }
                }
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
