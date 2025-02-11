package tfar.zomboabilities.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.init.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ZomboAbilities.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.FORCE_FIELD);

        getVariantBuilder(ModBlocks.POINTED_ICE).forAllStates(state -> {
            DripstoneThickness thickness = state.getValue(PointedDripstoneBlock.THICKNESS);
            Direction vertical_direction = state.getValue(PointedDripstoneBlock.TIP_DIRECTION);

            ModelFile file = itemModels().withExistingParent("pointed_ice_"+vertical_direction.getName()+"_"+thickness.getSerializedName(),mcLoc("block/pointed_dripstone"))
                    .texture("cross",mcLoc("block/ice"));
            return ConfiguredModel.builder().modelFile(file).build();
        });

    }
}
