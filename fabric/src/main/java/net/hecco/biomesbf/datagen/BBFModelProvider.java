package net.hecco.biomesbf.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.bountifulfares.registry.content.BFBlocks;
import net.hecco.nexuslib.datagen.NLModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.TexturedModel;

public class BBFModelProvider extends NLModelProvider {
    public BBFModelProvider(FabricDataOutput output) {
        super(BiomesBF.BIOMESBF_MOD_ID, output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialBlock(BBFBlocks.GOLDEN_WALNUT_LEAVES.get(), TexturedModel.LEAVES);
        blockModelGenerators.createPlant(BBFBlocks.GOLDEN_WALNUT_SAPLING.get(), BBFBlocks.POTTED_GOLDEN_WALNUT_SAPLING.get(), BlockModelGenerators.TintState.NOT_TINTED);

        blockModelGenerators.createCrossBlockWithDefaultItem(BBFBlocks.SALTGRASS.get(), BlockModelGenerators.TintState.NOT_TINTED);
        blockModelGenerators.createCrossBlockWithDefaultItem(BBFBlocks.DRY_SALTGRASS.get(), BlockModelGenerators.TintState.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {

    }
}
