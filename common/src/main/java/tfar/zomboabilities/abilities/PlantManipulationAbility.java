package tfar.zomboabilities.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.item.Items;
import tfar.zomboabilities.utils.Utils;

//
//Plant Manipulation (You can be creative with this one)
//
//Pressing R - Player will be able to grow and Crop/plant near them, This is infinite.
//
//Pressing Y - This will spawn a vine in your inventory
//Passives - Players will have Regen 1 In the Day time,
//Eating Any kind of meat will poison you for 10 seconds and give you hunger effect for 20.
public class PlantManipulationAbility extends Ability {
    @Override
    public void primary(ServerPlayer player) {
        for (int i = 0; i < 30;i++) {
            BlockPos offset = RandomPos.generateRandomDirection(player.getRandom(), 2, 2);
            BlockPos pos = player.blockPosition().offset(offset);
            player.serverLevel().getBlockState(pos).randomTick(player.serverLevel(), pos, player.serverLevel().getRandom());
        }
    }

    @Override
    public void secondary(ServerPlayer player) {
        player.addItem(Items.VINE.getDefaultInstance());
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
        if (Utils.isSunBurnTick(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,0,false,false));
        }
    }
}
