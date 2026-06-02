package net.hecco.biomesbf.platform;

import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.config.ConfigValues;
import net.hecco.biomesbf.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static final ConfigValues CONFIG = load(ConfigValues.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        BiomesBF.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}