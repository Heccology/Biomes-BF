package net.hecco.biomesbf.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.bountifulfares.registry.content.BFBlocks;
import net.hecco.nexuslib.datagen.NLRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class BBFRecipeProvider extends NLRecipeProvider {
    public BBFRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(BiomesBF.MOD_ID, output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        oneToOneConversionRecipe(recipeOutput, Items.PURPLE_DYE, BBFBlocks.PURPLE_POSIES.get(), "purple_dye", 1);
    }
}
