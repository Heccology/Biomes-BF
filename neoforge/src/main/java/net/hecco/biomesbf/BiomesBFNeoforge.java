package net.hecco.biomesbf;

import net.hecco.biomesbf.registry.content.BBFBiomes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(BiomesBF.BIOMESBF_MOD_ID)
public class BiomesBFNeoforge {

    public BiomesBFNeoforge(IEventBus eventBus) {
        eventBus.addListener(this::onCommonSetup);
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        BBFBiomes.registerBiomePlacement();
    }
}