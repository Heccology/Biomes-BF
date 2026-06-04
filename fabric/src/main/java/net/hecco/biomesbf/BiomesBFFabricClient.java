package net.hecco.biomesbf;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BiomesBFFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BiomesBFClient.onInitializeClient();
    }
}