package net.sydokiddo.odyssey;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.sydokiddo.odyssey.block.ModBlocks;
import net.sydokiddo.odyssey.effect.ModEffects;
import net.sydokiddo.odyssey.init.MobBookItems;
import net.sydokiddo.odyssey.item.ModItems;
import net.sydokiddo.odyssey.misc.OdysseyTags;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import net.sydokiddo.odyssey.world.feature.ModConfiguredFeatures;
import net.sydokiddo.odyssey.world.gen.ModWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"UnstableApiUsage", "rawtypes"})
public class Odyssey implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final String MOD_ID = "odyssey";

	public static boolean isOdysseyLoaded = false;

	// Blocks that can be converted to another block:

	public static final ImmutableMap<Block, Block> BLOCK_CONVERTING = ImmutableMap.<Block, Block>builder()
			.put(Blocks.POPPY, Blocks.WITHER_ROSE)
			.put(Blocks.DANDELION, Blocks.WITHER_ROSE)
			.put(Blocks.BLUE_ORCHID, Blocks.WITHER_ROSE)
			.put(Blocks.ALLIUM, Blocks.WITHER_ROSE)
			.put(Blocks.AZURE_BLUET, Blocks.WITHER_ROSE)
			.put(Blocks.RED_TULIP, Blocks.WITHER_ROSE)
			.put(Blocks.ORANGE_TULIP, Blocks.WITHER_ROSE)
			.put(Blocks.WHITE_TULIP, Blocks.WITHER_ROSE)
			.put(Blocks.PINK_TULIP, Blocks.WITHER_ROSE)
			.put(Blocks.OXEYE_DAISY, Blocks.WITHER_ROSE)
			.put(Blocks.CORNFLOWER, Blocks.WITHER_ROSE)
			.put(Blocks.LILY_OF_THE_VALLEY, Blocks.WITHER_ROSE)
			.put(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.put(Blocks.DIRT, Blocks.COARSE_DIRT)
			.put(Blocks.ROOTED_DIRT, Blocks.DIRT)
			.put(Blocks.MYCELIUM, Blocks.DIRT)
			.put(Blocks.PODZOL, Blocks.DIRT)
			.put(Blocks.DIRT_PATH, Blocks.DIRT)
			.put(Blocks.FARMLAND, Blocks.DIRT)
			.put(Blocks.OAK_SAPLING, Blocks.DEAD_BUSH)
			.put(Blocks.BIRCH_SAPLING, Blocks.DEAD_BUSH)
			.put(Blocks.SPRUCE_SAPLING, Blocks.DEAD_BUSH)
			.put(Blocks.DARK_OAK_SAPLING, Blocks.DEAD_BUSH)
			.put(Blocks.ACACIA_SAPLING, Blocks.DEAD_BUSH)
			.put(Blocks.AZALEA, Blocks.DEAD_BUSH)
			.put(Blocks.FLOWERING_AZALEA, Blocks.DEAD_BUSH)
			.build();

	public static Map<EntityType<?>, Item> booksMap = new HashMap();

	@Override
	public void onInitialize() {

		// Registry:

		isOdysseyLoaded = FabricLoader.getInstance().isModLoaded("odyssey");
		Reflection.initialize(MobBookItems.class);
		MobBookItems.init();
		put(MobBookItems.ALLAY_BOOK_ITEM, EntityType.ALLAY);
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModSoundEvents.registerSounds();
		ModEffects.registerEffects();
		ModWorldGen.generateModWorldGen();
		ModConfiguredFeatures.registerConfiguredFeatures();

		LOGGER.info("Thank you for downloading Odyssey! :)");

		// Furnace Fuel Registry:

		FuelRegistry.INSTANCE.add(ModBlocks.BAMBOO_BLOCK, 400);

		// Flammable Block Registry:

		FlammableBlockRegistry.getDefaultInstance().add(Blocks.COBWEB, 60, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BAMBOO_BLOCK, 60, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.SUGAR_CANE_BLOCK, 60, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PAPER_BLOCK, 60, 20);

		// Turning small flowers into Wither Roses using Wither Bone Meal

		UseBlockCallback.EVENT.register((player, world, hand, hitresult) -> {
			if (player.getStackInHand(hand).getItem() == ModItems.WITHER_BONE_MEAL) {

				var pos = hitresult.getBlockPos();
				var block = world.getBlockState(pos);

				if (block.isIn(OdysseyTags.WITHER_BONE_MEAL_CONVERTIBLE)) {

					var decay = BLOCK_CONVERTING.get(block.getBlock());
					if (decay != null) {

						// Changes any small flower to a Wither Rose

						world.setBlockState(pos, Objects.requireNonNull(BLOCK_CONVERTING.get(block.getBlock())).getDefaultState());

						// Particles and Sound Event

						world.playSound(null, pos, ModSoundEvents.ITEM_WITHER_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
						world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

						// Stats and Advancements

						ItemStack heldItem = player.getStackInHand(hand);
						Item item = heldItem.getItem();
						player.incrementStat(Stats.USED.getOrCreateStat(item));
						if (player instanceof ServerPlayerEntity) {
							Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, pos, heldItem);
						}

						// Remove 1 Wither Bone Meal if the player is not in Creative Mode

						if (!player.isCreative()) {
							heldItem.decrement(1);
						}

						return ActionResult.SUCCESS;
					} else {
						return ActionResult.PASS;
					}
				}
			} else {
				return ActionResult.PASS;
			}

			return ActionResult.PASS;
		});
	}

	// Backstabbing Damage Source for Amethyst Dagger:

	public static class BackstabDamageSource extends EntityDamageSource {

		public BackstabDamageSource(Entity source) {
			super("backstab", source);
			setBypassesArmor();
		}
	}

	public static void put(Item item, EntityType<?> type) {
		booksMap.put(type, item);
	}
}