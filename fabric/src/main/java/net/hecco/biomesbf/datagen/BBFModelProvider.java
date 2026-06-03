package net.hecco.biomesbf.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.registry.content.BBFBlocks;
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
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {

    }
}
