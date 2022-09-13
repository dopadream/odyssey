package net.sydokiddo.odyssey.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MagmaCreamBlock extends HalfTransparentBlock {
    public MagmaCreamBlock(Properties settings) {
        super(settings);
    }

    // Allows for the Magma Cream Block to negate fall damage similarly to a Slime Block

    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(world, state, pos, entity, fallDistance);
        } else {
            entity.causeFallDamage(fallDistance, 0.0F, DamageSource.FALL);
        }

    }

    // Allows for the Magma Cream Block to deal damage when standing on it similarly to magma, also negatable by wearing Frost Walker boots or by crouching

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        double d = Math.abs(entity.getDeltaMovement().y);
        if (d < 0.1D && !entity.isSteppingCarefully()) {
            double e = 0.4D + d * 0.2D;
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(e, 1.0D, e));
        }
        if (!entity.fireImmune() && !entity.isShiftKeyDown() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
        }

        super.stepOn(world, pos, state, entity);
    }

    // Allows for the block to be transparent

    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.is(this) || super.skipRendering(state, stateFrom, direction);
    }

    // Allows for entities to bounce on the Magma Cream Block similarly to a Slime Block

    public void updateEntityAfterFallOn(BlockGetter world, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(world, entity);
        } else {
            this.bounce(entity);
        }

    }

    private void bounce(Entity entity) {
        Vec3 vec3d = entity.getDeltaMovement();
        if (vec3d.y < 0.0D) {
            double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setDeltaMovement(vec3d.x, -vec3d.y * d, vec3d.z);
        }

    }
}
