package net.hecco.biomesbf.definition.worldgen.spring;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class SpringFeatureConfig implements FeatureConfiguration {
    protected final IntProvider size;
    public static final Codec<SpringFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
                    instance.group(
                            IntProvider.CODEC
                                    .fieldOf("size")
                                    .forGetter((placer) -> placer.size)
                            ).apply(instance, SpringFeatureConfig::new)

    );

    public SpringFeatureConfig(IntProvider size) {
        this.size = size;
    }
}
