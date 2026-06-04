package net.hecco.biomesbf.definition.worldgen.spring;

import com.mojang.serialization.Codec;
import net.hecco.biomesbf.BiomesBF;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BlockTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.core.Direction.*;

public class SpringFeature extends Feature<SpringFeatureConfig> {
    public SpringFeature(Codec<SpringFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SpringFeatureConfig> context) {
        WorldGenLevel level = context.level();
        SpringFeatureConfig config = context.config();
        RandomSource random = context.random();
        BlockPos blockPos = context.origin();
        List<BlockPos> waterPoses = new ArrayList<>();
        for (int x = -6; x <= 6; x++) {
            for (int z = -6; z <= 6; z++) {
                waterPoses.add(blockPos.offset(x, 0, z));
            }
        }

        int lowestPos = 999;
        BlockState prevState = Blocks.AIR.defaultBlockState();

        for (BlockPos pos : waterPoses.stream().toList()) {
            for (int i = 0; i < 8; i++) {
                BlockState state = level.getBlockState(pos.below(i));
                if (state.isSolidRender(level, pos.below(i)) && pos.below(i).getY() < lowestPos) {
                    lowestPos = pos.below(i).getY();
                    prevState = level.getBlockState(pos.below(i));
                    break;
                }
            }
        }

        if (lowestPos == 999) return false;

        BiomesBF.LOGGER.info("lowest " + lowestPos);

        for (BlockPos pos : waterPoses.stream().toList()) {
            if (!level.getBlockState(pos.atY(lowestPos-1)).isAir()) {
                level.setBlock(pos.atY(lowestPos), Blocks.WATER.defaultBlockState(), 2);
                for (int i = 1; i < 8; i++) {
                    for (int x = i * Math.min(i - 5, 0) + 1; x <= i * Math.max(5 - i, 0) - 1; x++) {
                        for (int z = i * Math.min(i - 5, 0) + 1; z <= i * Math.max(5 - i, 0) - 1; z++) {
                            BlockPos offset = pos.atY(lowestPos + i).offset(x, 0, z);
                            BlockState offsetState = level.getBlockState(offset);
                            if (!offsetState.is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                                level.removeBlock(offset, false);
                            }
                        }
                    }
                }
                for (Direction direction : List.of(NORTH, EAST, SOUTH, WEST, DOWN)) {
                    if (!level.getBlockState(pos.atY(lowestPos).relative(direction)).is(BlockTags.FEATURES_CANNOT_REPLACE) && !level.getBlockState(pos.atY(lowestPos).relative(direction)).is(Blocks.WATER)) {
                        level.setBlock(pos.atY(lowestPos).relative(direction), prevState, 2);
                    }
                    if (!level.getBlockState(pos.atY(lowestPos-1).relative(direction)).is(BlockTags.FEATURES_CANNOT_REPLACE) && !level.getBlockState(pos.atY(lowestPos-1).relative(direction)).is(Blocks.WATER)) {
                        level.setBlock(pos.atY(lowestPos-1).relative(direction), prevState, 2);
                    }
                }
            }
        }

        return true;
    }
}