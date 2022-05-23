package net.sydokiddo.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import net.sydokiddo.world.feature.ModPlacedFeatures;

public class ModOreGeneration {

    public static void generateOres() {

// Generation for Ruby Ore:

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.RUBY_ORE_PLACED.getKey().get());
    }
}