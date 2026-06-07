package net.hecco.biomesbf.definition.worldgen.spring;

import com.mojang.serialization.Codec;
import net.hecco.biomesbf.BiomesBF;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.minecraft.core.Direction.*;

public class SpringFeature extends Feature<SpringFeatureConfig> {
    public SpringFeature(Codec<SpringFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SpringFeatureConfig> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        SpringFeatureConfig config = context.config();
        BlockPos blockPos = context.origin();

        List<BlockPos> waterPoses = new ArrayList<>();
        int slices = 25;
        float[] noise = new float[slices];

        for (int i = 0; i < 25; i++) {
            noise[i] = 10.5f + (random.nextFloat() - 0.5f) * 10.0f;
        }
        float[] smoothed = new float[slices];
        for (int i = 0; i < slices; i++) {
            int prev = (i - 1 + slices) % slices;
            int next = (i + 1) % slices;
            smoothed[i] = (noise[prev] + noise[i] + noise[next]) / 3.0f;
        }

        int radius = 12;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
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

        for (BlockPos pos : waterPoses.stream().toList()) {
            for (int i = 0; i < 8; i++) {
                BlockPos movedPos = pos.below(i);
                BlockState state = level.getBlockState(movedPos);
                if (state.isSolidRender(level, movedPos)) {
                    if (movedPos.getY() < lowestPos && movedPos.getY() > 63) {
                        lowestPos = movedPos.getY();
                    }
                    break;
                }
            }
        }

        if (lowestPos == 999) return false;

        for (BlockPos pos : waterPoses) {
            for (int i = 1; i < 8; i++) {
                if (level.getBlockState(pos.atY(lowestPos + i)).is(Blocks.WATER)) {
                    return false;
                }
                if (i > 3 && !level.getBlockState(pos.atY(lowestPos + i)).canBeReplaced()) {
                    return false;
                }
            }
        }

        for (BlockPos pos : waterPoses.stream().toList()) {
            if (level.getBlockState(pos.atY(lowestPos)).isSolidRender(level, pos.atY(lowestPos))) {
                setBlock(level, pos.atY(lowestPos), Blocks.WATER.defaultBlockState());
                for (int i = 1; i < 10; i++) {
                    double berp = berp((i-1)/10f, 3, 5, 7);
                    for (double x = -berp; x <= berp; x++) {
                        for (double z = -berp; z <= berp; z++) {
                            BlockPos offset = pos.atY(lowestPos + i).offset((int) x, 0, (int) z);
                            if (Math.sqrt(x * x + z * z) > berp || level.getBlockState(offset).isAir()) continue;
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
                                    removeBlock(level, offset);
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
                    setBlock(level, pos.atY(lowestPos).relative(direction), Blocks.SANDSTONE.defaultBlockState());
                }
                if (!level.getBlockState(pos.atY(lowestPos-1).relative(direction)).is(BlockTags.FEATURES_CANNOT_REPLACE) && !level.getBlockState(pos.atY(lowestPos-1).relative(direction)).is(Blocks.WATER)) {
                    setBlock(level, pos.atY(lowestPos-1).relative(direction), Blocks.SANDSTONE.defaultBlockState());
                }
            }
        }


        List<BlockPos> floorpositions = new ArrayList<>();
        List<BlockPos> floorrandomPoses = new ArrayList<>();

        int floorRadius = 3;
        for (int x = -floorRadius; x <= floorRadius; x++) {
            for (int z = -floorRadius; z <= floorRadius; z++) {
                if (Math.sqrt(x * x + z * z) > floorRadius) continue;
                int xOffset = random.nextIntBetweenInclusive(-floorRadius, floorRadius);
                int zOffset = random.nextIntBetweenInclusive(-floorRadius, floorRadius);
                floorpositions.add(blockPos.offset(x - (radius/2) + xOffset, 1, z - (radius/2) + zOffset));
            }
        }

        for (int i = 0; i < 60; i++) {
            floorrandomPoses.add(floorpositions.get(random.nextInt(0, floorpositions.size())));
        }

        HolderSet<PlacedFeature> floorFeatures = config.floorFeatures;

        for (BlockPos pos : floorrandomPoses) {
            Optional<Holder<PlacedFeature>> feature = floorFeatures.getRandomElement(random);
            feature.ifPresent(placedFeatureHolder -> placedFeatureHolder.value().place(level, context.chunkGenerator(), random, pos));
        }




        List<BlockPos> innerpositions = new ArrayList<>();
        List<BlockPos> innerrandomPoses = new ArrayList<>();

        int innerRadius = 4;
        for (int x = -innerRadius; x <= innerRadius; x++) {
            for (int z = -innerRadius; z <= innerRadius; z++) {
                int xOffset = random.nextIntBetweenInclusive(-innerRadius, innerRadius);
                int zOffset = random.nextIntBetweenInclusive(-innerRadius, innerRadius);
                int offsetX = x - innerRadius + xOffset;
                int offsetZ = z - innerRadius + zOffset;
                if (Math.sqrt(x * x + z * z) > innerRadius) continue;
                innerpositions.add(blockPos.offset(offsetX, 1, offsetZ));
            }
        }

        if (!innerpositions.isEmpty()) {
            for (int i = 0; i < 40; i++) {
                innerrandomPoses.add(innerpositions.get(random.nextInt(0, innerpositions.size())));
            }
        }

        HolderSet<PlacedFeature> innerFeatures = config.innerVegetationFeatures;

        for (BlockPos pos : innerrandomPoses) {
            Optional<Holder<PlacedFeature>> feature = innerFeatures.getRandomElement(random);
            feature.ifPresent(placedFeatureHolder -> placedFeatureHolder.value().place(level, context.chunkGenerator(), random, pos));
        }




        List<BlockPos> outerpositions = new ArrayList<>();
        List<BlockPos> outerrandomPoses = new ArrayList<>();

        int outerRadius = 10;
        for (int x = -outerRadius; x <= outerRadius; x++) {
            for (int z = -outerRadius; z <= outerRadius; z++) {
                int xOffset = random.nextIntBetweenInclusive(-innerRadius, innerRadius);
                int zOffset = random.nextIntBetweenInclusive(-innerRadius, innerRadius);
                int offsetX = x - innerRadius + xOffset;
                int offsetZ = z - innerRadius + zOffset;
                if (Math.sqrt(x * x + z * z) > outerRadius) continue;
                if (Math.sqrt(x * x + z * z) < outerRadius - 2) continue;
                outerpositions.add(blockPos.offset(offsetX, 1, offsetZ));
            }
        }
        if (!outerpositions.isEmpty()) {
            for (int i = 0; i < 30; i++) {
                outerrandomPoses.add(outerpositions.get(random.nextInt(0, outerpositions.size())));
            }
        }

        HolderSet<PlacedFeature> outerFeatures = config.outerVegetationFeatures;

        for (BlockPos pos : outerrandomPoses) {
            Optional<Holder<PlacedFeature>> feature = outerFeatures.getRandomElement(random);
            feature.ifPresent(placedFeatureHolder -> placedFeatureHolder.value().place(level, context.chunkGenerator(), random, pos));
        }

        return true;
    }

    public static double berp(float delta, float start, float end, float easing) {
        return (end * Math.pow(Math.sin(Math.PI*delta), Math.max(easing / 10, 0))) + start;
    }

    private static void setBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (!level.ensureCanWrite(pos)) return;
        level.setBlock(pos, state, 2);
    }

    private static void removeBlock(WorldGenLevel level, BlockPos pos) {
        if (!level.ensureCanWrite(pos)) return;
        level.removeBlock(pos, false);
    }
}