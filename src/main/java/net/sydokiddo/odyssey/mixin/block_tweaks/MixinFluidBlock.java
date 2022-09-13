package net.sydokiddo.odyssey.mixin.block_tweaks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

// Mixin to allow Cobbled Deepslate generators to work at Y=8 or lower with a 12.5% chance, and a 100% chance of working below Y=0

@Mixin(LiquidBlock.class)
public class MixinFluidBlock {

    Random random = new Random();

    @ModifyArg(method = "shouldSpreadLiquid",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"),
            index = 1)
    protected BlockState deepslate$receiveNeighborFluids(BlockPos pos, BlockState state) {
        int y_level = pos.getY();
        // If Y value <= 0, 100% chance to generate Cobbled Deepslate
        // Else if y <= 8, (9 - y) * 12.5% chance to generate Cobbled Deepslate
        if (state.is(Blocks.COBBLESTONE) && (random.nextInt(1, 9) - y_level >= 0)) {
            state = Blocks.COBBLED_DEEPSLATE.defaultBlockState();
        }
        return state;
    }

}