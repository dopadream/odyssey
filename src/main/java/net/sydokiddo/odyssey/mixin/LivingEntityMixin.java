package net.sydokiddo.odyssey.mixin;

import net.sydokiddo.odyssey.misc.OdysseyTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

// Allows for any blocks in the Kinetic Cushioning Blocks tag to cushion kinetic damage

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private boolean isNearKineticCushioningBlock(LivingEntity entity) {
        BlockPos landingPos = this.getLandingPos();
        for (Direction direction : Direction.values()) {
            if (entity.getEntityWorld().getBlockState(landingPos.offset(direction)).isIn(OdysseyTags.KINETIC_CUSHIONING_BLOCKS) && entity.isFallFlying()) {
                return true;
            }
        }
        return false;
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    private void playSound(LivingEntity entity, SoundEvent sound, float volume, float pitch) {
        if (this.isNearKineticCushioningBlock(entity) && entity.isFallFlying()) {
            super.playSound(SoundEvents.BLOCK_WOOL_FALL, volume, pitch);
        } else {
            super.playSound(sound, volume, pitch);
        }
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean damage(LivingEntity entity, DamageSource source, float amount) {
        if (!this.isNearKineticCushioningBlock(entity) && entity.isFallFlying()) {
            return entity.damage(source, amount);
        }
        return false;
    }
}