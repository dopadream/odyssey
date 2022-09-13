package net.sydokiddo.odyssey.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {

// List of Configured Features:

    public static final List<OreConfiguration.TargetBlockState> RUBY_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                    ModBlocks.RUBY_ORE.defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    ModBlocks.DEEPSLATE_RUBY_ORE.defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> RUBY_ORE =
            FeatureUtils.register("ruby_ore", Feature.ORE,
                    new OreConfiguration(RUBY_ORES, 5));

    public static final List<OreConfiguration.TargetBlockState> RUBY_ORES_LARGE = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                    ModBlocks.RUBY_ORE.defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    ModBlocks.DEEPSLATE_RUBY_ORE.defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> RUBY_ORE_LARGE =
            FeatureUtils.register("ruby_ore_large", Feature.ORE,
                    new OreConfiguration(RUBY_ORES_LARGE, 8));


// Registry for Configured Features:

    public static void registerConfiguredFeatures() {
        System.out.println("Registering Configured Features for " + Odyssey.MOD_ID);
    }
}