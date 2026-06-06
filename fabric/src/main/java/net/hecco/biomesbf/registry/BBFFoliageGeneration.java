package net.hecco.biomesbf.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BBFFoliageGeneration {
    public static void generate() {
        if (Services.CONFIG.getBoolConfigValue("generateBeachSaltgrass")) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BEACH),
                    GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(BiomesBF.MOD_ID, "beach_saltgrass")));
        }
    }
}
