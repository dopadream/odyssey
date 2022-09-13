package net.sydokiddo.odyssey.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.item.ModItemGroup;
import net.sydokiddo.odyssey.item.custom_items.AllayBookItem;

public class MobBookItems {
    public static Item ALLAY_BOOK_ITEM = registerMobBook();

    public static void init(){}

    private static Item registerMobBook() {
        var item = new AllayBookItem(EntityType.ALLAY, Settings.MOB_BOOK, "");
        return Registry.register(Registry.ITEM, new ResourceLocation(Odyssey.MOD_ID, "allay_bound_book"), item);
    }

    public static class Settings {
        public static final Item.Properties MOB_BOOK = new Item.Properties().stacksTo(1).tab(ModItemGroup.ODYSSEY);
    }
}