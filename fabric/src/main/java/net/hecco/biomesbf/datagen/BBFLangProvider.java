package net.hecco.biomesbf.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.bountifulfares.BountifulFares;
import net.hecco.nexuslib.datagen.NLLanguageProvider;
import net.hecco.nexuslib.lib.util.NLUtility;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class BBFLangProvider extends NLLanguageProvider {

    public BBFLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(BountifulFares.MOD_ID, dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        generate(translationBuilder, "biomesbf.configuration.restart_warning", "Requires a game restart to apply changes");

        generate(translationBuilder, "biomesbf.configuration.title", "Biomes of Bountiful Fares Configuration");
        generate(translationBuilder, "biomesbf.configuration.common", "Gameplay");

        generate(translationBuilder, "biomesbf.configuration.generate_blooming_oasis", "Blooming Oases generate");
        generate(translationBuilder, "biomesbf.configuration.generate_walnut_forest", "Walnut Forests generate");
        generate(translationBuilder, "biomesbf.configuration.generate_golden_savanna", "Golden Savannas generate");
        generate(translationBuilder, "biomesbf.configuration.generate_coconut_beach", "Coconut Beaches generate");
        generate(translationBuilder, "biomesbf.configuration.generate_blooming_forest", "Blooming Forests generate");
        generate(translationBuilder, "biomesbf.configuration.generate_blooming_grove", "Blooming Groves generate");
        generate(translationBuilder, "biomesbf.configuration.generate_blooming_river", "Blooming Rivers generate");

        generate(translationBuilder, "itemgroup.biomesbf", "Biomes of Bountiful Fares");

        generate(translationBuilder, BBFBlocks.GOLDEN_WALNUT_LEAVES.get(), "Golden Walnut Leaves");
        generate(translationBuilder, BBFBlocks.GOLDEN_WALNUT_SAPLING.get(), "Golden Walnut Sapling");
        generate(translationBuilder, BBFBlocks.POTTED_GOLDEN_WALNUT_SAPLING.get(), "Golden Walnut Sapling");
    }

    public static Set<ResourceLocation> getBlockIdsInList(ArrayList<Supplier<Block>> list) {
        Set<ResourceLocation> a = new HashSet<>();

        for(Supplier<Block> block : list) {
            a.add(BuiltInRegistries.BLOCK.getKey(block.get()));
        }

        return a;
    }
}
