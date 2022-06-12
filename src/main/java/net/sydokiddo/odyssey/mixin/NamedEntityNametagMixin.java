package net.sydokiddo.odyssey.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Named Entities will drop Name Tags with their name on it when they die and send a death message in chat

@Mixin(LivingEntity.class)
public abstract class NamedEntityNametagMixin extends Entity {

    @Shadow protected boolean dead;

    @Shadow public abstract DamageTracker getDamageTracker();

    public NamedEntityNametagMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {

        if (!this.isRemoved() && !this.dead) {

            // Checks to see if the entity has a custom name

            if (hasCustomName() || (this instanceof Tameable && ((Tameable) this).getOwner() != null)) {
                ItemStack tag = new ItemStack(Items.NAME_TAG, 1);
                NbtCompound nbt = new NbtCompound();

                Text EntityName = getDefaultName();

                if (hasCustomName()) {
                    EntityName = getCustomName();
                }

            // Sets the name of the dropped name tag to the killed entity's name

                tag.setCustomName(EntityName);
                assert tag.getNbt() != null;
                tag.getNbt().put("data",nbt);
                dropStack(tag);
            }

            // Sends the death message of the named entity in the chat

            if (hasCustomName() &&!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES)) {
                this.world.getPlayers().forEach(player -> player.sendMessage(this.getDamageTracker().getDeathMessage()));
            }
        }
    }
}