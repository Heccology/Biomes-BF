package net.hecco.biomesbf.registry.content;

import net.hecco.biomesbf.BiomesBF;
import net.hecco.bountifulfares.definition.block.custom.FruitLeavesBlock;
import net.hecco.bountifulfares.registry.content.BFBlocks;
import net.hecco.nexuslib.lib.blockFamilyCreator.MinMiningToolTier;
import net.hecco.nexuslib.lib.blockFamilyCreator.Mineables;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.function.Supplier;

public class BBFBlocks {
    public static final ArrayList<Supplier<Block>> PICKAXE_MINEABLE = new ArrayList<>();
    public static final ArrayList<Supplier<Block>> AXE_MINEABLE = new ArrayList<>();
    public static final ArrayList<Supplier<Block>> SHOVEL_MINEABLE = new ArrayList<>();
    public static final ArrayList<Supplier<Block>> HOE_MINEABLE = new ArrayList<>();

    public static final Supplier<Block> GOLDEN_WALNUT_LEAVES = registerBlock("golden_walnut_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY)));






    public static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        return NLServices.REGISTRY.registerBlock(BiomesBF.MOD_ID, id, block);
    }

    public static <T extends Block> Supplier<T> registerBlock(String id, Mineables mineable, Supplier<T> block) {
        var registeredBlock = NLServices.REGISTRY.registerBlock(BiomesBF.MOD_ID, id, block);
        switch (mineable) {
            case PICKAXE -> PICKAXE_MINEABLE.add((Supplier<Block>) registeredBlock);
            case AXE -> AXE_MINEABLE.add((Supplier<Block>) registeredBlock);
            case SHOVEL -> SHOVEL_MINEABLE.add((Supplier<Block>) registeredBlock);
            case HOE -> HOE_MINEABLE.add((Supplier<Block>) registeredBlock);
        }
        return registeredBlock;
    }

    public static <T extends Block> Supplier<T> registerBlock(String id, Mineables mineable, Supplier<T> block, Item.Properties itemProperties) {
        var registeredBlock = NLServices.REGISTRY.registerBlock(BiomesBF.MOD_ID, id, block, itemProperties);
        switch (mineable) {
            case PICKAXE -> PICKAXE_MINEABLE.add((Supplier<Block>) registeredBlock);
            case AXE -> AXE_MINEABLE.add((Supplier<Block>) registeredBlock);
            case SHOVEL -> SHOVEL_MINEABLE.add((Supplier<Block>) registeredBlock);
            case HOE -> HOE_MINEABLE.add((Supplier<Block>) registeredBlock);
        }
        return registeredBlock;
    }

    public static <T extends Block> Supplier<T> registerBlockNoItem(String id, Supplier<T> block) {
        return NLServices.REGISTRY.registerBlockNoItem(BiomesBF.MOD_ID, id, block);
    }

    public static <T extends Block> Supplier<T> registerBlockNoItem(String id, Mineables mineable, Supplier<T> block) {
        var registeredBlock = NLServices.REGISTRY.registerBlockNoItem(BiomesBF.MOD_ID, id, block);
        switch (mineable) {
            case PICKAXE -> PICKAXE_MINEABLE.add((Supplier<Block>) registeredBlock);
            case AXE -> AXE_MINEABLE.add((Supplier<Block>) registeredBlock);
            case SHOVEL -> SHOVEL_MINEABLE.add((Supplier<Block>) registeredBlock);
            case HOE -> HOE_MINEABLE.add((Supplier<Block>) registeredBlock);
        }
        return registeredBlock;
    }

    public static void register() {

    }
}
