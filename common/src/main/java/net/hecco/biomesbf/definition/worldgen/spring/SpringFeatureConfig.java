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
                            PlacedFeature.LIST_CODEC.fieldOf("vegetation_features").forGetter((config) -> config.vegetationFeatures)
                            ).apply(instance, SpringFeatureConfig::new)

    );

    public final HolderSet<PlacedFeature> vegetationFeatures;

    public SpringFeatureConfig(IntProvider size, HolderSet<PlacedFeature> vegetationFeatures) {
        this.size = size;
        this.vegetationFeatures = vegetationFeatures;
    }
}
