package net.sydokiddo.odyssey.mixin.block_tweaks;

import net.sydokiddo.odyssey.block.ModBlocks;
import net.sydokiddo.odyssey.block.custom_blocks.PotionCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.core.cauldron.CauldronInteraction.EMPTY;
import static net.minecraft.core.cauldron.CauldronInteraction.addDefaultInteractions;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin {

    @Inject(at = @At("HEAD"), method = "use")
    private void onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        addDefaultInteractions(EMPTY);
        EMPTY.put(Items.POTION, (state1, world1, pos1, player1, hand1, stack) -> {
            if (!world1.isClientSide) {
                Potion potion = PotionUtils.getPotion(stack);
                Item item = stack.getItem();
                if (potion !=  Potions.WATER) {
                    PotionCauldronBlock block = ModBlocks.POTION_CAULDRON_STATE;
                    block.setPotion(potion);
                    player1.setItemInHand(hand1, ItemUtils.createFilledResult(stack, player1, new ItemStack(Items.GLASS_BOTTLE)));
                    player1.awardStat(Stats.USE_CAULDRON);
                    player1.awardStat(Stats.ITEM_USED.get(item));
                    world1.setBlockAndUpdate(pos1, block.defaultBlockState());
                } else {
                    player1.setItemInHand(hand1, ItemUtils.createFilledResult(stack, player1, new ItemStack(Items.GLASS_BOTTLE)));
                    player1.awardStat(Stats.USE_CAULDRON);
                    player1.awardStat(Stats.ITEM_USED.get(item));
                    world1.setBlockAndUpdate(pos1, Blocks.WATER_CAULDRON.defaultBlockState());
                }
                world1.playSound(null, pos1, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                world1.gameEvent(null, GameEvent.FLUID_PLACE, pos1);
            }

            return InteractionResult.sidedSuccess(world1.isClientSide);
        });
    }
}
