package net.hecco.biomesbf;

import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.client.renderer.RenderType;

public class BiomesBFClient {
    public static void onInitializeClient() {
        NLServices.client().setBlockRenderType(BBFBlocks.GOLDEN_WALNUT_SAPLING.get(), RenderType.cutout());
        NLServices.client().setBlockRenderType(BBFBlocks.POTTED_GOLDEN_WALNUT_SAPLING.get(), RenderType.cutout());
        NLServices.client().setBlockRenderType(BBFBlocks.SALTGRASS.get(), RenderType.cutout());
        NLServices.client().setBlockRenderType(BBFBlocks.DRY_SALTGRASS.get(), RenderType.cutout());
    }
}
