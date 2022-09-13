package net.sydokiddo.odyssey.item.custom_items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CopperWrenchItem extends Item {

    public CopperWrenchItem(Tier material, Properties settings) {
        super(settings.defaultDurability(material.getUses()));
    }

    public CopperWrenchItem(Properties group) {
        super(group);
    }

    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getDestroySpeed(world, pos) != 0.0F) {
            stack.hurtAndBreak(2, miner, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }
}