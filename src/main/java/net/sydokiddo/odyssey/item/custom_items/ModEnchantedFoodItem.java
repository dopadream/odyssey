package net.sydokiddo.odyssey.item.custom_items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModEnchantedFoodItem extends Item {
    public ModEnchantedFoodItem(Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}