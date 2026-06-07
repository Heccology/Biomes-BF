package net.hecco.biomesbf.definition.block;

import net.hecco.biomesbf.registry.content.BBFParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FloweringSpruceLeavesBlock extends LeavesBlock {
    public FloweringSpruceLeavesBlock(Properties properties) {
        super(properties);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);
        if (random.nextInt(8) == 0) {
            if (!isFaceFull(world.getBlockState(pos.below()).getCollisionShape(world, pos.below()), Direction.UP)) {
                ParticleUtils.spawnParticleBelow(world, pos, random, BBFParticles.SPRUCE_PETAL.get());
            }
        }
    }
}
