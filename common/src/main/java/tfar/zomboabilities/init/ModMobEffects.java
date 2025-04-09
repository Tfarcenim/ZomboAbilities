package tfar.zomboabilities.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.mobeffect.FlightEffect;
import tfar.zomboabilities.mobeffect.IntangibilityEffect;
import tfar.zomboabilities.mobeffect.QuietStepMobEffect;

public class ModMobEffects {
    public static final Holder<MobEffect> FLOATING_TOUCH = register("floating_touch",new MobEffect(MobEffectCategory.BENEFICIAL,0xffffff){});
    public static final Holder<MobEffect> FLOATING_ITEMS = register("floating_items",new MobEffect(MobEffectCategory.BENEFICIAL,0xffffff){});
    public static final Holder<MobEffect> COPY_ABILITY = register("copy_ability",new MobEffect(MobEffectCategory.BENEFICIAL,0xffffff){});
    public static final Holder<MobEffect> QUIET_STEP = register("quiet_step",new QuietStepMobEffect(MobEffectCategory.BENEFICIAL,0xffffff));
    public static final Holder<MobEffect> FLIGHT = register("flight",new FlightEffect(MobEffectCategory.BENEFICIAL,0xffffff));
    public static final Holder<MobEffect> INTANGIBILITY = register("intangibility",new IntangibilityEffect(MobEffectCategory.BENEFICIAL,0xffffff));
    public static final Holder<MobEffect> WITHER_TOUCH = register("wither_touch", new MobEffect(MobEffectCategory.BENEFICIAL,0x222222){});
    public static final Holder<MobEffect> KNOCKBACK = register("knockback",new MobEffect(MobEffectCategory.BENEFICIAL,0xff0000){}.addAttributeModifier(
            Attributes.ATTACK_KNOCKBACK, ZomboAbilities.id("effect.knockback"), 0.25F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ));
    public static final Holder<MobEffect> HYPER_SPEED = register("hyper_speed",new MobEffect(MobEffectCategory.BENEFICIAL,0x0000ff){});

    static Holder.Reference<MobEffect> register(String path, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ZomboAbilities.id(path),effect);
    }

    public static void boot(){}

}
