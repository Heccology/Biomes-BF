package net.hecco.biomesbf.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.hecco.biomesbf.BiomesBF;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class BBFBiomeModifiers {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, BiomesBF.BIOMESBF_MOD_ID);

    public static Supplier<MapCodec<BBFAddFeaturesByConfigBiomeModifier>> ADD_FEATURES_BY_CONFIG = BIOME_MODIFIERS.register("add_features_by_config", () ->
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(BBFAddFeaturesByConfigBiomeModifier::biomes),
                    Codec.STRING.fieldOf("config_value").forGetter(BBFAddFeaturesByConfigBiomeModifier::config),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(BBFAddFeaturesByConfigBiomeModifier::features),
                    GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(BBFAddFeaturesByConfigBiomeModifier::step)
                ).apply(instance, BBFAddFeaturesByConfigBiomeModifier::new)
            ));

}
