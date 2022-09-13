package net.sydokiddo.odyssey.mixin.bone_meal_additions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

// Mixin to allow Sugar Cane to be fertilized using Bone Meal

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockBoneMealMixin implements BonemealableBlock
{
    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient)
    {
        int i = this.countSugarCaneAbove(world, pos);
        int j = this.countSugarCaneBelow(world, pos);
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
        int i = this.countSugarCaneAbove(world, pos);
        world.setBlockAndUpdate(pos.above(i + 1), Blocks.SUGAR_CANE.defaultBlockState());
    }

    private int countSugarCaneAbove(BlockGetter world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.above(i + 1)).is(Blocks.SUGAR_CANE); ++i)
        {
        }
        return i;
    }

    private int countSugarCaneBelow(BlockGetter world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.below(i + 1)).is(Blocks.SUGAR_CANE); ++i)
        {
        }
        return i;
    }
}