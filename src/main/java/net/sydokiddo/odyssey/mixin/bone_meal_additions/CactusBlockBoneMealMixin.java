package net.sydokiddo.odyssey.mixin.bone_meal_additions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

// Mixin to allow Cacti to be fertilized using Bone Meal

@Mixin(CactusBlock.class)
public abstract class CactusBlockBoneMealMixin implements BonemealableBlock
{
    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient)
    {
        int i = this.countCactusAbove(world, pos);
        int j = this.countCactusBelow(world, pos);
        return i + j < 2 && world.getBlockState(pos.above(i + 1)).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state)
    {
        int i = this.countCactusAbove(world, pos);
        BlockPos growPos = pos.above(i + 1);
        world.setBlockAndUpdate(growPos, Blocks.CACTUS.defaultBlockState());
        state.tick(world, growPos, random);
    }

    private int countCactusAbove(BlockGetter world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.above(i + 1)).is(Blocks.CACTUS); ++i)
        {
        }
        return i;
    }

    private int countCactusBelow(BlockGetter world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.below(i + 1)).is(Blocks.CACTUS); ++i)
        {
        }
        return i;
    }
}