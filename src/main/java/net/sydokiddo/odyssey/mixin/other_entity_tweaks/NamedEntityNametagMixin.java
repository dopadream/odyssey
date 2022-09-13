package net.sydokiddo.odyssey.mixin.other_entity_tweaks;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Named Entities will drop Name Tags with their name on it when they die and send a death message in chat

@Mixin(LivingEntity.class)
public abstract class NamedEntityNametagMixin extends Entity {

    @Shadow protected boolean dead;

    @Shadow public abstract CombatTracker getCombatTracker();

    public NamedEntityNametagMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "die", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {

        if (!this.isRemoved() && !this.dead) {

            // Checks to see if the entity has a custom name

            if (hasCustomName() || (this instanceof OwnableEntity && ((OwnableEntity) this).getOwner() != null)) {
                ItemStack tag = new ItemStack(Items.NAME_TAG, 1);
                CompoundTag nbt = new CompoundTag();

                Component EntityName = getTypeName();

                if (hasCustomName()) {
                    EntityName = getCustomName();
                }

            // Sets the name of the dropped name tag to the killed entity's name

                tag.setHoverName(EntityName);
                assert tag.getTag() != null;
                tag.getTag().put("data",nbt);
                spawnAtLocation(tag);
            }

            // Sends the death message of the named entity in the chat

            if (hasCustomName() &&!this.level.isClientSide && this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES)) {
                this.level.players().forEach(player -> player.sendSystemMessage(this.getCombatTracker().getDeathMessage()));
            }
        }
    }
}