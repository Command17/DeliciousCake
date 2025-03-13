package com.github.command17.deliciouscake.item.tab;

import com.github.command17.deliciouscake.DeliciousCake;
import com.github.command17.deliciouscake.item.ModItems;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(DeliciousCake.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MAIN = REGISTRY.register("main",
            () -> CreativeTabRegistry.create((builder) -> builder
                    .title(Component.translatable("itemGroup." + DeliciousCake.MOD_ID + ".main"))
                    .icon(() -> new ItemStack(ModItems.GLAZED_CAKE.get()))
                    .displayItems((display, output) -> {
                            if (!DeliciousCake.CONFIG.showTab.get()) {
                                return;
                            }

                            output.accept(ModItems.GLAZED_CAKE.get());
                            output.accept(ModItems.GOLDEN_APPLE_CAKE.get());
                            output.accept(ModItems.COOKIE_CAKE.get());
                            output.accept(ModItems.NETHER_CAKE.get());
                    })));

    public static void register() {
        REGISTRY.register();
        DeliciousCake.LOGGER.info("Registered Creative Mode Tabs.");
    }
}
