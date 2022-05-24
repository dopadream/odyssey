package net.sydokiddo.odyssey;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sydokiddo.odyssey.block.ModBlocks;
import net.sydokiddo.odyssey.effect.ModEffects;
import net.sydokiddo.odyssey.init.MobBookItems;
import net.sydokiddo.odyssey.item.ModItems;
import net.sydokiddo.odyssey.sound.ModSoundEvents;
import net.sydokiddo.world.feature.ModConfiguredFeatures;
import net.sydokiddo.world.gen.ModWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings({"UnstableApiUsage", "rawtypes"})
public class Odyssey implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final String MOD_ID = "odyssey";

	public static Map<EntityType<?>, Item> booksMap = new HashMap();

	private static final Random RANDOM = new Random();

	@Override
	public void onInitialize() {

		// Registry:

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

		// Cake Eating Sounds + Particles:

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			BlockPos pos = hitResult.getBlockPos();
			BlockState blockState = world.getBlockState(pos);
			Block block = blockState.getBlock();

			if (!(block instanceof CakeBlock) || !player.canConsume(false) ||
					player.shouldCancelInteraction()) {
				return ActionResult.PASS;
			}
			ItemStack stack = block.getPickStack(world, pos, blockState);
			float modifier = 0.017453292F;

			for (int i = 0; i < 16; i++) {
				Vec3d vec3d = new Vec3d(((double) RANDOM.nextFloat() - 0.5D) * 0.1D,
						RANDOM.nextDouble() * 0.1D + 0.1D, 0.0D);
				vec3d = vec3d.rotateX(-player.getPitch() * modifier);
				vec3d = vec3d.rotateY(-player.getYaw() * modifier);
				double d0 = (double) (-RANDOM.nextFloat()) * 0.6D - 0.3D;
				Vec3d vec3d1 = new Vec3d(((double) RANDOM.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
				vec3d1 = vec3d1.rotateX(-player.getPitch() * modifier);
				vec3d1 = vec3d1.rotateY(-player.getYaw() * modifier);
				vec3d1 = vec3d1.add(player.getX(), player.getEyeY(), player.getZ());
				world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), vec3d1.x,
						vec3d1.y, vec3d1.z, vec3d.x, vec3d.y + 0.05D, vec3d.z);
			}
			player.playSound(player.getEatSound(stack), 0.5F + 0.5F * (float) RANDOM.nextInt(2),
					(RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1F);
			return ActionResult.PASS;
		});
	}

	public static void put(Item item, EntityType<?> type){
		booksMap.put(type, item);
	}
}