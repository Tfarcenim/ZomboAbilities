package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.component.ResolvableProfile;
import tfar.zomboabilities.entity.IllusionPlayerEntity;
import tfar.zomboabilities.init.ModEntityTypes;

////Pressing R - By pressing this you character will split into 10 clones of themselves, you will have to guess with one is real,
// if you hit a fake one they will just poof away, a similar mod does this with a Totem.
////Cooldown - 20 Seconds
////
////Pressing Y - By doing /Illusionset [Mob Name} The Player can summon a Fake Mod by Pressing Y, This mob will Attack Players but do 0 Damage to them, it its a creeper the simply just blow up without causing any destruction or damage.The mobs wont attack you obviously
////Cooldown - 15 Seconds
////
////Pressing T - By Pressing this while holding a block, it will create a fake version of that block/item,the block wont be craftable and if placed then broke it will simply not drop anything.Or if its a Item that you can eat, it will be eatable put wont give you the hunger/effect. If its a Totem, it will simply not work (Which would be funny for scamming people Lol)
////
public class IllusionCreationAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        for (int i = 0;i < 10;i++) {
            IllusionPlayerEntity clone = ModEntityTypes.ILLUSION_PLAYER.spawn(player.serverLevel(), player.blockPosition(), MobSpawnType.EVENT);
            clone.setClone(new ResolvableProfile(player.getGameProfile()));

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
