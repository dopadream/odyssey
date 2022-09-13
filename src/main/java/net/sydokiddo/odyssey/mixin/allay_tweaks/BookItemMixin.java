package net.sydokiddo.odyssey.mixin.allay_tweaks;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.sydokiddo.odyssey.util.MobBookHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Decreases book item stack by 1 if the player captures an Allay with one

// Steph here,
// I decided to make it so that it only decreases if you're not in creative.
// I did this for consistency with other capturable mobs.

@Mixin(BucketItem.class)
public class BookItemMixin {
    @Inject(method = "getEmptySuccessItem", at = @At(value = "HEAD"), cancellable = true)
    private static void stackableBook(ItemStack stack, Player player, CallbackInfoReturnable<ItemStack> cir) {
        if (MobBookHelper.isModified(stack) && stack.getCount() > 1) {
            MobBookHelper.insertNewItem(player, new ItemStack(Items.BOOK));
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            cir.setReturnValue(stack);
        }
    }
}