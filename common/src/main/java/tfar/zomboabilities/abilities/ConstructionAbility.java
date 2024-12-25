package tfar.zomboabilities.abilities;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class ConstructionAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        if (player.totalExperience >0) {
            Item item = BuiltInRegistries.ITEM.getRandom(player.getRandom()).get().value();
            player.addItem(item.getDefaultInstance());
            player.giveExperiencePoints(-1);
            applyCooldown(0, 60, player);
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
