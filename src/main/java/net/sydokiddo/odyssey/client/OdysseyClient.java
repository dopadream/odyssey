package net.sydokiddo.odyssey.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.sydokiddo.odyssey.block.ModBlocks;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class OdysseyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Renders Blocks in List as Transparent (Without Translucency)

        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.cutout(),

                ModBlocks.FIREFLY_LANTERN

        );

        // Renders Blocks in List as Transparent (With Translucency)

        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.translucent(),

                ModBlocks.MAGMA_CREAM_BLOCK,
                ModBlocks.FIREFLY_LANTERN

        );

        // Initializes potion cauldron color rendering

        CauldronRendering.setRenderColors();

    }
}