package net.hecco.biomesbf.config;

import java.util.ArrayList;
import java.util.List;

public interface ConfigValues {
    String createCategory(String title, boolean isChild, boolean isClient, ArrayList<String> values, ArrayList<String> categoryChildrenIds);

    void registerBoolConfigValue(String category, String id, String translationKey, boolean defaultValue, boolean requiresGameRestart);

    void registerIntConfigValue(String category, String id, String translationKey, int minValue, int maxValue, int defaultValue, boolean requiresGameRestart);

    <T extends Enum<T>> void registerEnumConfigValue(String category, String id, String translationKey, Class<T> enumValue, T defaultValue, boolean requiresGameRestart);

    boolean getBoolConfigValue(String id);

    int getIntConfigValue(String id);

    <T extends Enum<?>> T getEnumConfigValue(String id);

    List<Runnable> CONFIG_UPDATE_LISTENERS = new ArrayList<>();

    default void registerConfigUpdateListener(Runnable listener) {
        CONFIG_UPDATE_LISTENERS.add(listener);
    }

    default void triggerConfigUpdateListeners() {
        CONFIG_UPDATE_LISTENERS.forEach(Runnable::run);
    }
}
