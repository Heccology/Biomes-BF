package net.hecco.biomesbf.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.SubCategoryListEntry;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.hecco.biomesbf.BiomesBFFabric;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import oshi.util.tuples.Quartet;

import java.util.ArrayList;
import java.util.List;

public class BBFModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return this::buildConfigScreen;
    }

    private Screen buildConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setSavingRunnable(BBFFabricConfigValues::save)
                .setTitle(Component.translatable("biomesbf.configuration.title"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        BBFFabricConfigValues.CATEGORIES.values().stream().filter(category -> !category.isChild()).forEach(category -> buildCategory(builder, entryBuilder, category));
        return builder.build();
    }

    private void buildCategory(ConfigBuilder builder, ConfigEntryBuilder entryBuilder, Category category) {
        ConfigCategory configCategory = builder.getOrCreateCategory(Component.translatable("biomesbf.configuration." + category.title()));
        List<Entry<?>> entries = new ArrayList<>();
        for (String id : category.values()) {
            buildEntries(id, entries);
        }

        List<Category> children = new ArrayList<>();
        for (String id : category.categoryChildrenIds()) {
            if (BBFFabricConfigValues.CATEGORIES.containsKey(id)) {
                children.add(BBFFabricConfigValues.CATEGORIES.get(id));
            }
        }
        entries.forEach(entry -> {
            if (entry != null) {
            configCategory.addEntry(entry.build(entryBuilder));
            }
        });
        children.forEach(entry -> configCategory.addEntry(buildSubCategory(entryBuilder.startSubCategory(Component.translatable("biomesbf.configuration." + entry.title())), entryBuilder, entry)));

    }

    private static void buildEntries(String id, List<Entry<?>> entries) {
        if (BBFFabricConfigValues.BOOL_CONFIGS.containsKey(id)) {
            var value = BBFFabricConfigValues.BOOL_CONFIGS.get(id);
            String tooltip = "";
            if (value.getC()) {
                tooltip = "biomesbf.configuration.restart_warning";
            }
            entries.add(Entry.booleanEntry("biomesbf.configuration." + value.getA(), () -> BiomesBFFabric.CONFIG.getBoolConfigValue(id), newValue -> BiomesBFFabric.CONFIG.setBoolConfigValue(id, newValue), value.getB(), tooltip));
        }
        if (BBFFabricConfigValues.INT_CONFIGS.containsKey(id)) {
            var value = BBFFabricConfigValues.INT_CONFIGS.get(id);
            String tooltip = "";
            if (value.getC()) {
                tooltip = "biomesbf.configuration.restart_warning";
            }
            entries.add(Entry.integerEntry("biomesbf.configuration." + value.getA(), () -> BiomesBFFabric.CONFIG.getIntConfigValue(id), newValue -> BiomesBFFabric.CONFIG.setIntConfigValue(id, newValue), value.getB().defaultValue, value.getB().min, value.getB().max, tooltip));
        }
        if (BBFFabricConfigValues.ENUM_CONFIGS.containsKey(id)) {
            var value = BBFFabricConfigValues.ENUM_CONFIGS.get(id);
            String tooltip = "";
            if (value.getD()) {
                tooltip = "biomesbf.configuration.restart_warning";
            }
            addEnumEntry(entries, id, value, tooltip);
        }
    }

    private static void addEnumEntry(List<Entry<?>> entries, String id, Quartet<String, Class<? extends Enum<?>>, Enum<?>, Boolean> value, String tooltip) {
        Class enumClass = value.getB();
        Enum defaultValue = value.getC();

        entries.add(Entry.enumEntry(
                "biomesbf.configuration." + value.getA(),
                () -> BiomesBFFabric.CONFIG.getEnumConfigValue(id),
                newValue -> BiomesBFFabric.CONFIG.setEnumConfigValue(id, newValue),
                enumClass,
                defaultValue,
                tooltip
        ));
    }

    private SubCategoryListEntry buildSubCategory(SubCategoryBuilder subCategoryBuilder, ConfigEntryBuilder entryBuilder, Category category) {
        List<Entry<?>> entries = new ArrayList<>();
        for (String id : category.values()) {
            buildEntries(id, entries);
        }
        entries.forEach(entry -> {
            if (entry != null) {
                subCategoryBuilder.add(entry.build(entryBuilder));
            }
        });
        List<Category> children = new ArrayList<>();
        for (String id : category.categoryChildrenIds()) {
            if (BBFFabricConfigValues.CATEGORIES.containsKey(id)) {
                children.add(BBFFabricConfigValues.CATEGORIES.get(id));
            }
        }
        children.forEach(entry -> subCategoryBuilder.add(buildSubCategory(entryBuilder.startSubCategory(Component.translatable("biomesbf.configuration." + entry.title())), entryBuilder, entry)));
        return subCategoryBuilder.build();
    }
}