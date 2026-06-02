package net.hecco.biomesbf.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.nexuslib.datagen.NLLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class BBFLangProvider extends NLLanguageProvider {

    public BBFLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(BiomesBF.BIOMESBF_MOD_ID, dataOutput, registryLookup);
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
    }
}
