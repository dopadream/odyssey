package net.sydokiddo.odyssey.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {

// List of Configured Features:

    public static final List<OreFeatureConfig.Target> RUBY_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    ModBlocks.RUBY_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    ModBlocks.DEEPSLATE_RUBY_ORE.getDefaultState()));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> RUBY_ORE =
            ConfiguredFeatures.register("ruby_ore", Feature.ORE,
                    new OreFeatureConfig(RUBY_ORES, 5));

    public static final List<OreFeatureConfig.Target> RUBY_ORES_LARGE = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    ModBlocks.RUBY_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    ModBlocks.DEEPSLATE_RUBY_ORE.getDefaultState()));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> RUBY_ORE_LARGE =
            ConfiguredFeatures.register("ruby_ore_large", Feature.ORE,
                    new OreFeatureConfig(RUBY_ORES_LARGE, 8));


// Registry for Configured Features:

    public static void registerConfiguredFeatures() {
        System.out.println("Registering Configured Features for " + Odyssey.MOD_ID);
    }
}