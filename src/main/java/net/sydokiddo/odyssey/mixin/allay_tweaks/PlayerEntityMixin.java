package net.sydokiddo.odyssey.mixin.allay_tweaks;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import net.sydokiddo.odyssey.util.MobBookHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Issue: (Allays don't seem to remember how to pathfind to note blocks once captured in a book)

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Shadow public abstract Inventory getInventory();
    public abstract void dropItem(ItemStack stack, boolean retainOwnership);

    @Shadow protected abstract void dropEquipment();

    @Inject(method = "interactOn", at = @At("HEAD"), cancellable = true)
    public void interact(Entity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        var player = this;
        var held = player.getItemInHand(hand);
        var mobBook = new ItemStack(Odyssey.booksMap.get(entity.getType()));

        // Detects if the player has a book in hand and is sneaking in order to capture an Allay

        if ((held.getItem() == Items.BOOK) && this.isShiftKeyDown() && !entity.isPassenger() && (entity.getType() == EntityType.ALLAY)) {

            // Prevents capturing of Allay if the player is not holding a book

            if (held.isEmpty() || held.getItem() != Items.BOOK) {
                cir.setReturnValue(InteractionResult.PASS);
            }

            // Sets the name of the Allay Bound Book to the Allay's name

            if (entity.hasCustomName()) {
                mobBook.setHoverName(entity.getCustomName());
            }

            // Sets the NBT data of the Allay to the Allay Bound Book

            var nbt = new CompoundTag();
            MobBookHelper.setCompound(mobBook, "", entity.saveWithoutId(nbt));
            entity.removeVehicle();

            level.playSound(null, player.blockPosition(), ModSoundEvents.ITEM_ALLAY_BOOK_CAPTURE, SoundSource.NEUTRAL, 1.0F, 1.0F);

            if (held.getCount() == 1) {
                player.setItemInHand(hand, mobBook);
            } else {
                held.shrink(1);
                addOrDropStack(player, mobBook);
            }

            // Pass and don't do anything if the entity is not an Allay

            if (!(entity.getType() == EntityType.ALLAY)) {
                cir.setReturnValue(InteractionResult.PASS);
            }

            player.swing(hand);
            player.gameEvent(GameEvent.ENTITY_INTERACT);
            entity.discard();
        }
    }

    // Drops the Allay Bound Book if the player's inventory is full

    public void addOrDropStack(PlayerEntityMixin player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.dropItem(stack, true);
        }
    }
}