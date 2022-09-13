package net.sydokiddo.odyssey.mixin.bone_meal_additions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Mixin to allow Lily Pads to be fertilized using Bone Meal

@Mixin(WaterlilyBlock.class)
public abstract class LilyPadBlockBoneMealMixin implements BonemealableBlock {

    @Shadow protected abstract boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos);

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state)
    {
        BlockState blockState = Blocks.LILY_PAD.defaultBlockState();

        stopGrowth:
        for(int i = 0; i < 24; ++i) {
            BlockPos growPos = pos;
            for (int j = 0; j < i / 16; ++j) {
                growPos = growPos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (world.getBlockState(growPos).isCollisionShapeFullBlock(world, growPos)) {
                    continue stopGrowth;
                }
            }
            if (canGrowTo(growPos,world)) {
                world.setBlockAndUpdate(growPos,blockState);
                state.tick(world, growPos, random);
            }

        }

    }

    private boolean canGrowTo(BlockPos pos, BlockGetter world) {
        return this.mayPlaceOn(world.getBlockState(pos.below()), world, pos.below()) && world.getBlockState(pos).isAir();
    }
}