package net.sydokiddo.odyssey.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.sydokiddo.odyssey.Odyssey;

public class ModItemGroup {
    public static final ItemGroup ODYSSEY = FabricItemGroupBuilder.build(new Identifier(Odyssey.MOD_ID, "odyssey"),
            () -> new ItemStack(ModItems.RUBY));
}
