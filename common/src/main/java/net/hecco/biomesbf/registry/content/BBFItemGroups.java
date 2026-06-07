package net.hecco.biomesbf.registry.content;

import net.hecco.biomesbf.BiomesBF;
import net.hecco.bountifulfares.registry.content.BFBlocks;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class BBFItemGroups {
    public static final Supplier<CreativeModeTab> ITEM_GROUP = NLServices.REGISTRY.register(
            BiomesBF.BIOMESBF_MOD_ID, "biomes_bf", BuiltInRegistries.CREATIVE_MODE_TAB, () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("itemgroup.biomesbf"))
                    .icon(() -> new ItemStack(BFBlocks.WILD_PASSION_FRUIT_VINE.get()))
                    .displayItems((displayParameters, entries) -> {
                        entries.accept(BBFBlocks.GOLDEN_WALNUT_LEAVES.get());
                        entries.accept(BBFBlocks.GOLDEN_WALNUT_SAPLING.get());
                        entries.accept(BBFBlocks.SALTGRASS.get());
                        entries.accept(BBFBlocks.DRY_SALTGRASS.get());
                        entries.accept(BBFBlocks.PURPLE_POSIES.get());
                        entries.accept(BBFBlocks.FLOWERING_SPRUCE_LEAVES.get());
                    }).build());

    public static void register() {
    }
}
