package net.hecco.biomesbf.definition.worldgen.spring;

import com.mojang.serialization.Codec;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.nexuslib.lib.util.NLMath;
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
        RandomSource random = context.random();
        BlockPos blockPos = context.origin();

        List<BlockPos> waterPoses = new ArrayList<>();
        int slices = 25;
        float[] noise = new float[slices];

        for (int i = 0; i < 25; i++) {
            noise[i] = 10.5f + (random.nextFloat() - 0.5f) * 20.0f;
        }
        float[] smoothed = new float[slices];
        for (int i = 0; i < slices; i++) {
            int prev = (i - 1 + slices) % slices;
            int next = (i + 1) % slices;
            smoothed[i] = (noise[prev] + noise[i] + noise[next]) / 3.0f;
        }

        for (int x = -14; x <= 14; x++) {
            for (int z = -14; z <= 14; z++) {
                float dist = (float) Math.sqrt((x * x) + (z * z));
                if (dist < 0.5f) {
                    waterPoses.add(blockPos.offset(x, 0, z));
                    continue;
                }
                if (dist <= smoothed[(int)(((Math.atan2(z, x) + (float) Math.PI) / (2 * (float) Math.PI)) * slices) % slices]) {
                    waterPoses.add(blockPos.offset(x, 0, z));
                }
            }
        }

        int lowestPos = 999;
        BlockState prevState = Blocks.AIR.defaultBlockState();

        for (BlockPos pos : waterPoses.stream().toList()) {
            for (int i = 0; i < 8; i++) {
                BlockPos movedPos = pos.below(i);
                BlockState state = level.getBlockState(movedPos);
                if (state.isSolidRender(level, movedPos)) {
                    if (movedPos.getY() < lowestPos && movedPos.getY() > 63) {
                        lowestPos = movedPos.getY();
                        prevState = level.getBlockState(movedPos);
                    }
                    break;
                }
            }
        }

        if (lowestPos == 999) return false;

        for (BlockPos pos : waterPoses) {
            for (int i = 1; i < 7; i++) {
                if (level.getBlockState(pos.atY(lowestPos + i)).is(Blocks.WATER) || (i > 5 && !level.getBlockState(pos.atY(lowestPos + i)).isAir())) {
                    return false;
                }
            }
        }

        for (BlockPos pos : waterPoses.stream().toList()) {
            if (level.getBlockState(pos.atY(lowestPos)).isSolidRender(level, pos.atY(lowestPos))) {
                level.setBlock(pos.atY(lowestPos), Blocks.WATER.defaultBlockState(), 2);
                for (int i = 1; i < 10; i++) {
                    double berp = berp((i-1)/10f, 3, 5, 7);
                    for (double x = -berp; x <= berp; x++) {
                        for (double z = -berp; z <= berp; z++) {
                            if (Math.sqrt(x * x + z * z) > berp) continue;
                            BlockPos offset = pos.atY(lowestPos + i).offset((int) x, 0, (int) z);
                            BlockState offsetState = level.getBlockState(offset);
                            if (!offsetState.is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                                boolean waterSupporter = false;
                                for (Direction direction : List.of(NORTH, EAST, SOUTH, WEST, UP)) {
                                    if (level.getBlockState(offset.relative(direction)).is(Blocks.WATER)) {
                                        waterSupporter = true;
                                        break;
                                    }
                                }
                                if (!waterSupporter) {
                                    level.removeBlock(offset, false);
                                }
                            }
                        }
                    }
                }
            } else {
                waterPoses.remove(pos);
            }
        }
        for (BlockPos pos : waterPoses) {
            for (Direction direction : List.of(NORTH, EAST, SOUTH, WEST, DOWN)) {
                if (!level.getBlockState(pos.atY(lowestPos).relative(direction)).is(BlockTags.FEATURES_CANNOT_REPLACE) && !level.getBlockState(pos.atY(lowestPos).relative(direction)).is(Blocks.WATER)) {
                    level.setBlock(pos.atY(lowestPos).relative(direction), prevState, 2);
                }
                if (!level.getBlockState(pos.atY(lowestPos-1).relative(direction)).is(BlockTags.FEATURES_CANNOT_REPLACE) && !level.getBlockState(pos.atY(lowestPos-1).relative(direction)).is(Blocks.WATER)) {
                    level.setBlock(pos.atY(lowestPos-1).relative(direction), prevState, 2);
                }
            }
        }

        return true;
    }

    public static double berp(float delta, float start, float end, float easing) {
        return (end * Math.pow(Math.sin(Math.PI*delta), Math.max(easing / 10, 0))) + start;
    }
}