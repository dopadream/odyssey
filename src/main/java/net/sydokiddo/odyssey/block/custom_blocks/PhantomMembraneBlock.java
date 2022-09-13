package net.sydokiddo.odyssey.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class PhantomMembraneBlock extends Block {
    public PhantomMembraneBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float distance) {
        entity.playSound(SoundEvents.WOOL_FALL, 1, 1);
        entity.causeFallDamage(distance, 0, DamageSource.FALL);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter world, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(world, entity);
        } else {
            Vec3 velocity = entity.getDeltaMovement();
            if (velocity.y < 0) {
                double livingModifier = entity instanceof LivingEntity ? 1 : 0.8;
                entity.setDeltaMovement(velocity.x, velocity.y * livingModifier * -0.8, velocity.z);
            }
        }
    }
}