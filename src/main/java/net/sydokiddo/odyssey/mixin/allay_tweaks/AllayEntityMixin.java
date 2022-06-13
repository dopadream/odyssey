package net.sydokiddo.odyssey.mixin.allay_tweaks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.VibrationListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Allows for Allays to follow players who are holding Amethyst Shards or Echo Shards

@Mixin(AllayEntity.class)
public abstract class AllayEntityMixin extends PathAwareEntity implements InventoryOwner, VibrationListener.Callback {

    public AllayEntityMixin(EntityType<? extends AllayEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V",
            at = @At(value = "TAIL"))
    private void inject(EntityType entityType, World world, CallbackInfo ci) {
        this.goalSelector.add(4, new TemptGoal(this, 2.0D, Ingredient.ofItems(Items.AMETHYST_SHARD, Items.ECHO_SHARD), true));
    }
}