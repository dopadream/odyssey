package net.sydokiddo.odyssey.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Map;
import java.util.Objects;

import static net.minecraft.world.item.alchemy.PotionUtils.getPotion;

public class PotionCauldronInteraction {
    public static final Map<Item, CauldronInteraction> POTION_CAULDRON_BEHAVIOR = CauldronInteraction.newInteractionMap();


    public static void bootstrap() {

        POTION_CAULDRON_BEHAVIOR.put(Items.POTION, (state, world, pos, player, hand, stack) -> {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            PotionCauldronBlockEntity cauldron = (PotionCauldronBlockEntity)blockEntity;
            if (!Objects.requireNonNull(cauldron).hasPotion() || getPotion(stack) == cauldron.getPotion()) {
                if (state.getValue(LayeredCauldronBlock.LEVEL) != 3 && getPotion(stack) != Potions.WATER && cauldron.tryApplyPotion(getPotion(stack))) {
                    if (!world.isClientSide) {
                        player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                        player.awardStat(Stats.USE_CAULDRON);
                        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                        world.setBlock(pos, state.cycle(LayeredCauldronBlock.LEVEL), 3);
                        world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                        world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                    }
                    return InteractionResult.sidedSuccess(world.isClientSide);
                } else {
                    return InteractionResult.PASS;
                }
            } else {
                return CauldronInteraction.fillBucket(state, world, pos, player, hand, stack, new ItemStack(Items.GLASS_BOTTLE), stateIn -> true, SoundEvents.GENERIC_EXTINGUISH_FIRE);
            }
        });
        POTION_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            PotionCauldronBlockEntity cauldron = (PotionCauldronBlockEntity)blockEntity;
            Potion potion = Objects.requireNonNull(cauldron).getPotion();
            if (cauldron.hasPotion()) {
                if (!world.isClientSide) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, PotionUtils.setPotion(new ItemStack(Items.POTION), potion)));
                    player.awardStat(Stats.USE_CAULDRON);
                    player.awardStat(Stats.USE_CAULDRON);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    PotionCauldronBlock.lowerFillLevel(state, world, pos);
                    world.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                }
            }
            return InteractionResult.sidedSuccess(world.isClientSide);

        });

        CauldronInteraction.addDefaultInteractions(POTION_CAULDRON_BEHAVIOR);
    }

    public static boolean tryApplyingPotion(BlockState state, LevelAccessor worldIn, BlockPos pos, ItemStack stack) {
        BlockEntity blockEntity = worldIn.getBlockEntity(pos);
        int level = state.getValue(LayeredCauldronBlock.LEVEL);
        if (blockEntity instanceof PotionCauldronBlockEntity && level != 3) {
            if (((PotionCauldronBlockEntity)blockEntity).tryApplyPotion(getPotion(stack))) {
                worldIn.setBlock(pos, state.setValue(PotionCauldronBlock.LEVEL, level + 1), 3);
                worldIn.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}