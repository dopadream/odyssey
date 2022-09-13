package net.sydokiddo.odyssey.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MobBookHelper extends Item {
    public MobBookHelper(Properties settings) {
        super(settings);
    }

    public static <T extends Entity> T spawn(EntityType<T> type, ServerLevel world, BlockPos pos, MobSpawnType reason) {
        return type.create(world, null, null, null, pos, reason, false, false);
    }

    public static CompoundTag getCompound(ItemStack stack, String tag) {
        return getCompound(stack, tag, false);
    }

    public static CompoundTag getCompound(ItemStack stack, String tag, boolean nullify) {
        return tagExists(stack, tag) ? getNBT(stack).getCompound(tag) : (nullify ? null : new CompoundTag());
    }

    public static boolean tagExists(ItemStack stack, String tag) {
        return !stack.isEmpty() && stack.hasTag() && getNBT(stack).contains(tag);
    }

    public static CompoundTag getNBT(ItemStack stack) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        return stack.getTag();
    }

    public static void setCompound(ItemStack stack, String tag, CompoundTag cmp) {
        getNBT(stack).put(tag, cmp);
    }

    public static void insertNewItem(Player player, ItemStack stack2) {
        if (!player.getInventory().add(stack2)) {
            player.drop(stack2, false);
        }
        if (player instanceof ServerPlayer) {
            ((ServerPlayer) player).initInventoryMenu();
        }
    }

    public static boolean isModified(ItemStack s){
        if (s.isEmpty()){
            return false;
        }
        Item i = s.getItem();
        return (((ItemMaxCount)i).getVanillaMaxCount()!=i.getMaxStackSize())&&s.getCount()>0;
    }
}