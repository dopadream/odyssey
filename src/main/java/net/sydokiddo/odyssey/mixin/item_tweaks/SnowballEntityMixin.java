package net.sydokiddo.odyssey.mixin.item_tweaks;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Allows for snowball knockback to apply to players

// Steph here,
//
// Here's a fun fact:
//
// Did you know that for snow to form, the air needs pollen or dust?
// If the air is completely pure, it's impossible to form snow.
// Water droplets freeze onto the pollen/dust particle midair,
// freezing immediately, creating an ice crystal!

// So, the next time you get annoyed by dust or pollen,
// remember that snow wouldn't exist without it!


@Mixin(Snowball.class)
public abstract class SnowballEntityMixin extends ThrowableItemProjectile {
    public SnowballEntityMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }
    @Inject(at = @At("TAIL"),
            method="onHitEntity",
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    protected void onHitPlayer(EntityHitResult entityHitResult, CallbackInfo ci, Entity entity) {

        if (entity instanceof Player && !((Player) entity).getAbilities().invulnerable)
        {
            entity.setDeltaMovement(entity.getDeltaMovement().add(this.getDeltaMovement().normalize().scale(0.0f)));
            entity.hurtMarked = true;
            entity.hurt(DamageSource.thrown(this, this.getOwner()), 0.0001f);
        }

    }
}