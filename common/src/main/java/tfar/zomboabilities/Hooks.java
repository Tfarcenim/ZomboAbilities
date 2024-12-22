package tfar.zomboabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tfar.zomboabilities.ducks.AbstractFurnaceBlockEntityDuck;

public class Hooks {
    public static void onTick(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity) {
        AbstractFurnaceBlockEntityDuck duck = (AbstractFurnaceBlockEntityDuck) blockEntity;
        blockEntity.litTime = Math.max(duck.getLaserTimer(),blockEntity.litTime);

        if (duck.getLaserTimer() > 0) {
            duck.setLaserTimer(duck.getLaserTimer() - 1);
        }
    }
}
