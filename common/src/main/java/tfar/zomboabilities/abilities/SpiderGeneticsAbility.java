package tfar.zomboabilities.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.init.ModMobEffects;
import tfar.zomboabilities.utils.AbilityUtils;
import tfar.zomboabilities.utils.DangerSense;

import java.util.List;

//Pressing R - Player will be able to spawn a spider web in there cross air
//Cooldown - 5 seconds (After 3 is spawned
//
//Holding C - Player will be able to Climb Walls
//
//Passives - Player has Night vision
//Spiders/Cave Spiders will not attack player
//
//Danger sense - When any hostile mob spots the player, near the players hotbar,
//   it will say Danger! When it says Danger in Yellow That means a Mob has spotted it, When it says Danger in Blue,
//   that Means a Warden, Wither, When it says Danger in Red, A unknown Player has entered a 100 Blocks within you,
//   to not get this danger sense from a specific player the player can do /Dangersafe [Username}, make this so this saves even after death.
public class SpiderGeneticsAbility extends Ability{
    @Override
    public void primary(ServerPlayer player) {
        BlockHitResult pick = (BlockHitResult) player.pick(player.blockInteractionRange(),1,false);
        BlockItem sandBlock = (BlockItem) Items.COBWEB;
        sandBlock.place(new BlockPlaceContext(player, InteractionHand.MAIN_HAND,sandBlock.getDefaultInstance(), pick));

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

    @Override
    public void tickAbility(ServerPlayer player) {
        super.tickAbility(player);
        AABB aabb = player.getBoundingBox().inflate(100);
        List<LivingEntity> mobs = player.serverLevel().getEntitiesOfClass(LivingEntity.class,aabb);
        DangerSense sense = null;
        for (LivingEntity entity : mobs) {
            sense = DangerSense.getSense(player,entity);
            if (sense != null) break;
        }
        if (sense != null) {
            player.displayClientMessage(Component.literal("Danger!").withStyle(sense.chatFormatting),true);
        }
        //this is never true on the server
      //  System.out.println(player.horizontalCollision);
     //   AbilityUtils.setDataAttachment(player, CommonDataAttachments.CLIMBING,player.horizontalCollision);
    }

    @Override
    public boolean isFriendly(Mob aggressor) {
        return aggressor instanceof Spider;
    }

    @Override
    public void onRemoved(ServerPlayer player) {
        super.onRemoved(player);
        AbilityUtils.defaultDataAttachment(player, CommonDataAttachments.CLIMBING);
    }
}
