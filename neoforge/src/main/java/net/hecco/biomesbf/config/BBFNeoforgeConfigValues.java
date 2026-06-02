package net.hecco.biomesbf.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import oshi.util.tuples.Quartet;
import oshi.util.tuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BBFNeoforgeConfigValues implements ConfigValues {

    private static final Map<String, Triplet<String, Boolean, Boolean>> BOOL_ENTRIES = new HashMap<>();
    private static final Map<String, Triplet<String, LimitedIntValue, Boolean>> INT_ENTRIES = new HashMap<>();
    private static final Map<String, Quartet<String, Class<? extends Enum<?>>, Enum<?>, Boolean>> ENUM_ENTRIES = new HashMap<>();

    private static final Map<String, ModConfigSpec.BooleanValue> BOOL_CONFIGS = new HashMap<>();
    private static final Map<String, ModConfigSpec.IntValue> INT_CONFIGS = new HashMap<>();
    private static final Map<String, ModConfigSpec.EnumValue<?>> ENUM_CONFIGS = new HashMap<>();

    public static final Map<String, Category> COMMON_CATEGORIES = new HashMap<>();
    public static final Map<String, Category> CLIENT_CATEGORIES = new HashMap<>();

    @Override
    public String createCategory(String title, boolean isChild, boolean isClient, ArrayList<String> values, ArrayList<String> categoryChildrenIds) {
        if (isClient) {
            CLIENT_CATEGORIES.put(title, new Category(title, isChild, isClient, values, categoryChildrenIds));
        } else {
            COMMON_CATEGORIES.put(title, new Category(title, isChild, isClient, values, categoryChildrenIds));
        }
        return title;
    }

    @Override
    public void registerBoolConfigValue(String category, String id, String translationKey, boolean defaultValue, boolean requiresGameRestart) {
        BOOL_ENTRIES.put(id, new Triplet<>(translationKey, defaultValue, requiresGameRestart));
        if (CLIENT_CATEGORIES.containsKey(category)) {
            CLIENT_CATEGORIES.get(category).putValue(id);
        } else if (COMMON_CATEGORIES.containsKey(category)) {
            COMMON_CATEGORIES.get(category).putValue(id);
        }
    }

    @Override
    public void registerIntConfigValue(String category, String id, String translationKey, int minValue, int maxValue, int defaultValue, boolean requiresGameRestart) {
        INT_ENTRIES.put(id, new Triplet<>(translationKey, new LimitedIntValue(defaultValue, minValue, maxValue), requiresGameRestart));
        if (CLIENT_CATEGORIES.containsKey(category)) {
            CLIENT_CATEGORIES.get(category).putValue(id);
        } else if (COMMON_CATEGORIES.containsKey(category)) {
            COMMON_CATEGORIES.get(category).putValue(id);
        }
    }

    @Override
    public <T extends Enum<T>> void registerEnumConfigValue(String category, String id, String translationKey, Class<T> enumValue, T defaultValue, boolean requiresGameRestart) {
        ENUM_ENTRIES.put(id, new Quartet<>(translationKey, enumValue, defaultValue, requiresGameRestart));
        if (CLIENT_CATEGORIES.containsKey(category)) {
            CLIENT_CATEGORIES.get(category).putValue(id);
        } else if (COMMON_CATEGORIES.containsKey(category)) {
            COMMON_CATEGORIES.get(category).putValue(id);
        }
    }

    @Override
    public boolean getBoolConfigValue(String id) {
        if (!BOOL_CONFIGS.containsKey(id)) {
            return false;
        }
        return BOOL_CONFIGS.get(id).getAsBoolean();
    }

    @Override
    public int getIntConfigValue(String id) {
        if (!INT_CONFIGS.containsKey(id)) {
            return 1;
        }
        return INT_CONFIGS.get(id).getAsInt();
    }

    @Override
    public <T extends Enum<?>> T getEnumConfigValue(String id) {
        if (ENUM_CONFIGS.containsKey(id)) {
            return (T) ENUM_CONFIGS.get(id).get();
        }
        return null;
    }

    @Override
    public void registerConfigUpdateListener(Runnable listener) {

    }

    @Override
    public void triggerConfigUpdateListeners() {

    }

    public static ModConfigSpec COMMON_SPEC;
    public static ModConfigSpec CLIENT_SPEC;

    public static void register(ModContainer container) {
        COMMON_SPEC = new ModConfigSpec.Builder()
                .configure(builder -> {
                    COMMON_CATEGORIES.forEach((i, category) -> {
                        if (!category.isChild()) {
                            var entries = category.values();
                            entries.forEach(id -> buildEntry(builder, id));

                            for (String childId : category.categoryChildrenIds()) {
                                Category child = (category.isClient() ? CLIENT_CATEGORIES : COMMON_CATEGORIES).get(childId);
                                if (child != null) {
                                    buildCategory(builder, child);
                                }
                            }
                        }
                    });
                    return null;
                }).getRight();

        CLIENT_SPEC = new ModConfigSpec.Builder()
                .configure(builder -> {
                    CLIENT_CATEGORIES.forEach((i, category) -> {
                        if (!category.isChild()) {
                            var entries = category.values();
                            entries.forEach(id -> buildEntry(builder, id));

                            for (String childId : category.categoryChildrenIds()) {
                                Category child = (category.isClient() ? CLIENT_CATEGORIES : COMMON_CATEGORIES).get(childId);
                                if (child != null) {
                                    buildCategory(builder, child);
                                }
                            }
                        }
                    });
//                    CLIENT_CONFIGS.forEach(id -> {
//                        if (BOOL_ENTRIES.containsKey(id)) {
//                            var entry = BOOL_ENTRIES.get(id);
//                            BOOL_CONFIGS.put(id, builder.define(entry.getA(), entry::getB));
//                        }
//
//                        if (INT_ENTRIES.containsKey(id)) {
//                            var entry = INT_ENTRIES.get(id);
//                            INT_CONFIGS.put(id, builder.defineInRange(entry.getA(), entry.getB().defaultValue, entry.getB().min, entry.getB().max));
//                        }
//                    });
                    return null;
                }).getRight();
        container.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
    }

    private static void buildCategory(ModConfigSpec.Builder builder, Category category) {
        builder.push(category.title());
        var entries = category.values();
        entries.forEach(id -> buildEntry(builder, id));

        for (String childId : category.categoryChildrenIds()) {
            Category child = (category.isClient() ? CLIENT_CATEGORIES : COMMON_CATEGORIES).get(childId);
            if (child != null) {
                buildCategory(builder, child);
            }
        }
        builder.pop();
    }

    private static void buildEntry(ModConfigSpec.Builder builder, String value) {
        if (BOOL_ENTRIES.containsKey(value)) {
            var entry = BOOL_ENTRIES.get(value);
            if (entry.getC()) {
                BOOL_CONFIGS.put(value, builder.gameRestart().define(entry.getA(), entry::getB));
            } else {
                BOOL_CONFIGS.put(value, builder.define(entry.getA(), entry::getB));
            }
        } else if (INT_ENTRIES.containsKey(value)) {
            var entry = INT_ENTRIES.get(value);
            if (entry.getC()) {
                INT_CONFIGS.put(value, builder.gameRestart().defineInRange(entry.getA(), entry.getB().defaultValue, entry.getB().min, entry.getB().max));
            } else {
                INT_CONFIGS.put(value, builder.defineInRange(entry.getA(), entry.getB().defaultValue, entry.getB().min, entry.getB().max));
            }
        } else if (ENUM_ENTRIES.containsKey(value)) {
            var entry = ENUM_ENTRIES.get(value);
            if (entry.getD()) {
                ENUM_CONFIGS.put(value, defineEnumValue(builder, entry.getA(), entry.getC(), entry.getB(), true));
            } else {
                ENUM_CONFIGS.put(value, defineEnumValue(builder, entry.getA(), entry.getC(), entry.getB(), false));
            }
        }
    }

    @SuppressWarnings({"unchecked"})
    private static <V extends Enum<V>> ModConfigSpec.EnumValue<V> defineEnumValue(ModConfigSpec.Builder builder, String path, Enum<?> defaultValue, Class<? extends Enum<?>> enumClass, boolean restart) {
        if (restart) {
            return builder.gameRestart().defineEnum(path, (V) defaultValue, (V[]) enumClass.getEnumConstants());
        }
        return builder.defineEnum(path, (V) defaultValue, (V[]) enumClass.getEnumConstants());
    }
}