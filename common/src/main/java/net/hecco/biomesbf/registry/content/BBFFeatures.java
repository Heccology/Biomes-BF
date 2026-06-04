package net.hecco.biomesbf.registry.content;

import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.definition.worldgen.spring.SpringFeature;
import net.hecco.biomesbf.definition.worldgen.spring.SpringFeatureConfig;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.function.Supplier;

public class BBFFeatures {
        public static final Supplier<Feature<SpringFeatureConfig>> SPRING = register("spring", () -> new SpringFeature(SpringFeatureConfig.CODEC));

        @SuppressWarnings("unchecked")
        private static <C extends FeatureConfiguration, T extends Feature<C>> Supplier<T> register(String name, Supplier<T> feature) {
                return NLServices.REGISTRY.register(BiomesBF.MOD_ID, name, (Registry<T>) BuiltInRegistries.FEATURE, feature);
        }

        public static void register() {
        }
}