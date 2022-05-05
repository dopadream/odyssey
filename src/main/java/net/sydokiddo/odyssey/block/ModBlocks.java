package net.sydokiddo.odyssey.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.sydokiddo.odyssey.Odyssey;
import net.sydokiddo.odyssey.block.custom_blocks.*;
import net.sydokiddo.odyssey.item.ModItemGroup;
import net.sydokiddo.odyssey.sound.ModSoundEvents;

public class ModBlocks {

// List of Blocks:

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