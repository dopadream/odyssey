package net.sydokiddo.odyssey.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {

    public static final Holder<PlacedFeature> RUBY_ORE_PLACED = PlacementUtils.register("ore_ruby",
            ModConfiguredFeatures.RUBY_ORE, ModOreFeatures.modifiersWithCount(5,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

    public static final Holder<PlacedFeature> RUBY_ORE_PLACED_LARGE = PlacementUtils.register("ore_ruby_large",
            ModConfiguredFeatures.RUBY_ORE_LARGE, ModOreFeatures.modifiersWithCount(8,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-60), VerticalAnchor.absolute(120))));
}