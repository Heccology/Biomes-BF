package net.hecco.biomesbf.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.bountifulfares.registry.tags.BFItemTags;
import net.hecco.nexuslib.datagen.NLBlockLootTableProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class BBFLootTableProvider extends NLBlockLootTableProvider {
    public BBFLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(BiomesBF.BIOMESBF_MOD_ID, dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        add(BBFBlocks.GOLDEN_WALNUT_LEAVES.get(), createLeavesDrops(BBFBlocks.GOLDEN_WALNUT_LEAVES.get(), BBFBlocks.GOLDEN_WALNUT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(BBFBlocks.GOLDEN_WALNUT_SAPLING.get());
        add(BBFBlocks.POTTED_GOLDEN_WALNUT_SAPLING.get(), createPotFlowerItemTable(BBFBlocks.GOLDEN_WALNUT_SAPLING.get()));
        add(BBFBlocks.SALTGRASS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(this.hasCShearsOrSilkTouch())
                        .add(this.applyExplosionDecay(BBFBlocks.SALTGRASS.get(), LootItem.lootTableItem(BBFBlocks.SALTGRASS.get())))));
    }

    public static LootItemCondition.Builder hasCShears() {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(BFItemTags.C_SHEARS));
    }

    public final LootItemCondition.Builder hasCShearsOrSilkTouch() {
        return hasCShears().or(this.hasSilkTouch());
    }

    public final LootItemCondition.Builder doesNotHaveCShearsOrSilkTouch() {
        return this.hasShearsOrSilkTouch().invert();
    }

    public LootTable.Builder createSilkTouchOrCShearsDispatchTable(Block block, LootPoolEntryContainer.Builder<?> builder) {
        return createSelfDropDispatchTable(block, this.hasCShearsOrSilkTouch(), builder);
    }
}
