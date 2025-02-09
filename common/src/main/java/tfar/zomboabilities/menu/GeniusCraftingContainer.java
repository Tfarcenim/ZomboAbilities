package tfar.zomboabilities.menu;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;

import java.util.ArrayList;
import java.util.List;

public class GeniusCraftingContainer extends TransientCraftingContainer {

    public static final GeniusCraftingInput EMPTY = new GeniusCraftingInput(0, 0, List.of());

    public GeniusCraftingContainer(AbstractContainerMenu menu, int width, int height, NonNullList<ItemStack> items) {
        super(menu, width, height, items);
    }

    public GeniusCraftingContainer(AbstractContainerMenu menu, int width, int height) {
        super(menu, width, height);
    }

    @Override
    public GeniusCraftingInput asCraftInput() {
        return GeniusCraftingInput.ofPositionedG(this.getWidth(), this.getHeight(), this.getItems()).input();
    }

    public static class GeniusCraftingInput extends CraftingInput {

        public GeniusCraftingInput(int width, int height, List<ItemStack> item) {
            super(width, height, item);
        }


        public static Positioned ofPositionedG(int width, int height, List<ItemStack> items) {
            if (width != 0 && height != 0) {
                int i = width - 1;
                int j = 0;
                int k = height - 1;
                int l = 0;

                for (int i1 = 0; i1 < height; i1++) {
                    boolean flag = true;

                    for (int j1 = 0; j1 < width; j1++) {
                        ItemStack itemstack = items.get(j1 + i1 * width);
                        if (!itemstack.isEmpty()) {
                            i = Math.min(i, j1);
                            j = Math.max(j, j1);
                            flag = false;
                        }
                    }

                    if (!flag) {
                        k = Math.min(k, i1);
                        l = Math.max(l, i1);
                    }
                }

                int i2 = j - i + 1;
                int j2 = l - k + 1;
                if (i2 <= 0 || j2 <= 0) {
                    return Positioned.EMPTY;
                } else if (i2 == width && j2 == height) {
                    return new Positioned(new GeniusCraftingInput(width, height, items), i, k);
                } else {
                    List<ItemStack> list = new ArrayList<>(i2 * j2);

                    for (int k2 = 0; k2 < j2; k2++) {
                        for (int k1 = 0; k1 < i2; k1++) {
                            int l1 = k1 + i + (k2 + k) * width;
                            list.add(items.get(l1));
                        }
                    }

                    return new Positioned(new GeniusCraftingInput(i2, j2, list), i, k);
                }
            } else {
                return Positioned.EMPTY;
            }
        }

        public record Positioned(GeniusCraftingInput input, int left, int top) {
            public static final Positioned EMPTY = new Positioned(GeniusCraftingContainer.EMPTY, 0, 0);
        }
    }

}
