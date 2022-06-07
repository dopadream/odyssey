package net.sydokiddo.odyssey.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.block.custom_blocks.MagmaCreamBlock;
import net.sydokiddo.odyssey.block.custom_blocks.ModPillarBlock;
import net.sydokiddo.odyssey.block.custom_blocks.PhantomMembraneBlock;
import net.sydokiddo.odyssey.item.ModItemGroup;
import net.sydokiddo.odyssey.sound.ModSoundEvents;

public class ModBlocks {

// List of Blocks:

    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
    new Block(FabricBlockSettings.of(Material.METAL, MapColor.BRIGHT_RED)
    .sounds(BlockSoundGroup.METAL).requiresTool().hardness(5.0f).strength(6.0f)));

    public static final Block FIREFLY_LANTERN = registerBlock("firefly_lantern",
    new LanternBlock(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY)
    .sounds(ModSoundEvents.FIREFLY_LANTERN).requiresTool().luminance(10).hardness(3.5f).strength(3.5f)));

    public static final Block GUNPOWDER_BLOCK = registerBlock("gunpowder_block",
    new FallingBlock(FabricBlockSettings.of(Material.SOIL, MapColor.GRAY)
    .sounds(BlockSoundGroup.SAND).hardness(0.5f).strength(0.5f)));

    public static final Block SUGAR_CANE_BLOCK = registerBlock("sugar_cane_block",
    new ModPillarBlock(FabricBlockSettings.of(Material.PLANT, MapColor.PALE_GREEN)
    .sounds(BlockSoundGroup.GRASS).hardness(1.0f).strength(1.0f)));

    public static final Block BAMBOO_BLOCK = registerBlock("bamboo_block",
    new ModPillarBlock(FabricBlockSettings.of(Material.PLANT, MapColor.GREEN)
    .sounds(BlockSoundGroup.BAMBOO).hardness(1.0f).strength(1.0f)));

    public static final Block ROTTEN_FLESH_BLOCK = registerBlock("rotten_flesh_block",
    new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.TERRACOTTA_ORANGE)
    .sounds(BlockSoundGroup.WET_GRASS).hardness(1.0f).strength(1.0f)));

    public static final Block PHANTOM_MEMBRANE_BLOCK = registerBlock("phantom_membrane_block",
    new PhantomMembraneBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.TERRACOTTA_LIGHT_GRAY)
    .sounds(BlockSoundGroup.WET_GRASS).hardness(1.0f).strength(1.0f)));

    public static final Block SPIDER_EYE_BLOCK = registerBlock("spider_eye_block",
    new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.DARK_RED)
    .sounds(BlockSoundGroup.WET_GRASS).hardness(1.0f).strength(1.0f)));

    public static final Block MAGMA_CREAM_BLOCK = registerBlock("magma_cream_block",
    new MagmaCreamBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.RED)
    .sounds(BlockSoundGroup.SLIME).luminance(8).hardness(0.0f).strength(0.0f).nonOpaque()));

    public static final Block BLAZE_POWDER_BLOCK = registerBlock("blaze_powder_block",
    new FallingBlock(FabricBlockSettings.of(Material.SOIL, MapColor.ORANGE)
    .sounds(BlockSoundGroup.SAND).hardness(0.5f).strength(0.5f)));

    public static final Block PAPER_BLOCK = registerBlock("paper_block",
    new ModPillarBlock(FabricBlockSettings.of(Material.PLANT, MapColor.WHITE)
    .sounds(ModSoundEvents.PAPER_BLOCK).hardness(0.5f).strength(0.5f)));

    public static final Block SUGAR_BLOCK = registerBlock("sugar_block",
    new FallingBlock(FabricBlockSettings.of(Material.SOIL, MapColor.WHITE)
    .sounds(BlockSoundGroup.SAND).hardness(0.5f).strength(0.5f)));

    public static final Block RUBY_ORE = registerBlock("ruby_ore",
    new OreBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
    .sounds(BlockSoundGroup.STONE).requiresTool().hardness(3.0f).strength(3.0f), UniformIntProvider.create(3, 7)));

    public static final Block DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore",
    new OreBlock(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY)
    .sounds(BlockSoundGroup.DEEPSLATE).requiresTool().hardness(4.5f).strength(3.0f), UniformIntProvider.create(3, 7)));

    public static final Block WITHER_BONE_BLOCK = registerBlock("wither_bone_block",
    new ModPillarBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK)
    .requiresTool().sounds(BlockSoundGroup.BONE).strength(2.0f)));

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