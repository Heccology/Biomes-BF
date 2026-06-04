package net.hecco.biomesbf.registry.content;

import net.hecco.biomesbf.BiomesBF;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class BBFConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_WALNUT_KEY = registerKey("golden_walnut");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(BiomesBF.MOD_ID, name));
    }
}
