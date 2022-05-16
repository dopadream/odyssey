package net.sydokiddo.odyssey.init;

import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.item.ModItemGroup;
import net.sydokiddo.odyssey.item.custom_items.AllayBottleItem;

public class MobBottleItems {
    public static Item ALLAY_BOTTLE_ITEM = registerMobBottle();

    public static void init(){}

    private static Item registerMobBottle() {
        var item = new AllayBottleItem(EntityType.ALLAY, Fluids.EMPTY, Settings.MOB_BUCKET, "");
        return Registry.register(Registry.ITEM, new Identifier(Odyssey.MOD_ID, "allay_bottle"), item);
    }

    public static class Settings {
        public static final Item.Settings MOB_BUCKET = new Item.Settings().maxCount(1).group(ModItemGroup.ODYSSEY);
    }
}