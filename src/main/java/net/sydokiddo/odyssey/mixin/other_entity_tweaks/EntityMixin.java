package net.sydokiddo.odyssey.mixin.other_entity_tweaks;


import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public Level level;

    @Shadow public abstract void setSwimming(boolean bl);

    @Shadow public abstract boolean isSprinting();

    @Shadow public abstract boolean isUnderWater();

    @Shadow public abstract boolean isPassenger();

    @Shadow private BlockPos blockPosition;

    @Shadow public abstract boolean isSwimming();

    @Shadow public abstract boolean isInLava();

    @Shadow public abstract boolean isEyeInFluid(TagKey<Fluid> tagKey);

    @Shadow protected boolean wasTouchingWater;

    public boolean isUnderLava() {
        return this.isEyeInFluid(FluidTags.LAVA) && this.isInLava();
    }

    @Inject(method = "isOnFire", at = @At("RETURN"), cancellable = true)
    public void displayFireAnimation(CallbackInfoReturnable<Boolean> cir) {
        boolean bl = this.level != null && this.level.isClientSide;
        if (bl) {
            Entity $this = (Entity) (Object) this;
            if ($this instanceof LivingEntity) {
                if (((LivingEntity) $this).hasEffect(MobEffects.FIRE_RESISTANCE)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }




}
