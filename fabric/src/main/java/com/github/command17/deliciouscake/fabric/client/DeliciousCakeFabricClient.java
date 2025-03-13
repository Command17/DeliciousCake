package com.github.command17.deliciouscake.fabric.client;

import com.github.command17.deliciouscake.client.DeliciousCakeClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class DeliciousCakeFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DeliciousCakeClient.init();
    }
}
