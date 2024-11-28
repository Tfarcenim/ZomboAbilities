package tfar.zomboabilities;

import net.minecraft.world.entity.item.ItemEntity;

public interface ItemEntityDuck {


    void setFloatTime(int floatTime);

    static ItemEntityDuck of(ItemEntity entity) {
        return (ItemEntityDuck) entity;
    }

}
