package net.hecco.biomesbf.registry.content;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.CriterionBuilder;
import com.terraformersmc.biolith.api.biome.sub.RatioTargets;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.platform.Services;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BBFBiomes {
    public static final ResourceKey<Biome> BLOOMING_OASIS = ResourceKey.create(Registries.BIOME, BiomesBF.id("blooming_oasis"));
    public static final ResourceKey<Biome> WALNUT_FOREST = ResourceKey.create(Registries.BIOME, BiomesBF.id("walnut_forest"));
    public static final ResourceKey<Biome> BLOOMING_TAIGA = ResourceKey.create(Registries.BIOME, BiomesBF.id("blooming_taiga"));

    public static void register(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
    }

    public static void registerBiomePlacement() {
        BiomePlacement.addSubOverworld(Biomes.DESERT, BLOOMING_OASIS, CriterionBuilder.ratioMax(RatioTargets.CENTER, 0.1f)); //old, use for good oasis seed
        if (Services.CONFIG.getBoolConfigValue("generateBloomingOasis")) {
//            BiomePlacement.addSubOverworld(Biomes.DESERT, BLOOMING_OASIS, CriterionBuilder.ratioMax(RatioTargets.CENTER, 0.13f));
            SurfaceGeneration.addOverworldSurfaceRules(
                    BiomesBF.id("rules/overworld"),
                    SurfaceRules.ifTrue(SurfaceRules.isBiome(BLOOMING_OASIS), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SurfaceRules.state(Blocks.SANDSTONE.defaultBlockState())), SurfaceRules.state(Blocks.SAND.defaultBlockState()))),
                    SurfaceRules.ifTrue(SurfaceRules.isBiome(BLOOMING_OASIS), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.state(Blocks.SANDSTONE.defaultBlockState())))
            );
        }
        if (Services.CONFIG.getBoolConfigValue("generateBloomingTaiga")) {
            BiomePlacement.addSubOverworld(Biomes.TAIGA, BLOOMING_TAIGA, CriterionBuilder.ratioMax(RatioTargets.CENTER, 0.15f));
        }
        if (Services.CONFIG.getBoolConfigValue("generateWalnutForest")) {
            BiomePlacement.addOverworld(WALNUT_FOREST, new Climate.ParameterPoint(
                    Climate.Parameter.span(0.1F, 0.7F),        // temperature
                    Climate.Parameter.span(-0.2F, 0.2F),        // humidity
                    Climate.Parameter.span(0.25F, 0.6F),        // continentalness
                    Climate.Parameter.span(-0.5F, -0.05F),        // erosion
                    Climate.Parameter.span(0.0F, 0.0F),         // depth <!>
                    Climate.Parameter.span(-0.8F, -0.07F),     // weirdness
                    0 //offset(?)
            ));
        }
    }
}