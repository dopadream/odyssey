package net.sydokiddo.odyssey;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.sydokiddo.odyssey.block.ModBlocks;
import net.sydokiddo.odyssey.init.MobBookItems;
import net.sydokiddo.odyssey.item.ModItems;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"UnstableApiUsage", "rawtypes"})
public class Odyssey implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final String MOD_ID = "odyssey";

	public static Map<EntityType<?>, Item> booksMap = new HashMap();

	@Override
	public void onInitialize() {
		Reflection.initialize(MobBookItems.class);
		MobBookItems.init();

		put(MobBookItems.ALLAY_BOOK_ITEM, EntityType.ALLAY);

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModSoundEvents.registerSounds();

		LOGGER.info("Hello Fabric world!");
	}

	public static void put(Item item, EntityType<?> type){
		booksMap.put(type, item);
	}
}