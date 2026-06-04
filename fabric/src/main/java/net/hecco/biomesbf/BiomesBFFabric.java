package net.hecco.biomesbf;

import net.fabricmc.api.ModInitializer;
import net.hecco.biomesbf.config.BBFFabricConfigValues;
import net.hecco.biomesbf.registry.content.BBFBiomes;
import net.hecco.biomesbf.registry.content.BBFCompostables;
import net.hecco.biomesbf.registry.misc.BBFConfiguration;
import net.hecco.bountifulfares.registry.util.BFRegistries;

public class BiomesBFFabric implements ModInitializer {

    public static BBFFabricConfigValues CONFIG = new BBFFabricConfigValues();

    @Override
    public void onInitialize() {
        BBFConfiguration.register();
        BiomesBFFabric.CONFIG = BBFFabricConfigValues.load();
        BiomesBF.init();
        BBFBiomes.registerBiomePlacement();
        BBFCompostables.registerModCompostables();
    }
}
