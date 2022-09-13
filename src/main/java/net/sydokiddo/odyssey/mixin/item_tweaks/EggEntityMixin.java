package net.sydokiddo.odyssey.mixin.item_tweaks;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Allows for egg knockback to apply to players

// Steph here,
// Too lazy to test this. Sorry.

@Mixin(ThrownEgg.class)
public abstract class EggEntityMixin extends ThrowableItemProjectile {
    public EggEntityMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"),
            method="onHitEntity"
    )
    protected void onHitPlayer(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof Player && !((Player) entity).getAbilities().invulnerable)
        {
            entity.setDeltaMovement(entity.getDeltaMovement().add(this.getDeltaMovement().normalize().scale(0.0f)));
            entity.hurtMarked = true;
            entity.hurt(DamageSource.thrown(this, this.getOwner()), 0.0001f);
        }

    }
}