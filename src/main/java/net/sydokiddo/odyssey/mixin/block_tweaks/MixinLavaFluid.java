package net.sydokiddo.odyssey.mixin.block_tweaks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Mixin(LavaFluid.class)
public class MixinLavaFluid {

// Mixin to allow Deepslate generators to work at Y=8 or lower with a 12.5% chance, and a 100% chance of working below Y=0

    Random random = new Random();

    @ModifyArg(method = "flow",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
            index = 1)
    protected BlockState deepslate$flow(BlockPos pos, BlockState state, int flag) {
        System.out.println(pos.toString());
        int y_level = pos.getY();
        // If Y value <= 0, 100% chance to generate Deepslate
        // Else if Y value <= 8, (9 - y) * 12.5% chance to generate Deepslate
        if (random.nextInt(1, 9) - y_level >= 0) {
            return Blocks.DEEPSLATE.getDefaultState();
        }
        return state;
    }

}