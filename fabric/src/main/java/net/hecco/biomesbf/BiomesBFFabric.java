package net.hecco.biomesbf;

import net.fabricmc.api.ModInitializer;
import net.hecco.biomesbf.registry.content.BBFBiomes;

public class BiomesBFFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        BiomesBF.init();
        BBFBiomes.registerBiomePlacement();
    }
}
