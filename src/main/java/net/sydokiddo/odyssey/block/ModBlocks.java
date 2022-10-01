package net.sydokiddo.odyssey.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.block.custom_blocks.*;
import net.sydokiddo.odyssey.item.ModItemGroup;
import net.sydokiddo.odyssey.sound.ModSoundEvents;

public class ModBlocks {



// List of Blocks:

    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
    new Block(FabricBlockSettings.of(Material.METAL, MaterialColor.FIRE)
    .sound(SoundType.METAL).requiresCorrectToolForDrops().destroyTime(5.0f).strength(6.0f)));

    public static final Block REFINED_AMETHYST_BLOCK = registerBlock("refined_amethyst_block",
    new AmethystBlock(FabricBlockSettings.of(Material.AMETHYST, MaterialColor.COLOR_PURPLE)
    .sound(SoundType.AMETHYST).requiresCorrectToolForDrops().destroyTime(3.0f).strength(1.5f)));

    public static final Block FIREFLY_LANTERN = registerBlock("firefly_lantern",
    new LanternBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.METAL)
    .sound(ModSoundEvents.FIREFLY_LANTERN).requiresCorrectToolForDrops().destroyTime(3.5f).strength(3.5f).lightLevel((blockStatex) -> {
        return 10;
    })));

    public static PotionCauldronBlock POTION_CAULDRON_STATE = new PotionCauldronBlock(FabricBlockSettings.copy(Blocks.CAULDRON), PotionCauldronInteraction.POTION_CAULDRON_BEHAVIOR);

    public static final Block POTION_CAULDRON = registerBlockNoItem("potion_cauldron",
            POTION_CAULDRON_STATE
    );

    public static final BlockEntityType<PotionCauldronBlockEntity> POTION_CAULDRON_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, Odyssey.MOD_ID + "potion_cauldron_entity", FabricBlockEntityTypeBuilder.create(PotionCauldronBlockEntity::new, POTION_CAULDRON).build(null));


    public static final Block GUNPOWDER_BLOCK = registerBlock("gunpowder_block",
    new FallingBlock(FabricBlockSettings.of(Material.DIRT, MaterialColor.COLOR_GRAY)
    .sound(SoundType.SAND).destroyTime(0.5f).strength(0.5f)));

    public static final Block SUGAR_CANE_BLOCK = registerBlock("sugar_cane_block",
    new ModPillarBlock(FabricBlockSettings.of(Material.PLANT, MaterialColor.GRASS)
    .sound(SoundType.GRASS).destroyTime(1.0f).strength(1.0f)));

    public static final Block BAMBOO_BLOCK = registerBlock("bamboo_block",
    new ModPillarBlock(FabricBlockSettings.of(Material.PLANT, MaterialColor.COLOR_GREEN)
    .sound(SoundType.BAMBOO).destroyTime(1.0f).strength(1.0f)));

    public static final Block ROTTEN_FLESH_BLOCK = registerBlock("rotten_flesh_block",
    new Block(FabricBlockSettings.of(Material.GRASS, MaterialColor.TERRACOTTA_ORANGE)
    .sound(SoundType.WET_GRASS).destroyTime(1.0f).strength(1.0f)));

    public static final Block SPIDER_EYE_BLOCK = registerBlock("spider_eye_block",
    new Block(FabricBlockSettings.of(Material.GRASS, MaterialColor.NETHER)
    .sound(SoundType.WET_GRASS).destroyTime(1.0f).strength(1.0f)));

    public static final Block MAGMA_CREAM_BLOCK = registerBlock("magma_cream_block",
    new MagmaCreamBlock(FabricBlockSettings.of(Material.GRASS, MaterialColor.COLOR_RED)
    .sound(SoundType.SLIME_BLOCK).destroyTime(0.0f).strength(0.0f).noOcclusion().lightLevel((blockStatex) -> {
        return 8;
    })));

    public static final Block BLAZE_POWDER_BLOCK = registerBlock("blaze_powder_block",
    new FallingBlock(FabricBlockSettings.of(Material.DIRT, MaterialColor.COLOR_ORANGE)
    .sound(SoundType.SAND).destroyTime(0.5f).strength(0.5f)));

    public static final Block PAPER_BLOCK = registerBlock("paper_block",
    new ModPillarBlock(FabricBlockSettings.of(Material.PLANT, MaterialColor.SNOW)
    .sound(ModSoundEvents.PAPER_BLOCK).destroyTime(0.5f).strength(0.5f)));

    public static final Block SUGAR_BLOCK = registerBlock("sugar_block",
    new FallingBlock(FabricBlockSettings.of(Material.DIRT, MaterialColor.SNOW)
    .sound(SoundType.SAND).destroyTime(0.5f).strength(0.5f)));

    public static final Block RUBY_ORE = registerBlock("ruby_ore",
    new DropExperienceBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE)
    .sound(SoundType.STONE).requiresCorrectToolForDrops().destroyTime(3.0f).strength(3.0f), UniformInt.of(3, 7)));

    public static final Block DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore",
    new DropExperienceBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.DEEPSLATE)
    .sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().destroyTime(4.5f).strength(3.0f), UniformInt.of(3, 7)));

    public static final Block WITHER_BONE_BLOCK = registerBlock("wither_bone_block",
    new ModPillarBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.COLOR_BLACK)
    .requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK).strength(2.0f)));

    public static final Block CARROT_BLOCK = registerBlock("carrot_block",
    new Block(FabricBlockSettings.of(Material.GRASS, MaterialColor.COLOR_ORANGE)
    .sound(SoundType.CROP).strength(1.0f)));

    public static final Block POTATO_BLOCK = registerBlock("potato_block",
    new Block(FabricBlockSettings.of(Material.GRASS, MaterialColor.WOOD)
    .sound(SoundType.CROP).strength(1.0f)));

    public static final Block BEETROOT_BLOCK = registerBlock("beetroot_block",
    new Block(FabricBlockSettings.of(Material.GRASS, MaterialColor.COLOR_RED)
    .sound(SoundType.CROP).strength(1.0f)));

// Registry for Blocks:

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, new ResourceLocation(Odyssey.MOD_ID, name), block);
    }

    private static Block registerBlockNoItem(String name, Block block){
        return Registry.register(Registry.BLOCK, new ResourceLocation(Odyssey.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registry.ITEM, new ResourceLocation(Odyssey.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().tab(ModItemGroup.ODYSSEY)));
    }

    public static void registerModBlocks() {
        System.out.println("Registering Blocks for " + Odyssey.MOD_ID);
    }
}