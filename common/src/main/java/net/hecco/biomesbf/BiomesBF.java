package net.hecco.biomesbf;

import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.biomesbf.registry.content.BBFItemGroups;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiomesBF {

    public static final String MOD_ID = "bountifulfares";
    public static final String BIOMESBF_MOD_ID = "biomesbf";
    public static final String MOD_NAME = "Biomes of Bountiful Fares";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void init() {
        BBFBlocks.register();
        BBFItemGroups.register();
    }
}