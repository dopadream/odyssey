package net.sydokiddo.odyssey.mixin.item_tweaks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

// Adds a cooldown to throwable potions so that they aren't extremely overpowered with being stackable

@Mixin(ThrowablePotionItem.class)
public abstract class ThrowablePotionItemMixin extends PotionItem {
    private ThrowablePotionItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method="use", at=@At("RETURN"))
    private void onUse(World world, PlayerEntity user, Hand hand,
        CallbackInfoReturnable<TypedActionResult<ItemStack>> info) {
        user.getItemCooldownManager().set(this, 20);
    }
}