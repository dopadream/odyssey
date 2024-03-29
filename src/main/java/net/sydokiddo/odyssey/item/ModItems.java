package net.sydokiddo.odyssey.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.item.custom_items.*;

public class ModItems {

// List of Items:

    public static final Item RUBY = registerItem("ruby",
    new Item(new FabricItemSettings().tab(ModItemGroup.ODYSSEY)));

    public static final Item COPPER_NUGGET = registerItem("copper_nugget",
    new Item(new FabricItemSettings().tab(ModItemGroup.ODYSSEY)));

    public static final Item IRON_POTATO = registerItem("iron_potato",
    new Item(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(6).saturationMod(10).alwaysEat()
    .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1), 1f)
    .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0), 1f).build())
    .rarity(Rarity.RARE).tab(ModItemGroup.ODYSSEY)));

    public static final Item ENCHANTED_IRON_POTATO = registerItem("enchanted_iron_potato",
    new ModEnchantedFoodItem(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(6).saturationMod(10).alwaysEat()
    .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 1), 1f)
    .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 900, 0), 1f).build())
    .rarity(Rarity.EPIC).tab(ModItemGroup.ODYSSEY)));

    public static final Item SOUL_POWDER = registerItem("soul_powder",
    new Item(new FabricItemSettings().tab(ModItemGroup.ODYSSEY)));

    public static final Item GLASS_SHARD = registerItem("glass_shard",
    new Item(new FabricItemSettings().tab(ModItemGroup.ODYSSEY)));

    public static final Item WITHER_BONE_MEAL = registerItem("wither_bone_meal",
    new WitherBoneMealItem(new FabricItemSettings().tab(ModItemGroup.ODYSSEY)));

    public static final Item WITHER_BONE = registerItem("wither_bone",
    new Item(new FabricItemSettings().tab(ModItemGroup.ODYSSEY)));

    public static final Item WITHER_SKULL_FRAGMENT = registerItem("wither_skull_fragment",
    new Item(new FabricItemSettings().tab(ModItemGroup.ODYSSEY)));

    public static final Item RUINED_TRIDENT = registerItem("ruined_trident",
    new Item(new FabricItemSettings().stacksTo(1).tab(ModItemGroup.ODYSSEY)));

    public static final Item COPPER_WRENCH = registerItem("copper_wrench",
    new CopperWrenchItem(new FabricItemSettings().stacksTo(1).durability(256).tab(ModItemGroup.ODYSSEY)));

    public static final Item AMETHYST_DAGGER = registerItem("amethyst_dagger",
    new AmethystDaggerItem(ModToolMaterial.AMETHYST, 1, -2f,
    new FabricItemSettings().tab(ModItemGroup.ODYSSEY)));

// Registry for Items:

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new ResourceLocation(Odyssey.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering Items for " + "odyssey");
    }
}