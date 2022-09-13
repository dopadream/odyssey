package net.sydokiddo.odyssey.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.sydokiddo.odyssey.world.feature.ModPlacedFeatures;

public class ModOreGeneration {

    public static void generateOres() {

// Generation for Ruby Ore:

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.RUBY_ORE_PLACED.unwrapKey().get());

// Generation for Ruby Ore (In Dark Forest):

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DARK_FOREST),
                GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.RUBY_ORE_PLACED_LARGE.unwrapKey().get());
    }
}