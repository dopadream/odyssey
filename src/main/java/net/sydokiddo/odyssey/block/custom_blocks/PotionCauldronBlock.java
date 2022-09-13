package net.sydokiddo.odyssey.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.sydokiddo.odyssey.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.BlockView;
import java.util.Collection;
import java.util.Map;

public class PotionCauldronBlock extends LayeredCauldronBlock implements EntityBlock {
    private PotionCauldronBlockEntity blockEntity;
    public Potion potion;

    public PotionCauldronBlock(BlockBehaviour.Properties settings, Map<Item, CauldronInteraction> map) {
        super(settings, predicate -> predicate == Biome.Precipitation.NONE, map);
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
    }

    public Potion getPotion() {
        return this.potion;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(Blocks.CAULDRON);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> blockEntityType, BlockEntityType<E> blockEntityType2, BlockEntityTicker<? super E> blockEntityTicker) {
        return blockEntityType2 == blockEntityType ? (BlockEntityTicker<A>) blockEntityTicker : null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
                return PotionCauldronBlock.createTickerHelper(blockEntityType, ModBlocks.POTION_CAULDRON_ENTITY, PotionCauldronBlockEntity::particleTick);

        }
        return null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        this.blockEntity = new PotionCauldronBlockEntity(blockPos, blockState);
        return this.blockEntity;
    }
}
