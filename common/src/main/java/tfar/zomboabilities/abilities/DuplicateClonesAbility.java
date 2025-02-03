package tfar.zomboabilities.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.phys.EntityHitResult;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.Utils;
import tfar.zomboabilities.entity.ClonePlayerEntity;
import tfar.zomboabilities.init.ModEntityTypes;

public class DuplicateClonesAbility extends Ability{

    //Duplicate Clones
//
//Pressing R - Doing this will Spawn Clones of the player, Each clone Will have a number depending on the order you spawned them in,
// Clone 1,2,3,4,etc, This requires 1 Exp per clone, You can give the Clones Items, if the clone dies then the items will drop from that clone.
// If you ask your clone to do something, the Ai will actually do it, like for example, "Clone protect me from any mob while I mine" Or "Clone Protect my base"
//
//Passives - Whenever your attacked or you hit something, your clones will automatically Fight that Player/Mob, If you want your clones to turn passive and not attack anything Press T.
//You can give you clones Weapons and armor to fight with
//You can only spawn up to 25 Clones at a time, 25+ clones cannot exist.

    public static final int MAX = 25;
    @Override
    public void primary(ServerPlayer player) {
        if (player.totalExperience >0) {
            PlayerDuck playerDuck = PlayerDuck.of(player);
            if (playerDuck.tooManyClones()) {
                player.displayClientMessage(Component.literal("Too many clones"),false);
                return;
            }
            playerDuck.incrementClone();
            ClonePlayerEntity clone = ModEntityTypes.CLONE_PLAYER.spawn(player.serverLevel(), player.blockPosition(), MobSpawnType.MOB_SUMMONED);
            if (clone != null) {
                clone.setClone(new ResolvableProfile(player.getGameProfile()));
                clone.setOwnerUUID(player.getUUID());
                clone.setCustomName(player.getDisplayName());
                player.giveExperiencePoints(-1);
            }
        }
    }

    @Override
    public void secondary(ServerPlayer player) {
        EntityHitResult result = Utils.pick(player,player.blockInteractionRange(),player.entityInteractionRange(),0);
        if (result != null) {
            Entity entity = result.getEntity();
            if (entity instanceof ClonePlayerEntity clonePlayerEntity && clonePlayerEntity.getOwner() == player) {
                clonePlayerEntity.setOrderedToSit(!clonePlayerEntity.isOrderedToSit());
                player.displayClientMessage(Component.literal("Clone set to stay: "+clonePlayerEntity.isOrderedToSit()),true);
            }
        }
    }

    @Override
    public void tertiary(ServerPlayer player) {

    }

    @Override
    public void quaternary(ServerPlayer player) {

    }
}
