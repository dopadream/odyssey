package net.sydokiddo.odyssey.mixin.block_tweaks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import net.sydokiddo.odyssey.block.ModBlocks;
import net.sydokiddo.odyssey.block.custom_blocks.PotionCauldronBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.core.cauldron.CauldronInteraction.EMPTY;
import static net.minecraft.core.cauldron.CauldronInteraction.addDefaultInteractions;

// Makes it so that water can't be placed into a Cauldron in the Nether

// Steph here,
// I changed this so doing this consumes the water and plays an animation.
// Seeing the vapor appear out of thin air with no visual context is a bit odd.


@Mixin(AbstractCauldronBlock.class)
public class MixinAbstractCauldronBlock {
    @Inject(at=@At("HEAD"), method= "use", cancellable = true)
    private void setLevel(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.getItemInHand(hand).getItem().equals(Items.WATER_BUCKET) && world.dimensionType().ultraWarm()) {
            world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
            if (world instanceof ServerLevel) {
                ((ServerLevel) world).sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, 8, 0.2, 0.2, 0.2, 0);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET.asItem()));
                }
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
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