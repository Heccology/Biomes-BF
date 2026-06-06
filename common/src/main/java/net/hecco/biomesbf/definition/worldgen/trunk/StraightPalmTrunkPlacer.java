package net.hecco.biomesbf.definition.worldgen.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.hecco.biomesbf.registry.misc.BBFTrunkPlacerTypes;
import net.hecco.bountifulfares.definition.block.custom.CoconutBlock;
import net.hecco.bountifulfares.definition.block.custom.PalmFrondParentBlock;
import net.hecco.bountifulfares.definition.block.custom.WallPalmFrondBlock;
import net.hecco.bountifulfares.registry.content.BFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class StraightPalmTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<StraightPalmTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> trunkPlacerParts(instance).apply(instance, StraightPalmTrunkPlacer::new));

    public StraightPalmTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return BBFTrunkPlacerTypes.STRAIGHT_PALM_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (!world.isStateAtPosition(startPos.offset(x, y, z), BlockBehaviour.BlockStateBase::canBeReplaced)) {
                        return ImmutableList.of();
                    }
                }
            }
        }

        int firstHeight = random.nextIntBetweenInclusive(2, 3);
        BlockPos crownPos = startPos;
        for (int i = 0; i < firstHeight; i++) {
            placeLog(world, replacer, random, startPos.above(i), config);
            crownPos = startPos.above(i);
        }
        BlockPos frondPos = crownPos;
        for (int i = 0; i < 1; i++) {
            BlockState crownState = BFBlocks.PALM_CROWN.get().defaultBlockState();
            replacer.accept(crownPos.above(i), crownState);
            frondPos = crownPos.above(i);
        }
        replacer.accept(frondPos.above(), BFBlocks.PALM_FROND.get().defaultBlockState().setValue(PalmFrondParentBlock.SIZE, random.nextIntBetweenInclusive(0, 2)));
        replacer.accept(frondPos.north(), BFBlocks.WALL_PALM_FROND.get().defaultBlockState().setValue(WallPalmFrondBlock.FACING, Direction.NORTH).setValue(PalmFrondParentBlock.SIZE, random.nextIntBetweenInclusive(0, 1)));
        replacer.accept(frondPos.east(), BFBlocks.WALL_PALM_FROND.get().defaultBlockState().setValue(WallPalmFrondBlock.FACING, Direction.EAST).setValue(PalmFrondParentBlock.SIZE, random.nextIntBetweenInclusive(0, 1)));
        replacer.accept(frondPos.south(), BFBlocks.WALL_PALM_FROND.get().defaultBlockState().setValue(WallPalmFrondBlock.FACING, Direction.SOUTH).setValue(PalmFrondParentBlock.SIZE, random.nextIntBetweenInclusive(0, 1)));
        replacer.accept(frondPos.west(), BFBlocks.WALL_PALM_FROND.get().defaultBlockState().setValue(WallPalmFrondBlock.FACING, Direction.WEST).setValue(PalmFrondParentBlock.SIZE, random.nextIntBetweenInclusive(0, 1)));
        return ImmutableList.of();
    }
}