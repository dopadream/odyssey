package net.sydokiddo.odyssey.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import net.sydokiddo.odyssey.util.MobBottleHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract PlayerInventory getInventory();
    @Shadow @Nullable public abstract ItemEntity dropItem(ItemStack stack, boolean retainOwnership);

    @Shadow protected abstract void dropInventory();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        var player = this;
        var held = player.getStackInHand(hand);
        var mobBottle = new ItemStack(Odyssey.booksMap.get(entity.getType()));

        if ((held.getItem() == Items.BOOK) && this.isSneaking()) {

            if (held.isEmpty() || held.getItem() != Items.BOOK) {
                cir.setReturnValue(ActionResult.PASS);
            }

            if (entity.hasCustomName()) {
                mobBottle.setCustomName(entity.getCustomName());
            }

            var nbt = new NbtCompound();
            MobBottleHelper.setCompound(mobBottle, "", entity.writeNbt(nbt));

            world.playSound(null, player.getBlockPos(), ModSoundEvents.ITEM_ALLAY_BOOK_CAPTURE, SoundCategory.NEUTRAL, 1.0F, 1.0F);

            if (held.getCount() == 1) {
                player.setStackInHand(hand, mobBottle);
            } else {
                held.decrement(1);
                addOrDropStack(player, mobBottle);
            }

            player.swingHand(hand);
            entity.discard();
        }
    }

    public void addOrDropStack(PlayerEntityMixin player, ItemStack stack) {
        if (!player.getInventory().insertStack(stack)) {
            player.dropItem(stack, true);
        }
    }
}