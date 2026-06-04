package net.hecco.biomesbf.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.nexuslib.datagen.NLBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class BBFLootTableProvider extends NLBlockLootTableProvider {
    public BBFLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(BiomesBF.BIOMESBF_MOD_ID, dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        add(BBFBlocks.GOLDEN_WALNUT_LEAVES.get(), createLeavesDrops(BBFBlocks.GOLDEN_WALNUT_LEAVES.get(), BBFBlocks.GOLDEN_WALNUT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(BBFBlocks.GOLDEN_WALNUT_SAPLING.get());
        add(BBFBlocks.POTTED_GOLDEN_WALNUT_SAPLING.get(), createPotFlowerItemTable(BBFBlocks.GOLDEN_WALNUT_SAPLING.get()));
    }
}
