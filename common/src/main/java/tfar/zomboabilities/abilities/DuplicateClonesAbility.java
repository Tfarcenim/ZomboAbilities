package tfar.zomboabilities.abilities;

import net.minecraft.server.level.ServerPlayer;

public class DuplicateClonesAbility extends Ability{

    //Duplicate Clones
//
//Pressing R - Doing this will Spawn Clones of the player, Each clone Will have a number depending on the order you spawned them in,
// Clone 1,2,3,4,etc, This requires 1 Exp per clone, You can give the Clones Items, if the clone dies then the items will drop from that clone.
// If you ask your clone to do something, the Ai will actually do it, like for example, “Clone protect me from any mob while I mine” Or “Clone Protect my base”
//
//Passives - Whenever your attacked or you hit something, your clones will automatically Fight that Player/Mob, If you want your clones to turn passive and not attack anything Press T.
//You can give you clones Weapons and armor to fight with
//You can only spawn up to 25 Clones at a time, 25+ clones cannot exist.

    @Override
    public void primary(ServerPlayer player) {

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
