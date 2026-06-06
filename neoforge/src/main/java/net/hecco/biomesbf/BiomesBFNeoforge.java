package net.hecco.biomesbf;

import net.hecco.biomesbf.config.BBFNeoforgeConfigValues;
import net.hecco.biomesbf.registry.BBFBiomeModifiers;
import net.hecco.biomesbf.registry.content.BBFBiomes;
import net.hecco.biomesbf.registry.misc.BBFConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(BiomesBF.BIOMESBF_MOD_ID)
public class BiomesBFNeoforge {
    public static ModContainer modContainer;

    public BiomesBFNeoforge(IEventBus eventBus, ModContainer container) {
        modContainer = container;

        BBFConfiguration.register();
        BBFNeoforgeConfigValues.register(container);

        BiomesBF.init();

        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::onCommonSetup);

        BBFBiomeModifiers.BIOME_MODIFIERS.register(eventBus);
    }


    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event) {
        BiomesBFClient.onInitializeClient();
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        BBFBiomes.registerBiomePlacement();
    }
}