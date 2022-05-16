package net.sydokiddo.odyssey.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.sydokiddo.odyssey.util.MobBottleHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BookItemMixin {
    @Inject(method = "getEmptiedStack", at = @At(value = "HEAD"), cancellable = true)
    private static void stackableBottle(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        if (MobBottleHelper.isModified(stack) && stack.getCount() > 1) {
            MobBottleHelper.insertNewItem(player, new ItemStack(Items.BOOK));
            stack.decrement(1);
            cir.setReturnValue(stack);
        }
    }
}