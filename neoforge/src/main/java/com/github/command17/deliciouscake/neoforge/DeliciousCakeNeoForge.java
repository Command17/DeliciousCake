package com.github.command17.deliciouscake.neoforge;

import com.github.command17.deliciouscake.DeliciousCake;
import com.github.command17.deliciouscake.client.DeliciousCakeClient;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(DeliciousCake.MOD_ID)
public final class DeliciousCakeNeoForge {
    public DeliciousCakeNeoForge(ModContainer container) {
        container.registerConfig(ModConfig.Type.STARTUP, DeliciousCake.CONFIG_SPEC);
        container.registerConfig(ModConfig.Type.SERVER, DeliciousCake.SERVER_CONFIG_SPEC);
        DeliciousCake.init();
        EnvExecutor.runInEnv(Env.CLIENT, () -> DeliciousCakeClient::init);
    }
}
