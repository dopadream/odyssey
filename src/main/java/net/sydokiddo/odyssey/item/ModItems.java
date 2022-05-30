package net.sydokiddo.odyssey.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.item.custom_items.*;

public class ModItems {

// List of Items:

    public static final Item RUBY = registerItem("ruby",
            new Item(new FabricItemSettings().group(ModItemGroup.ODYSSEY)));

    public static final Item IRON_POTATO = registerItem("iron_potato", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(10).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 300, 1), 1f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 300, 0), 1f).build())
            .rarity(Rarity.RARE).group(ModItemGroup.ODYSSEY)));

    public static final Item ENCHANTED_IRON_POTATO = registerItem("enchanted_iron_potato", new ModEnchantedFoodItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(10).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 900, 1), 1f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 900, 0), 1f).build())
            .rarity(Rarity.EPIC).group(ModItemGroup.ODYSSEY)));

    public static final Item SOUL_POWDER = registerItem("soul_powder",
            new Item(new FabricItemSettings().group(ModItemGroup.ODYSSEY)));

    public static final Item GLASS_SHARD = registerItem("glass_shard",
            new Item(new FabricItemSettings().group(ModItemGroup.ODYSSEY)));

    public static final Item WITHER_BONE_MEAL = registerItem("wither_bone_meal",
            new WitherBoneMealItem(new FabricItemSettings().group(ModItemGroup.ODYSSEY)));

    public static final Item WITHER_BONE = registerItem("wither_bone",
            new Item(new FabricItemSettings().group(ModItemGroup.ODYSSEY)));

    public static final Item WITHER_SKULL_FRAGMENT = registerItem("wither_skull_fragment",
            new Item(new FabricItemSettings().group(ModItemGroup.ODYSSEY)));

// To Implement Later:

//    public static final Item ROSE_GOLD_INGOT = registerItem("rose_gold_ingot",
//            new Item(new FabricItemSettings().group(ModItemGroup.ODYSSEY)));

//    public static final Item SOUL_STEEL_INGOT = registerItem("soul_steel_ingot",
//            new Item(new FabricItemSettings().fireproof().group(ModItemGroup.ODYSSEY)));

//    public static final Item FLINTLOCK = registerItem("flintlock",
//            new Item(new FabricItemSettings().group(ModItemGroup.ODYSSEY).maxCount(1)));

//    public static final Item PELLET = registerItem("pellet",
//            new Item(new FabricItemSettings().group(ModItemGroup.ODYSSEY)));

// Registry for Items:

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Odyssey.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering Items for " + "odyssey");
    }
}