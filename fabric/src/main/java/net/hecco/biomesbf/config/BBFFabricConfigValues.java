package net.hecco.biomesbf.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.hecco.biomesbf.BiomesBF;
import net.hecco.biomesbf.BiomesBFFabric;
import oshi.util.tuples.Quartet;
import oshi.util.tuples.Triplet;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BBFFabricConfigValues implements net.hecco.biomesbf.config.ConfigValues {
    public static final Map<String, Triplet<String, Boolean, Boolean>> BOOL_CONFIGS = new HashMap<>();
    public static final Map<String, Triplet<String, LimitedIntValue, Boolean>> INT_CONFIGS = new HashMap<>();
    public static final Map<String, Quartet<String, Class<? extends Enum<?>>, Enum<?>, Boolean>> ENUM_CONFIGS = new HashMap<>();

    public static final Map<String, Category> CATEGORIES = new HashMap<>();

    @Override
    public String createCategory(String title, boolean isChild, boolean isClient, ArrayList<String> values, ArrayList<String> categoryChildrenIds) {
        CATEGORIES.put(title, new Category(title, isChild, isClient, values, categoryChildrenIds));
        return title;
    }

    @Override
    public void registerBoolConfigValue(String category, String id, String translationKey, boolean defaultValue, boolean requiresGameRestart) {
        BOOL_CONFIGS.put(id, new Triplet<>(translationKey, defaultValue, requiresGameRestart));
        if (CATEGORIES.containsKey(category)) {
            CATEGORIES.get(category).putValue(id);
        }
    }

    @Override
    public void registerIntConfigValue(String category, String id, String translationKey, int minValue, int maxValue, int defaultValue, boolean requiresGameRestart) {
        INT_CONFIGS.put(id, new Triplet<>(translationKey, new LimitedIntValue(defaultValue, minValue, maxValue), requiresGameRestart));
        if (CATEGORIES.containsKey(category)) {
            CATEGORIES.get(category).putValue(id);
        }
    }

    @Override
    public <T extends Enum<T>> void registerEnumConfigValue(String category, String id, String translationKey, Class<T> enumValue, T defaultValue, boolean requiresGameRestart) {
        ENUM_CONFIGS.put(id, new Quartet<>(translationKey, enumValue, defaultValue, requiresGameRestart));
        if (CATEGORIES.containsKey(category)) {
            CATEGORIES.get(category).putValue(id);
        }
    }


    @Override
    public boolean getBoolConfigValue(String id) {
        if (BOOL_CONFIGS.containsKey(id)) {
            return BOOL_CONFIGS.get(id).getB();
        }
        return false;
    }

    @Override
    public int getIntConfigValue(String id) {
        if (INT_CONFIGS.containsKey(id)) {
            return INT_CONFIGS.get(id).getB().value();
        }
        return 1;
    }

    @Override
    public <T extends Enum<?>> T getEnumConfigValue(String id) {
        if (ENUM_CONFIGS.containsKey(id)) {
            return (T) ENUM_CONFIGS.get(id).getC();
        }
        return null;
    }

    public void setBoolConfigValue(String id, boolean newValue) {
        if (BOOL_CONFIGS.containsKey(id)) {
            var a = BOOL_CONFIGS.get(id);
            BOOL_CONFIGS.replace(id, new Triplet<>(a.getA(), newValue, a.getC()));
        }
    }

    public void setIntConfigValue(String id, int newValue) {
        if (INT_CONFIGS.containsKey(id)) {
            INT_CONFIGS.get(id).getB().setValue(newValue);
        }
    }

    public void setEnumConfigValue(String id, Enum<?> newValue) {
        if (ENUM_CONFIGS.containsKey(id)) {
            var value = ENUM_CONFIGS.get(id);
            ENUM_CONFIGS.replace(id, new Quartet<>(value.getA(), value.getB(), newValue, value.getD()));
        }
    }


    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "biomesbf.json");

    public static BBFFabricConfigValues load() {
        BBFFabricConfigValues configuration = new BBFFabricConfigValues();

        if (!CONFIG_FILE.exists()) {
            save();
            return configuration;
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_FILE.toPath())) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            for (var entry : BOOL_CONFIGS.entrySet()) {
                String id = entry.getKey();
                if (root.has(id)) {
                    configuration.setBoolConfigValue(id, root.get(id).getAsBoolean());
                }
            }

            for (var entry : INT_CONFIGS.entrySet()) {
                String id = entry.getKey();
                if (root.has(id)) {
                    configuration.setIntConfigValue(id, root.get(id).getAsInt());
                }
            }

            for (var entry : ENUM_CONFIGS.entrySet()) {
                String id = entry.getKey();
                if (root.has(id)) {
                    var data = entry.getValue();
                    String name = root.get(id).getAsString();
                    try {
                        Enum<?> value = Enum.valueOf((Class) data.getB(), name);
                        configuration.setEnumConfigValue(id, value);
                    } catch (IllegalArgumentException e) {
                        BiomesBF.LOGGER.warn("Invalid enum value '{}' for config '{}', using default.", name, id);
                        configuration.setEnumConfigValue(id, data.getC());
                    }
                }
            }
        } catch (Exception e) {
            BiomesBF.LOGGER.error("Error while trying to load configuration file", e);
        }

        BiomesBFFabric.CONFIG.triggerConfigUpdateListeners();

        return configuration;
    }

    public static void save() {
        JsonObject root = new JsonObject();

        for (var entry : BOOL_CONFIGS.entrySet()) {
            root.addProperty(entry.getKey(), entry.getValue().getB());
        }

        for (var entry : INT_CONFIGS.entrySet()) {
            root.addProperty(entry.getKey(), entry.getValue().getB().value());
        }

        for (var entry : ENUM_CONFIGS.entrySet()) {
            root.addProperty(entry.getKey(), entry.getValue().getC().name());
        }

        try (Writer writer = Files.newBufferedWriter(CONFIG_FILE.toPath())) {
            new GsonBuilder().setPrettyPrinting().create().toJson(root, writer);
        } catch (IOException e) {
            BiomesBF.LOGGER.error("Error while trying to save configuration file", e);
        }

        BiomesBFFabric.CONFIG.triggerConfigUpdateListeners();
    }
}
