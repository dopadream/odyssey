package net.sydokiddo.odyssey;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.sydokiddo.odyssey.block.ModBlocks;
import net.sydokiddo.odyssey.item.ModItems;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Odyssey implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final String MOD_ID = "odyssey";

	@Override
	public void onInitialize() {

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModSoundEvents.registerSounds();

		LOGGER.info("Hello Fabric world!");
	}
}
