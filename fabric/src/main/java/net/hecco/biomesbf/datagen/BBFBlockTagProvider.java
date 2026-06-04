package net.hecco.biomesbf.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.nexuslib.datagen.NLBlockTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class BBFBlockTagProvider extends NLBlockTagProvider {
    public BBFBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(BiomesBF.BIOMESBF_MOD_ID, output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.getOrCreateTagBuilder(BlockTags.LEAVES).add(BBFBlocks.GOLDEN_WALNUT_SAPLING.get());

        this.getOrCreateTagBuilder(BlockTags.SAPLINGS).add(BBFBlocks.GOLDEN_WALNUT_SAPLING.get());

        this.getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(BBFBlocks.POTTED_GOLDEN_WALNUT_SAPLING.get());

        for(Supplier<Block> block : BBFBlocks.PICKAXE_MINEABLE) {
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get());
        }

        for(Supplier<Block> block : BBFBlocks.AXE_MINEABLE) {
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(block.get());
        }

        for(Supplier<Block> block : BBFBlocks.SHOVEL_MINEABLE) {
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL).add(block.get());
        }

        for(Supplier<Block> block : BBFBlocks.HOE_MINEABLE) {
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE).add(block.get());
        }
    }
}
