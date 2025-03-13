package com.github.command17.deliciouscake.client;

import com.github.command17.deliciouscake.block.entity.ModBlockEntities;
import com.github.command17.deliciouscake.client.renderer.blockentity.CustomCakeBlockEntityRenderer;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static com.github.command17.deliciouscake.DeliciousCake.LOGGER;

@Environment(EnvType.CLIENT)
public final class DeliciousCakeClient {
    public static void init() {
        LOGGER.info("Initializing Client...");

        if (Platform.isFabric()) {
            registerRenderers();
        } else {
            LifecycleEvent.SETUP.register(DeliciousCakeClient::registerRenderers);
        }

        LOGGER.info("Initialized Client.");
    }

    private static void registerRenderers() {
        BlockEntityRendererRegistry.register(ModBlockEntities.CUSTOM_CAKE.get(), CustomCakeBlockEntityRenderer::new);
    }
}
