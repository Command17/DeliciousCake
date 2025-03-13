package com.github.command17.deliciouscake.fabric;

import com.github.command17.deliciouscake.DeliciousCake;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;

public final class DeliciousCakeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(DeliciousCake.MOD_ID, ModConfig.Type.STARTUP, DeliciousCake.CONFIG_SPEC);
        NeoForgeConfigRegistry.INSTANCE.register(DeliciousCake.MOD_ID, ModConfig.Type.SERVER, DeliciousCake.SERVER_CONFIG_SPEC);
        DeliciousCake.init();
    }
}
