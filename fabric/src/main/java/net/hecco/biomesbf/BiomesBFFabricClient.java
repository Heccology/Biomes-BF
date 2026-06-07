package net.hecco.biomesbf;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.hecco.biomesbf.definition.particle.SprucePetalParticle;
import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.biomesbf.registry.content.BBFParticles;
import net.hecco.bountifulfares.definition.particle.GoldenPetalParticle;
import net.hecco.bountifulfares.registry.content.BFBlocks;
import net.hecco.bountifulfares.registry.content.BFParticles;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;

@Environment(EnvType.CLIENT)
public class BiomesBFFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BiomesBFClient.onInitializeClient();
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.getDefaultColor(), BBFBlocks.PURPLE_POSIES.get());
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getEvergreenColor(), BBFBlocks.FLOWERING_SPRUCE_LEAVES.get());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColor.getEvergreenColor(), BBFBlocks.FLOWERING_SPRUCE_LEAVES.get());
        ParticleFactoryRegistry.getInstance().register(BBFParticles.SPRUCE_PETAL.get(), SprucePetalParticle.Factory::new);
    }
}