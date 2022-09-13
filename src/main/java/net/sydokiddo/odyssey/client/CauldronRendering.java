package net.sydokiddo.odyssey.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.sydokiddo.odyssey.block.ModBlocks;
import net.sydokiddo.odyssey.block.custom_blocks.PotionCauldronBlockEntity;

public class CauldronRendering {
    @Environment(EnvType.CLIENT)
    public static void setRenderColors() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PotionCauldronBlockEntity) {
                    return ((PotionCauldronBlockEntity) blockEntity).getColor();
                } else {
                    return 0;
                }
            }
            return 0;
        }, ModBlocks.POTION_CAULDRON);
    }
}
