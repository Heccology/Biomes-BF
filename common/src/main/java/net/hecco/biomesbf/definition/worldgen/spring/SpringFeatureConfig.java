package net.hecco.biomesbf.definition.worldgen.spring;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class SpringFeatureConfig implements FeatureConfiguration {
    protected final IntProvider size;
    public static final Codec<SpringFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
                    instance.group(
                            IntProvider.CODEC
                                    .fieldOf("size")
                                    .forGetter((placer) -> placer.size),
                            PlacedFeature.LIST_CODEC.fieldOf("floor_features").forGetter((config) -> config.floorFeatures),
                            PlacedFeature.LIST_CODEC.fieldOf("inner_vegetation_features").forGetter((config) -> config.innerVegetationFeatures),
                            PlacedFeature.LIST_CODEC.fieldOf("outer_vegetation_features").forGetter((config) -> config.outerVegetationFeatures)
                            ).apply(instance, SpringFeatureConfig::new)

    );

    public final HolderSet<PlacedFeature> floorFeatures;
    public final HolderSet<PlacedFeature> innerVegetationFeatures;
    public final HolderSet<PlacedFeature> outerVegetationFeatures;

    public SpringFeatureConfig(IntProvider size, HolderSet<PlacedFeature> floorFeatures, HolderSet<PlacedFeature> innerVegetationFeatures, HolderSet<PlacedFeature> outerVegetationFeatures) {
        this.size = size;
        this.floorFeatures = floorFeatures;
        this.innerVegetationFeatures = innerVegetationFeatures;
        this.outerVegetationFeatures = outerVegetationFeatures;
    }
}
