package net.sydokiddo.odyssey.mixin.block_tweaks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Allows for Anvils to be repaired by shift right-clicking on them with an Iron Block

@Mixin(AnvilBlock.class)
public class MixinAnvilBlock {

    @Inject(at=@At("HEAD"), method="use",
            cancellable=true)
    public void onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> ci) {
        if (!world.isClientSide) {
            ItemStack held = player.getItemInHand(hand);
            if (held.getItem() == Item.byBlock(Blocks.IRON_BLOCK)) {
                BlockState bs = world.getBlockState(pos);
                boolean consume = false;
                if (bs.getBlock() == Blocks.DAMAGED_ANVIL) {
                    world.setBlockAndUpdate(pos, Blocks.CHIPPED_ANVIL.defaultBlockState().setValue(AnvilBlock.FACING, bs.getValue(AnvilBlock.FACING)));
                    consume = true;
                } else if (bs.getBlock() == Blocks.CHIPPED_ANVIL) {
                    world.setBlockAndUpdate(pos, Blocks.ANVIL.defaultBlockState().setValue(AnvilBlock.FACING, bs.getValue(AnvilBlock.FACING)));
                    consume = true;
                }
                if (consume) {
                    world.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1, 1);
                    player.gameEvent(GameEvent.BLOCK_PLACE);
                    if (!player.getAbilities().instabuild) {
                        held.shrink(1);
                    }
                    ci.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }
    }

}