package net.sydokiddo.odyssey.mixin.weapon_enchantments;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.LootBonusEnchantment;
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

    // Mixin to allow Looting and any damage related enchantment to work on Amethyst Daggers

    @Shadow
    @Final
    public EnchantmentCategory category;
    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        if (enchantment instanceof LootBonusEnchantment) {
            if (category != EnchantmentCategory.WEAPON || !(stack.getItem() instanceof AmethystDaggerItem)) return;
            cir.setReturnValue(true);
        }
        if (enchantment instanceof DamageEnchantment) {
            if (category != EnchantmentCategory.WEAPON || !(stack.getItem() instanceof AmethystDaggerItem)) return;
            cir.setReturnValue(true);
        }
    }
}