package net.hecco.biomesbf.registry.misc;

import com.mojang.serialization.MapCodec;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.definition.worldgen.trunk.StraightPalmTrunkPlacer;
import net.hecco.biomesbf.mixin.TrunkPlacerInvoker;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.function.Supplier;

public class BBFTrunkPlacerTypes {
    public static final Supplier<TrunkPlacerType<?>> STRAIGHT_PALM_TRUNK_PLACER = registerTrunkPlacer("straight_palm_trunk_placer", StraightPalmTrunkPlacer.CODEC);

    private static <P extends TrunkPlacer> Supplier<TrunkPlacerType<?>> registerTrunkPlacer(String id, MapCodec<P> codec) {
        return NLServices.REGISTRY.register(BiomesBF.MOD_ID, id, BuiltInRegistries.TRUNK_PLACER_TYPE, () -> TrunkPlacerInvoker.register(codec));
    }
    public static void register() {
    }

}