package net.sydokiddo.odyssey.mixin.block_tweaks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.sydokiddo.odyssey.misc.OdysseyTags;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Allows for any blocks in the Kinetic Cushioning Blocks tag to cushion kinetic damage

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Unique
    private boolean isNearKineticCushioningBlock(LivingEntity entity) {
        BlockPos landingPos = this.getOnPosLegacy();
        for (Direction direction : Direction.values()) {
            if (entity.getCommandSenderWorld().getBlockState(landingPos.relative(direction)).is(OdysseyTags.KINETIC_CUSHIONING_BLOCKS) && entity.isFallFlying()) {
                return true;
            }
        }
        return false;
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    private void playSound(LivingEntity entity, SoundEvent sound, float volume, float pitch) {
        if (this.isNearKineticCushioningBlock(entity) && entity.isFallFlying()) {
            super.playSound(ModSoundEvents.BLOCK_PHANTOM_CUSHION_ABSORB, volume, pitch);
        } else {
            super.playSound(sound, volume, pitch);
        }
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean damage(LivingEntity entity, DamageSource source, float amount) {
        if (!this.isNearKineticCushioningBlock(entity) && entity.isFallFlying()) {
            return entity.hurt(source, amount);
        }
        return false;
    }
}