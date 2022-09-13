package net.sydokiddo.odyssey.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.sydokiddo.odyssey.Odyssey;

// Registry for the Odyssey item group, which should be pretty self-explanatory

public class ModItemGroup {
    public static final CreativeModeTab ODYSSEY = FabricItemGroupBuilder.build(new ResourceLocation(Odyssey.MOD_ID, "odyssey"),
            () -> new ItemStack(ModItems.RUBY));
}
