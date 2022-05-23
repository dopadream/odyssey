package net.sydokiddo.odyssey.mixin;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.VibrationListener;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.init.MobBookItems;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import net.sydokiddo.odyssey.util.MobBookHelper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

// Issue: (Allays don't seem to remember how to pathfind to note blocks once captured in a book)

// Issue: (Capturing an Allay that is sitting in a boat deletes the boat and the book)

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract PlayerInventory getInventory();
    @Shadow @Nullable public abstract ItemEntity dropItem(ItemStack stack, boolean retainOwnership);

    @Shadow protected abstract void dropInventory();

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        var player = this;
        var held = player.getStackInHand(hand);
        var mobBook = new ItemStack(Odyssey.booksMap.get(entity.getType()));

        // Detects if the player has a book in hand and is sneaking in order to capture an Allay

        if ((held.getItem() == Items.BOOK) && this.isSneaking()) {

            if (held.isEmpty() || held.getItem() != Items.BOOK) {
                cir.setReturnValue(ActionResult.PASS);
            }

            if (entity.hasCustomName()) {
                mobBook.setCustomName(entity.getCustomName());
            }

            // Sets the NBT data of the Allay to the Allay Bound Book

            var nbt = new NbtCompound();
            MobBookHelper.setCompound(mobBook, "", entity.writeNbt(nbt));

            world.playSound(null, player.getBlockPos(), ModSoundEvents.ITEM_ALLAY_BOOK_CAPTURE, SoundCategory.NEUTRAL, 1.0F, 1.0F);

            if (held.getCount() == 1) {
                player.setStackInHand(hand, mobBook);
            } else {
                held.decrement(1);
                addOrDropStack(player, mobBook);
            }

            player.swingHand(hand);
            entity.discard();
        }
    }

    // Drops the Allay Bound Book if the player's inventory is full

    public void addOrDropStack(PlayerEntityMixin player, ItemStack stack) {
        if (!player.getInventory().insertStack(stack)) {
            player.dropItem(stack, true);
        }
    }
}