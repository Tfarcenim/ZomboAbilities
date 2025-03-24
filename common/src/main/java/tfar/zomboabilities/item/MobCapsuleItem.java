package tfar.zomboabilities.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import tfar.zomboabilities.init.ModTags;
import tfar.zomboabilities.platform.Services;

import java.util.Objects;

public class MobCapsuleItem extends Item {

    private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");

    public MobCapsuleItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        CustomData data = stack.get(DataComponents.ENTITY_DATA);
        if (data == null || data.isEmpty()) return super.getName(stack);

        return super.getName(stack).copy().append(" (").append(getType(stack).getDescription()).append(")");
    }

    public static EntityType<?> getType(ItemStack stack) {
        CustomData customdata = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        return !customdata.isEmpty() ? customdata.read(ENTITY_TYPE_FIELD_CODEC).result().orElseThrow() : EntityType.PIG;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();

            if (!itemstack.has(DataComponents.ENTITY_DATA))return InteractionResult.FAIL;

            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);
            BlockPos blockpos1 = blockstate.getCollisionShape(level, blockpos).isEmpty() ? blockpos : blockpos.relative(direction);

            EntityType<?> entitytype = getType(itemstack);
            Entity spawn = entitytype.spawn(
                    (ServerLevel) level,
                    itemstack,
                    context.getPlayer(),
                    blockpos1,
                    MobSpawnType.SPAWN_EGG,
                    true,
                    !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
            if (spawn != null) {
                itemstack.remove(DataComponents.ENTITY_DATA);
                level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
            }

            return InteractionResult.CONSUME;
        }
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!stack.has(DataComponents.ENTITY_DATA)) {
            if (canCapture(interactionTarget)) {
                CompoundTag tag = new CompoundTag();
                interactionTarget.save(tag);
                tag.remove("Pos");
                stack.set(DataComponents.ENTITY_DATA,CustomData.of(tag));
                interactionTarget.discard();
            }
        }
        return InteractionResult.PASS;
    }

    public static boolean canCapture(Entity entity) {
        return !Services.PLATFORM.isMultipart(entity) && !entity.getType().is(ModTags.EntityTypes.CAPTURING_NOT_SUPPORTED)
                && !entity.isPassenger() && !(entity instanceof Player);
    }

}
