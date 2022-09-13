package net.sydokiddo.odyssey.item.custom_items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModEnchantedFoodItem extends Item {
    public ModEnchantedFoodItem(Properties settings) {
        super(settings);
    }

    public boolean isFoil(ItemStack stack) {
        return true;
    }
}