package net.sydokiddo.odyssey.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.world.World;

// Mixin to allow Tridents enchanted with Loyalty to return to the player if fallen into the void

@Mixin(TridentEntity.class)
public abstract class MixinTridentEntity extends Entity {

    public MixinTridentEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Final
    @Shadow
    private static TrackedData<Byte> LOYALTY;
    @Shadow
    private boolean dealtDamage;

    @Override
    protected void tickInVoid() {
        TridentEntity self = (TridentEntity)(Object)this;
        if (self.getOwner() != null) {
            int i = this.dataTracker.get(LOYALTY);
            if (i > 0) {
                this.dealtDamage = true;
                return;
            }
        }
        super.tickInVoid();
    }
}