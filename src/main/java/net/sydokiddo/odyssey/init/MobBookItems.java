package net.sydokiddo.odyssey.init;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.item.ModItemGroup;
import net.sydokiddo.odyssey.item.custom_items.AllayBookItem;

public class MobBookItems {
    public static Item ALLAY_BOOK_ITEM = registerMobBook();

    public static void init(){}

    private static Item registerMobBook() {
        var item = new AllayBookItem(EntityType.ALLAY, Settings.MOB_BOOK, "");
        return Registry.register(Registry.ITEM, new Identifier(Odyssey.MOD_ID, "allay_bound_book"), item);
    }

    public static class Settings {
        public static final Item.Settings MOB_BOOK = new Item.Settings().maxCount(1).group(ModItemGroup.ODYSSEY);
    }
}