package net.sydokiddo.odyssey.mixin.other_entity_tweaks;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Allows for Villagers to follow the player if the player is holding an Emerald Block

@Mixin(Villager.class)
public abstract class VillagerEntityMixin extends AbstractVillager {

    public VillagerEntityMixin(EntityType<? extends AbstractVillager> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/npc/VillagerType;)V",
            at = @At(value = "TAIL"))
    private void inject(EntityType<? extends Villager> entityType, Level world, VillagerType type, CallbackInfo ci) {
        this.goalSelector.addGoal(2, new TemptGoal(this, .4D, Ingredient.of(Items.EMERALD_BLOCK), true));
    }

}