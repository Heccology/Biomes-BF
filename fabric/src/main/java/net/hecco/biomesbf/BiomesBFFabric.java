package net.hecco.biomesbf;

import net.fabricmc.api.ModInitializer;

public class BiomesBFFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        BiomesBF.init();
    }
}
