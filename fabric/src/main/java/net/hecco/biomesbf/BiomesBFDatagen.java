package net.hecco.biomesbf;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.hecco.biomesbf.datagen.*;

public class BiomesBFDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(BBFLangProvider::new);
        pack.addProvider(BBFLootTableProvider::new);
        pack.addProvider(BBFModelProvider::new);
        pack.addProvider(BBFBlockTagProvider::new);
        pack.addProvider(BBFRecipeProvider::new);
    }
}
