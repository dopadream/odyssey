package net.sydokiddo.odyssey.mixin.block_tweaks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

// Mixin to allow Cobbled Deepslate generators to work at Y=8 or lower with a 12.5% chance, and a 100% chance of working below Y=0

@Mixin(FluidBlock.class)
public class MixinFluidBlock {

    Random random = new Random();

    @ModifyArg(method = "receiveNeighborFluids",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"),
            index = 1)
    protected BlockState deepslate$receiveNeighborFluids(BlockPos pos, BlockState state) {
        int y_level = pos.getY();
        // If Y value <= 0, 100% chance to generate Cobbled Deepslate
        // Else if y <= 8, (9 - y) * 12.5% chance to generate Cobbled Deepslate
        if (state.isOf(Blocks.COBBLESTONE) && (random.nextInt(1, 9) - y_level >= 0)) {
            state = Blocks.COBBLED_DEEPSLATE.getDefaultState();
        }
        return state;
    }

}