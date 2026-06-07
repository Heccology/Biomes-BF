package net.hecco.biomesbf.registry.content;

import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.definition.block.FloweringSpruceLeavesBlock;
import net.hecco.biomesbf.definition.block.PurplePosiesBlock;
import net.hecco.biomesbf.definition.block.SaltgrassBlock;
import net.hecco.bountifulfares.definition.block.custom.FruitLeavesBlock;
import net.hecco.bountifulfares.registry.content.BFBlocks;
import net.hecco.bountifulfares.registry.misc.BFConfiguredFeatures;
import net.hecco.bountifulfares.registry.misc.BFSaplingGenerators;
import net.hecco.nexuslib.lib.blockFamilyCreator.MinMiningToolTier;
import net.hecco.nexuslib.lib.blockFamilyCreator.Mineables;
import net.hecco.nexuslib.lib.publicBlocks.PublicSaplingBlock;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

public class BBFBlocks {
    public static final ArrayList<Supplier<Block>> PICKAXE_MINEABLE = new ArrayList<>();
    public static final ArrayList<Supplier<Block>> AXE_MINEABLE = new ArrayList<>();
    public static final ArrayList<Supplier<Block>> SHOVEL_MINEABLE = new ArrayList<>();
    public static final ArrayList<Supplier<Block>> HOE_MINEABLE = new ArrayList<>();

    public static final TreeGrower GOLDEN_WALNUT_SAPLING_GENERATOR = new TreeGrower("golden_walnut", 0f,
            Optional.empty(),
            Optional.empty(),
            Optional.of(BBFConfiguredFeatures.GOLDEN_WALNUT_KEY),
            Optional.empty(),
            Optional.empty(),
            Optional.empty());

    public static final Supplier<Block> GOLDEN_WALNUT_LEAVES = registerBlock("golden_walnut_leaves", Mineables.HOE, () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> GOLDEN_WALNUT_SAPLING = registerBlock("golden_walnut_sapling", () -> new PublicSaplingBlock(GOLDEN_WALNUT_SAPLING_GENERATOR, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).randomTicks().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> POTTED_GOLDEN_WALNUT_SAPLING = registerBlockNoItem("potted_golden_walnut_sapling", () -> new FlowerPotBlock(GOLDEN_WALNUT_SAPLING.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

    public static final Supplier<Block> SALTGRASS = registerBlock("saltgrass", () -> new SaltgrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).replaceable().noCollission().instabreak().sound(SoundType.SWEET_BERRY_BUSH).offsetType(BlockBehaviour.OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> DRY_SALTGRASS = registerBlock("dry_saltgrass", () -> new SaltgrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).replaceable().noCollission().instabreak().sound(SoundType.SWEET_BERRY_BUSH).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> PURPLE_POSIES = registerBlock("purple_posies", () -> new PurplePosiesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> FLOWERING_SPRUCE_LEAVES = registerBlock("flowering_spruce_leaves", Mineables.HOE, () -> new FloweringSpruceLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));



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
