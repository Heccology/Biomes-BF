package net.hecco.biomesbf.registry.misc;

import net.hecco.biomesbf.platform.Services;

import java.util.ArrayList;
import java.util.List;

public class BBFConfiguration {
    public static void register() {
        String COMMON_CATEGORY = Services.CONFIG.createCategory("common", false, false, new ArrayList<>(), new ArrayList<>(List.of()));
        Services.CONFIG.registerBoolConfigValue(COMMON_CATEGORY, "generateBloomingOasis", "generate_blooming_oasis", true, true);
        Services.CONFIG.registerBoolConfigValue(COMMON_CATEGORY, "generateWalnutForest", "generate_walnut_forest", true, true);
//        Services.CONFIG.registerBoolConfigValue(COMMON_CATEGORY, "generateGoldenSavanna", "generate_golden_savanna", true, true);
//        Services.CONFIG.registerBoolConfigValue(COMMON_CATEGORY, "generateCoconutBeach", "generate_coconut_beach", true, true);
//        Services.CONFIG.registerBoolConfigValue(COMMON_CATEGORY, "generateBloomingForest", "generate_blooming_forest", true, true);
//        Services.CONFIG.registerBoolConfigValue(COMMON_CATEGORY, "generateBloomingGrove", "generate_blooming_grove", true, true);
//        Services.CONFIG.registerBoolConfigValue(COMMON_CATEGORY, "generateBloomingRiver", "generate_blooming_river", true, true);
        Services.CONFIG.registerBoolConfigValue(COMMON_CATEGORY, "generateBeachSaltgrass", "generate_beach_saltgrass", true, true);
    }
}
