package tfar.zomboabilities.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.mobeffect.QuietStepMobEffect;

public class ModMobEffects {
    public static final Holder<MobEffect> FLOATING_TOUCH = register("floating_touch",new MobEffect(MobEffectCategory.BENEFICIAL,0xffffff){});
    public static final Holder<MobEffect> FLOATING_ITEMS = register("floating_items",new MobEffect(MobEffectCategory.BENEFICIAL,0xffffff){});
    public static final Holder<MobEffect> COPY_ABILITY = register("copy_ability",new MobEffect(MobEffectCategory.BENEFICIAL,0xffffff){});
    public static final Holder<MobEffect> QUIET_STEP = register("quiet_step",new QuietStepMobEffect(MobEffectCategory.BENEFICIAL,0xffffff));

    static Holder.Reference<MobEffect> register(String path, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ZomboAbilities.id(path),effect);
    }

    public static void boot(){}

}
