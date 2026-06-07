package net.hecco.biomesbf;

import net.hecco.biomesbf.definition.particle.SprucePetalParticle;
import net.hecco.biomesbf.registry.content.BBFBlocks;
import net.hecco.biomesbf.registry.content.BBFParticles;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = BiomesBF.BIOMESBF_MOD_ID, value = Dist.CLIENT)
public class BiomesBFNeoforgeClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModContainer container = BiomesBFNeoforge.modContainer;
        if (container != null) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    @SubscribeEvent
    public static void blockColorSetup(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.getDefaultColor(), BBFBlocks.PURPLE_POSIES.get());
        event.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getEvergreenColor(), BBFBlocks.FLOWERING_SPRUCE_LEAVES.get());
    }
    @SubscribeEvent
    public static void itemColorSetup(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> FoliageColor.getEvergreenColor(), BBFBlocks.FLOWERING_SPRUCE_LEAVES.get(), BBFBlocks.FLOWERING_SPRUCE_LEAVES.get());
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(BBFParticles.SPRUCE_PETAL.get(), SprucePetalParticle.Factory::new);
    }
}
