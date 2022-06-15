package net.sydokiddo.odyssey.mixin.weapon_enchantments;

import net.minecraft.enchantment.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.sydokiddo.odyssey.item.custom_items.AmethystDaggerItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(Enchantment.class)
@SuppressWarnings("ConstantConditions")

public abstract class AmethystDaggerModification {

    // Mixin to allow Looting, any Damage Enchantment, and Fire Aspect to work on Amethyst Daggers

    @Shadow
    @Final
    public EnchantmentTarget type;
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        if (enchantment instanceof LuckEnchantment) {
            if (type != EnchantmentTarget.WEAPON || !(stack.getItem() instanceof AmethystDaggerItem)) return;
            cir.setReturnValue(true);
        }
        if (enchantment instanceof DamageEnchantment) {
            if (type != EnchantmentTarget.WEAPON || !(stack.getItem() instanceof AmethystDaggerItem)) return;
            cir.setReturnValue(true);
        }
        if (enchantment instanceof FireAspectEnchantment) {
            if (type != EnchantmentTarget.WEAPON || !(stack.getItem() instanceof AmethystDaggerItem)) return;
            cir.setReturnValue(true);
        }
    }
}