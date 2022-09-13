package net.sydokiddo.odyssey.mixin.item_tweaks;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Adds a cooldown to throwable potions so that they aren't extremely overpowered with being stackable

@Mixin(ThrowablePotionItem.class)
public abstract class ThrowablePotionItemMixin extends PotionItem {
    private ThrowablePotionItemMixin(Properties settings) {
        super(settings);
    }

    @Inject(method="use", at=@At("RETURN"))
    private void onUse(Level world, Player user, InteractionHand hand,
        CallbackInfoReturnable<InteractionResultHolder<ItemStack>> info) {
        user.getCooldowns().addCooldown(this, 20);
    }
}