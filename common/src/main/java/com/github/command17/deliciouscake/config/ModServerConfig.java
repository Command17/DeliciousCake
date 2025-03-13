package com.github.command17.deliciouscake.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ModServerConfig {
    // Effects

    // Golden Apple Cake
    public final ModConfigSpec.ConfigValue<Integer> goldenAppleCakeRegenerationDuration;
    public final ModConfigSpec.ConfigValue<Integer> goldenAppleCakeRegenerationAmplifier;
    public final ModConfigSpec.ConfigValue<Integer> goldenAppleCakeAbsorptionDuration;
    public final ModConfigSpec.ConfigValue<Integer> goldenAppleCakeAbsorptionAmplifier;

    // Nether Cake
    public final ModConfigSpec.ConfigValue<Integer> netherCakeFireResistanceDuration;
    public final ModConfigSpec.ConfigValue<Integer> netherCakeFireResistanceAmplifier;

    public ModServerConfig(ModConfigSpec.Builder builder) {
        this.goldenAppleCakeRegenerationDuration = builder
                .comment("Duration of the Regeneration effect that the golden apple cake gives you (in ticks).")
                .define(key("effects", "goldenAppleCake", "regenerationDuration"), 80);

        this.goldenAppleCakeRegenerationAmplifier = builder
                .comment("Amplifier of the Regeneration effect that the golden apple cake gives you (remember that 0 is level 1, 1 is level 2 and so on).")
                .define(key("effects", "goldenAppleCake", "regenerationAmplifier"), 1);

        this.goldenAppleCakeAbsorptionDuration = builder
                .comment("Duration of the Absorption effect that the golden apple cake gives you (in ticks).")
                .define(key("effects", "goldenAppleCake", "absorptionDuration"), 250);

        this.goldenAppleCakeAbsorptionAmplifier = builder
                .comment("Amplifier of the Absorption effect that the golden apple cake gives you (remember that 0 is level 1, 1 is level 2 and so on).")
                .define(key("effects", "goldenAppleCake", "absorptionAmplifier"), 0);

        this.netherCakeFireResistanceDuration = builder
                .comment("Duration of the Fire Resistance effect that the nether cake gives you (in ticks).")
                .define(key("effects", "netherCake", "fireResistanceDuration"), 200);

        this.netherCakeFireResistanceAmplifier = builder
                .comment("Amplifier of the Fire Resistance effect that the nether cake gives you (remember that 0 is level 1, 1 is level 2 and so on).")
                .define(key("effects", "netherCake", "fireResistanceAmplifier"), 0);
    }

    private static List<String> key(String category, String field) {
        return List.of(category, field);
    }

    private static List<String> key(String category, String subcategory, String field) {
        return List.of(category, subcategory, field);
    }
}
