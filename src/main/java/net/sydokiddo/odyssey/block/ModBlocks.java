package net.sydokiddo.odyssey.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.item.ModItemGroup;

public class ModBlocks {

// List of Blocks:

    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
    new Block(FabricBlockSettings.of(Material.METAL, MapColor.BRIGHT_RED)
    .sounds(BlockSoundGroup.METAL).requiresTool().hardness(5.0f).strength(6.0f)));

    public static final Block FIREFLY_LANTERN = registerBlock("firefly_lantern",
    new LanternBlock(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY)
    .sounds(BlockSoundGroup.LANTERN).requiresTool().luminance(10).hardness(3.5f).strength(3.5f)));

// Registry for Blocks:

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, new Identifier(Odyssey.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registry.ITEM, new Identifier(Odyssey.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(ModItemGroup.ODYSSEY)));
    }

    public static void registerModBlocks() {
        System.out.println("Registering Blocks for " + Odyssey.MOD_ID);
    }

}