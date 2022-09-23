package net.sydokiddo.odyssey.misc;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class OdysseyTags {
    public static final TagKey<Block> UNPHASEABLE_BLOCKS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("odyssey", "unphaseable_blocks"));
    public static final TagKey<Block> WITHER_BONE_MEAL_CONVERTIBLE = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("odyssey", "wither_bone_meal_convertible"));
}