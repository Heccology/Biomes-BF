package net.hecco.biomesbf.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.nexuslib.datagen.NLBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class BBFLootTableProvider extends NLBlockLootTableProvider {
    public BBFLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(BiomesBF.BIOMESBF_MOD_ID, dataOutput, registryLookup);
    }

    @Override
    public void generate() {

    }
}
