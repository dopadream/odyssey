package net.sydokiddo.odyssey.mixin.item_tweaks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Allows for snowball knockback to apply to players

@Mixin(SnowballEntity.class)
public abstract class SnowballEntityMixin extends ThrownItemEntity {
    public SnowballEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(at = @At("TAIL"),
            method="onEntityHit",
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    protected void onHitPlayer(EntityHitResult entityHitResult, CallbackInfo ci, Entity entity) {

        if (entity instanceof PlayerEntity && !((PlayerEntity) entity).getAbilities().invulnerable)
        {
            entity.setVelocity(entity.getVelocity().add(this.getVelocity().normalize().multiply(0.0f)));
            entity.velocityModified = true;
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.0001f);
        }

    }
}