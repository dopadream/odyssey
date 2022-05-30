package net.sydokiddo.odyssey.misc;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OdysseyTags {
    public static final TagKey<Block> UNPHASEABLE_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("odyssey", "unphaseable_blocks"));
}