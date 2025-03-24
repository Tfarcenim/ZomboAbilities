package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.init.ModItems;
//Pressing R - By pressing this, the player will gain a item in there inventory, it will be a heart of the sea,
// But the name of it will the Mob Capsule, This cost 5 Exp to Make, By right clicking a mob with the Mob capsule,
// the mob will basically be yours, like a Pokemon, The name of the mob capsule will turn into the name of the mob you have,
// when you summon the mob out, the mob will only not attack you, You can put the mob back in its capsule anytime,
// if the mob dies, the Mob Capsule from your inventory will just disappear, The mob capsule can not be used for 2 mobs at a time only 1.

public class MobAbsorbAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        player.addItem(ModItems.MOB_CAPSULE.getDefaultInstance());
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
