package com.github.command17.deliciouscake.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ModConfig {
    // General
    public final ModConfigSpec.ConfigValue<Boolean> showTab;

    public ModConfig(ModConfigSpec.Builder builder) {
        this.showTab = builder
                .comment("If true, sorts all the mod's items into a creative tab.")
                .define(key("general", "showTab"), false);
    }

    private static List<String> key(String category, String field) {
        return List.of(category, field);
    }

    private static List<String> key(String category, String subcategory, String field) {
        return List.of(category, subcategory, field);
    }
}
