package net.sydokiddo.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

public class ModPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> RUBY_ORE_PLACED = PlacedFeatures.register("ore_ruby",
            ModConfiguredFeatures.RUBY_ORE, ModOreFeatures.modifiersWithCount(5,
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
}