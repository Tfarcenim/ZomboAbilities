package tfar.zomboabilities.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tfar.zomboabilities.ZomboAbilities;

@Mixin(Inventory.class)
public class InventoryMixin {
    @ModifyVariable(method = "add(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), argsOnly = true)
    private ItemStack onItemAdded(ItemStack original) {
        return ZomboAbilities.onItemPickedUp((Inventory)(Object)this,original);
    }
}
