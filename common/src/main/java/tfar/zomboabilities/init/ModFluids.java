package tfar.zomboabilities.init;

import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.platform.Services;

public class ModFluids {

    private static final ResourceLocation UNDERWATER_LOCATION = ResourceLocation.withDefaultNamespace("textures/misc/underwater.png");
    private static final ResourceLocation WATER_STILL = ResourceLocation.withDefaultNamespace("block/water_still");
    private static final ResourceLocation WATER_FLOW = ResourceLocation.withDefaultNamespace("block/water_flow");
    private static final ResourceLocation WATER_OVERLAY = ResourceLocation.withDefaultNamespace("block/water_overlay");

    public static final SimpleArchitecturyFluidAttributes ATTR = SimpleArchitecturyFluidAttributes.of(() -> ModFluids.FLOWING_ACID,() -> ModFluids.ACID)
            .overlayTexture(WATER_OVERLAY).sourceTexture(WATER_STILL).flowingTexture(WATER_FLOW);
    public static final FlowingFluid FLOWING_ACID = register("flowing_acid", Services.PLATFORM.flowingAcidFluid(ATTR));
    public static final FlowingFluid ACID = register("acid", Services.PLATFORM.sourceAcidFluid(ATTR));

    private static <T extends Fluid> T register(String key, T fluid) {
        for (FluidState fluidstate : fluid.getStateDefinition().getPossibleStates()) {
            Fluid.FLUID_STATE_REGISTRY.add(fluidstate);
        }
        return Registry.register(BuiltInRegistries.FLUID, ZomboAbilities.id(key), fluid);
    }

    public static void init() {
    }
}
