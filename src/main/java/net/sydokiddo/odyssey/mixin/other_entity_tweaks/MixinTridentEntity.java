package net.sydokiddo.odyssey.mixin.other_entity_tweaks;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Mixin to allow Tridents enchanted with Loyalty to return to the player if fallen into the void

@Mixin(ThrownTrident.class)
public abstract class MixinTridentEntity extends Entity {

    public MixinTridentEntity(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }

    @Final
    @Shadow
    private static EntityDataAccessor<Byte> ID_LOYALTY;
    @Shadow
    private boolean dealtDamage;

    @Override
    protected void outOfWorld() {
        ThrownTrident self = (ThrownTrident)(Object)this;
        if (self.getOwner() != null) {
            int i = this.entityData.get(ID_LOYALTY);
            if (i > 0) {
                this.dealtDamage = true;
                return;
            }
        }
        super.outOfWorld();
    }
}