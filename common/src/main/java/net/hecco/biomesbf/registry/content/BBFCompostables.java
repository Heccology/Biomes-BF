package net.hecco.biomesbf.registry.content;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.hecco.biomesbf.BiomesBF;
import net.minecraft.world.level.ItemLike;

import static net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES;

public class BBFCompostables {
    public static Object2FloatMap<ItemLike> registerModCompostables() {
        Object2FloatMap<ItemLike> compostables = new Object2FloatOpenHashMap<>();
        compostables.put(BBFBlocks.GOLDEN_WALNUT_LEAVES.get().asItem(), 0.65f);
        compostables.put(BBFBlocks.GOLDEN_WALNUT_SAPLING.get().asItem(), 0.85f);
        COMPOSTABLES.putAll(compostables);
        return compostables;
    }
}
