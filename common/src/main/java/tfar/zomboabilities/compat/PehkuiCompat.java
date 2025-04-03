package tfar.zomboabilities.compat;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.ZomboAbilitiesConfig;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Map;

public class PehkuiCompat {

    public static final ResourceLocation MODIFIER_ID = ZomboAbilities.id("attribute_modifier");

    public static void shrink(LivingEntity living, boolean reset) {
        if (reset) {
            for (ScaleType type : ScaleRegistries.SCALE_TYPES.values()) {
                ScaleData data = type.getScaleData(living);
                Boolean persist = data.getPersistence();
                data.resetScale();
                data.setPersistence(persist);
            }

            AttributeMap attributes = living.getAttributes();
            for (Map.Entry<Holder<Attribute>, AttributeInstance> entry : attributes.attributes.entrySet()) {
                AttributeInstance instance = entry.getValue();
                instance.removeModifier(MODIFIER_ID);
            }

        } else {
            double newScale = 1;

            if (living instanceof Player player) {
                newScale = Math.max(.05,player.getFoodData().getFoodLevel() / 20d);
            }

            ScaleData scaleData = ScaleTypes.BASE.getScaleData(living);
            scaleData.setScaleTickDelay(40);
            scaleData.setPersistence(true);
            scaleData.setTargetScale((float) newScale);

       /*     if (living instanceof Player player) {
                //multiplying by -1 is 0
                double speedModifier = Server.BLOCK_BREAK_SPEED_SCALING.get().function.applyAsDouble(newScale);
                addAttributeMultSafely(player, Attributes.BLOCK_BREAK_SPEED, speedModifier);
            }*/

            double maxHealthModifier = Math.max(ZomboAbilitiesConfig.SERVER.minHealth.get(),
                    ZomboAbilitiesConfig.SERVER.healthScaling.get().function.applyAsDouble(newScale));
            addAttributeMultSafely(living, Attributes.MAX_HEALTH, maxHealthModifier);

         //   double attackDamageModifier = Server.ATTACK_DAMAGE_SCALING.get().function.applyAsDouble(newScale);
         //   addAttributeMultSafely(living, Attributes.ATTACK_DAMAGE, attackDamageModifier);

         //   double movementModifier = Server.MOVEMENT_SPEED_SCALING.get().function.applyAsDouble(newScale);
         //   addAttributeMultSafely(living, Attributes.MOVEMENT_SPEED, movementModifier);

         //   double fallDamageScaling = Server.FALL_DAMAGE_MULTIPLIER_SCALING.get().function.applyAsDouble(newScale);
         //   addAttributeMultSafely(living, Attributes.FALL_DAMAGE_MULTIPLIER, fallDamageScaling);

        //    double safeFallScaling = Server.SAFE_FALL_DISTANCE_SCALING.get().function.applyAsDouble(newScale);
         //   addAttributeMultSafely(living, Attributes.SAFE_FALL_DISTANCE, safeFallScaling);
        //    if (living instanceof ServerPlayer player) {
        //        ModCriteriaTriggers.REACH_SIZE.trigger(player, newScale);
        //    }
        }
        if (living.getHealth() > living.getHealth()) {
            living.setHealth(living.getMaxHealth());
        }
    }

    public static void addAttributeSafely(LivingEntity entity, Holder<Attribute> attribute, AttributeModifier modifier) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        if (attributeInstance != null) {
            AttributeModifier old = attributeInstance.getModifier(modifier.id());
            if (old != null) {
                attributeInstance.removeModifier(modifier.id());
            }
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    public static void addAttributeMultSafely(LivingEntity entity, Holder<Attribute> attribute, double value) {
        addAttributeSafely(entity, attribute, new AttributeModifier(MODIFIER_ID, value - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

}
