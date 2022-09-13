package net.sydokiddo.odyssey.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.sydokiddo.odyssey.block.ModBlocks;

public class PotionCauldronBlockEntity extends BlockEntity {
    public Potion potion = ModBlocks.POTION_CAULDRON_STATE.potion;

    public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.POTION_CAULDRON_ENTITY, pos, state);
    }



    public int getColor() {
        if (this.hasPotion())
            return PotionUtils.getColor(this.potion);
        return 0xffffff;
    }

    public Potion getPotion() {
        return this.potion;
    }

    public boolean hasPotion() {
        return this.potion != Potions.EMPTY;
    }

    public boolean tryApplyPotion(Potion potion) {
        if (this.potion == Potions.EMPTY || this.potion == potion) {
            this.potion = potion;
            return true;
        }
        return false;
    }

    public static void particleTick(Level level, BlockPos blockPos, BlockState blockState, PotionCauldronBlockEntity potionCauldronBlockEntity) {
        if (potionCauldronBlockEntity.getPotion() !=  Potions.EMPTY && potionCauldronBlockEntity.hasPotion()) {
            RandomSource randomSource = level.random;
            int i = PotionUtils.getColor(potionCauldronBlockEntity.getPotion().getEffects());

            float f1 = randomSource.nextFloat();
            if (f1 > 0.05f) return;

            double d = (double)(i >> 16 & 0xFF) / 255.0;
            double e = (double)(i >> 8 & 0xFF) / 255.0;
            double f = (double)(i & 0xFF) / 255.0;
            level.addParticle(ParticleTypes.ENTITY_EFFECT, blockPos.getX() + 0.5, blockPos.getY() + 1f, blockPos.getZ() + 0.5, d, e, f);
        }
    }


    @Override
    public void saveAdditional(CompoundTag compound) {
        ResourceLocation identifier = Registry.POTION.getKey(this.potion);
        compound.putString("Potion", String.valueOf(identifier));

        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.potion = Potion.byName(compound.getString("Potion"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }


}
