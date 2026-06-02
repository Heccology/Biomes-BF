package net.hecco.biomesbf.registry.content;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.CriterionBuilder;
import com.terraformersmc.biolith.api.biome.sub.RatioTargets;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.hecco.biomesbf.BiomesBF;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BBFBiomes {
    public static final ResourceKey<Biome> BLOOMING_OASIS = ResourceKey.create(Registries.BIOME, BiomesBF.id("blooming_oasis"));
    public static final ResourceKey<Biome> GOLDEN_SAVANNA = ResourceKey.create(Registries.BIOME, BiomesBF.id("golden_savanna"));
    public static final ResourceKey<Biome> WALNUT_FOREST = ResourceKey.create(Registries.BIOME, BiomesBF.id("walnut_forest"));
    public static final ResourceKey<Biome> COCONUT_BEACH = ResourceKey.create(Registries.BIOME, BiomesBF.id("coconut_beach"));
    public static final ResourceKey<Biome> BLOOMING_FOREST = ResourceKey.create(Registries.BIOME, BiomesBF.id("blooming_forest"));
    public static final ResourceKey<Biome> BLOOMING_GROVE = ResourceKey.create(Registries.BIOME, BiomesBF.id("blooming_grove"));
    public static final ResourceKey<Biome> BLOOMING_RIVER = ResourceKey.create(Registries.BIOME, BiomesBF.id("blooming_river"));

    public static void register(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
    }

    public static void registerBiomePlacement() {
        BiomePlacement.addSubOverworld(Biomes.DESERT, BLOOMING_OASIS, CriterionBuilder.ratioMax(RatioTargets.CENTER, 0.1f));
        SurfaceRules.RuleSource blooming_oasis = SurfaceRules.ifTrue(SurfaceRules.isBiome(BLOOMING_OASIS), SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceNoiseAbove(1.0), SurfaceRules.state(Blocks.DIAMOND_BLOCK.defaultBlockState()))));
        SurfaceGeneration.addOverworldSurfaceRules(
                BiomesBF.id("rules/overworld"),
                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(blooming_oasis)))
        );
        BiomePlacement.addOverworld(WALNUT_FOREST, new Climate.ParameterPoint(
                Climate.Parameter.span(0.2F, 0.9F),        // temperature
                Climate.Parameter.span(-0.3F, 0.5F),        // humidity
                Climate.Parameter.span(0.25F, 0.6F),        // continentalness
                Climate.Parameter.span(-0.5F, -0.05F),        // erosion
                Climate.Parameter.span(0.0F, 0.0F),         // depth <!>
                Climate.Parameter.span(-1F, 1F),     // weirdness
                0 //offset(?)
        ));
    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25, Double.MAX_VALUE);
    }
}