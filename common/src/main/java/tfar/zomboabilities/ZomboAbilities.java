package tfar.zomboabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.zomboabilities.abilities.CopyAbility;
import tfar.zomboabilities.abilities.EndermanGeneticsAbility;
import tfar.zomboabilities.abilities.GoldTouchAbility;
import tfar.zomboabilities.commands.ModCommands;
import tfar.zomboabilities.data.AbilityData;
import tfar.zomboabilities.entity.ClonePlayerEntity;
import tfar.zomboabilities.init.*;
import tfar.zomboabilities.platform.Services;
import tfar.zomboabilities.utils.AbilityUtils;
import tfar.zomboabilities.utils.LivesUtils;
import tfar.zomboabilities.utils.Utils;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class ZomboAbilities {

    public static final String MOD_ID = "zomboabilities";
    public static final String MOD_NAME = "ZomboAbilities";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final ResourceKey<DimensionType> DEATH_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,id("death"));
    public static final ResourceKey<Level> DEATH_DIM = ResourceKey.create(Registries.DIMENSION,id("death"));
    public static final boolean ENABLE_LOG = Services.PLATFORM.isDevelopmentEnvironment();

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID,path);
    }

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
        Services.PLATFORM.registerAll(ModBlocks.class, BuiltInRegistries.BLOCK, Block.class);
        Services.PLATFORM.registerAll(ModItems.class, BuiltInRegistries.ITEM,Item.class);
        Services.PLATFORM.registerAll(ModEntityTypes.class, BuiltInRegistries.ENTITY_TYPE,dirtyCast(EntityType.class));
        Services.PLATFORM.registerAll(ModRecipeSerializers.class, BuiltInRegistries.RECIPE_SERIALIZER,dirtyCast(RecipeSerializer.class));

        ModGameRules.init();
        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
    }

    public static void registerAttributes(BiConsumer<EntityType<? extends LivingEntity>,AttributeSupplier> consumer) {
        consumer.accept(ModEntityTypes.CLONE_PLAYER, ClonePlayerEntity.createAttributes().build());
        consumer.accept(ModEntityTypes.ILLUSION_PLAYER, ClonePlayerEntity.createAttributes().build());
    }

    static void onDeath(LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            int lives = LivesUtils.getLives(player);
            LivesUtils.loseLife(player);
            if (lives<=1) {
                MinecraftServer server = player.server;
                //ServerLevel level = server.getLevel(DEATH_DIM);
                player.setRespawnPosition(DEATH_DIM,new BlockPos(0,2,0),0,true,false);
                //player.teleportTo(level,0,2,0,player.getYRot(),player.getXRot());
                player.setGameMode(GameType.CREATIVE);
                Services.PLATFORM.setAData(player,new AbilityData());
            }
        }
    }

    public static ItemStack onItemPickedUp(Inventory inventory, ItemStack stack) {
        Player player = inventory.player;
        if (AbilityUtils.hasAbility(player,Abilities.GOLD_TOUCH)) {
            if (!stack.is(ModTags.Items.MIDAS_IMMUNE)) {
                ItemStack transform = GoldTouchAbility.MIDAS_TRANSFORM.getOrDefault(stack.getItem(),Items.GOLD_INGOT).getDefaultInstance().copyWithCount(stack.getCount());
                stack.setCount(0);
                return transform;
            }
            return stack;
        }
        return stack;
    }

    @SuppressWarnings("unchecked")
    static <T> Class<T> dirtyCast(Class<?> clazz) {
        return (Class<T>) clazz;
    }

    static void onClone(ServerPlayer oldPlayer,ServerPlayer newPlayer,boolean alive) {
        PlayerDuck.of(newPlayer).copyFrom(oldPlayer);
    }

    static void onRespawn(ServerPlayer player,boolean fromEnd) {
        if (!fromEnd) {
            player.displayClientMessage(getLivesInfo(player),false);
            ModCommands.updateAbility(player,null, AbilityUtils.getAbility(player).orElse(null));
        }
    }



    public static Component getLivesInfo(ServerPlayer player) {
        int lives = LivesUtils.getLives(player);
        if (lives >0) {
            if (lives == 1) {
                return Component.literal("1 life remaining");
            } else {
                return Component.literal(lives+" lives remaining");
            }
        } else {
            return Component.literal("No lives remaining");
        }
    }

    public static void saveBedItems(Player player) {
        if (player instanceof ServerPlayer) {
            Inventory inventory = player.getInventory();
            SavedInventory savedInventory = ((PlayerDuck) player).getSavedInventory();
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (player.getRandom().nextBoolean()) {
                    savedInventory.setItem(i, stack);
                    inventory.setItem(i, ItemStack.EMPTY);
                }
            }
        }
    }

    static void playerTick(ServerPlayer player) {
        PlayerDuck playerDuck = PlayerDuck.of(player);
        playerDuck.tickServer();
        if (!player.isRemoved() && player.hasEffect(ModMobEffects.COPY_ABILITY)) {
            if (playerDuck.getMobAbility() == CopyAbility.ZOMBIE || playerDuck.getMobAbility() == CopyAbility.DROWNED) {
                boolean flag = Utils.isSunBurnTick(player);
                if (flag) {
                    ItemStack itemstack = player.getItemBySlot(EquipmentSlot.HEAD);
                    if (!itemstack.isEmpty()) {
                        if (itemstack.isDamageableItem()) {
                            Item item = itemstack.getItem();
                            itemstack.setDamageValue(itemstack.getDamageValue() + player.getRandom().nextInt(2));
                            if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                                player.onEquippedItemBroken(item, EquipmentSlot.HEAD);
                                player.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                            }
                        }
                        flag = false;
                    }
                    if (flag) {
                        player.igniteForSeconds(8.0F);
                    }
                }
            } else if (playerDuck.getMobAbility() == CopyAbility.SPIDER) {
                boolean flag = Utils.isSunBurnTick(player);
                if (flag) {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,10,1));
                }
            }
        }
    }

    static void onEffectRemove(LivingEntity living, Holder<MobEffect> holder) {
        if (holder == ModMobEffects.COPY_ABILITY) {
            if (living instanceof ServerPlayer player) {
                PlayerDuck.of(player).setMobAbility(null);
                PlayerDuck.of(player).setCopiedAbility(null);
            }
        } else if (holder == ModMobEffects.QUIET_STEP) {
            living.setSilent(false);
        }
         else if (holder == ModMobEffects.FLIGHT) {
             if (living instanceof ServerPlayer player) {
                 player.getAbilities().mayfly = false;
                 player.getAbilities().flying = false;
                 player.getAbilities().setFlyingSpeed(.05f);
                 player.onUpdateAbilities();
             }
        }
    }

    static void onEffectExpire(LivingEntity living,@Nullable MobEffectInstance mobEffectInstance) {
        Holder<MobEffect> holder = mobEffectInstance.getEffect();
        if (holder == ModMobEffects.COPY_ABILITY) {
            if (living instanceof ServerPlayer player) {
                PlayerDuck.of(player).setMobAbility(null);
                PlayerDuck.of(player).setCopiedAbility(null);
            }
        } else if (holder == ModMobEffects.QUIET_STEP) {
            living.setSilent(false);
        }
        else if (holder == ModMobEffects.FLIGHT) {
            if (living instanceof ServerPlayer player) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.getAbilities().setFlyingSpeed(.05f);
                player.onUpdateAbilities();
            }
        }
    }

    static void interactMob(Player player, Entity entity){
        if (player.hasEffect(ModMobEffects.FLOATING_TOUCH) && entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.LEVITATION,200));
        }
    }

    static void onHit(LivingEntity target, DamageSource source) {
        Entity attacker = source.getEntity();
        if (attacker instanceof ServerPlayer player) {
            if (player.hasEffect(ModMobEffects.COPY_ABILITY)) {
                if (target instanceof ServerPlayer playerTarget) {
                    AbilityUtils.getAbility(playerTarget).ifPresentOrElse(
                            ability -> {
                                PlayerDuck.of(player).setCopiedAbility(ability);
                                player.displayClientMessage(Component.literal("ability "+ability.getName()+" copied"),false);
                            },
                            () -> player.displayClientMessage(Component.literal("No Ability Found"),false)
                    );
                } else {
                    EntityType<?> type = target.getType();
                    Consumer<ServerPlayer> consumer = CopyAbility.MAP.get(type);
                    if (consumer != null) {
                        PlayerDuck.of(player).setMobAbility(consumer);
                        consumer.accept(player);
                    } else {
                        player.displayClientMessage(Component.literal("No Ability Found"),false);
                    }
                }
            }
        }

        if (target instanceof ServerPlayer playerTarget) {
            PlayerDuck playerTargetDuck = PlayerDuck.of(playerTarget);
            if (playerTargetDuck.getMobAbility() == CopyAbility.ENDERMAN) {
                playerTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,0));
            }
            playerTarget.removeEffect(ModMobEffects.FLIGHT);
        }
    }

    static void onLightning(Entity entity) {
        if (entity instanceof ServerPlayer player) {
            PlayerDuck playerDuck = PlayerDuck.of(player);
            if (player.hasEffect(ModMobEffects.COPY_ABILITY) &&playerDuck.getMobAbility() == CopyAbility.CREEPER) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,400,2));
                player.addEffect(new MobEffectInstance(MobEffects.GLOWING,400,0));
            }
            if (AbilityUtils.hasAbility(player,Abilities.EXPLOSION)) {
                playerDuck.setExplosionImmunityTimer(200);
            }
        }
    }

    public static boolean onIncomingDamage(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof Player player) {
            boolean enderman = AbilityUtils.hasAbility(player,Abilities.ENDERMAN_GENETICS);
            if (enderman) {
                boolean isPotion = source.getDirectEntity() instanceof ThrownPotion;
                if (source.is(DamageTypeTags.IS_PROJECTILE) && !isPotion) {
                    for (int i = 0; i < 64; i++) {
                        if (EndermanGeneticsAbility.randomTeleport((ServerPlayer) player)) {
                            return true;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkInvulnerable(Entity entity, DamageSource source, boolean isInvulnerable) {
        if (isInvulnerable) return true;

        if (Services.PLATFORM.isInfinityActive(entity) && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return true;
        }

        if (entity instanceof Player player) {
            return AbilityUtils.hasAbility(player,Abilities.FIRE_MANIPULATION) && source.is(DamageTypeTags.IS_FIRE);
        }

        return false;
    }

    public static InteractionResult onRightClickItem(Level level, Player player, InteractionHand hand, ItemStack itemStack) {
        if (itemStack.getItem() instanceof FireworkRocketItem && player.hasEffect(ModMobEffects.FLIGHT)) {

            if (!level.isClientSide) {
                FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(level, itemStack, player);
                level.addFreshEntity(fireworkrocketentity);
                itemStack.consume(1, player);
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                player.getAbilities().setFlyingSpeed(player.getAbilities().getFlyingSpeed() +.05f);
                player.onUpdateAbilities();
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return null;
    }

    public static void locationChanged(ServerLevel level, LivingEntity entity) {
        Enchantment frostWalker = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.FROST_WALKER).get().value();
        EnchantedItemInUse enchantediteminuse = new EnchantedItemInUse(new ItemStack(Items.NETHERITE_BOOTS), EquipmentSlot.FEET, entity);
        frostWalker.runLocationChangedEffects(level,1,enchantediteminuse,entity);
    }
}