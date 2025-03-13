package com.github.command17.deliciouscake;

import com.github.command17.deliciouscake.block.ModBlocks;
import com.github.command17.deliciouscake.block.entity.ModBlockEntities;
import com.github.command17.deliciouscake.config.ModConfig;
import com.github.command17.deliciouscake.config.ModServerConfig;
import com.github.command17.deliciouscake.item.ModItems;
import com.github.command17.deliciouscake.item.tab.ModCreativeModeTabs;
import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class DeliciousCake {
    public static final String MOD_ID = "deliciouscake";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ModConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;
    public static final ModServerConfig SERVER_CONFIG;
    public static final ModConfigSpec SERVER_CONFIG_SPEC;

    /*
    TODO: IDEAS (very fun)
    - Redstone Cake
    - Glowstone Cake
     */
    public static void init() {
        LOGGER.info("Initializing...");

        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();
        ModCreativeModeTabs.register();
        buildCreativeTabContent();

        LOGGER.info("Initialized.");
    }

    private static void buildCreativeTabContent() {
        var foods = CreativeTabRegistry.defer(CreativeModeTabs.FOOD_AND_DRINKS);

        if (CONFIG.showTab.get()) {
            return;
        }

        // Foods
        CreativeTabRegistry.modify(foods, (f, output, b) -> {
            output.acceptAllAfter(Items.CAKE, List.of(
                    new ItemStack(ModItems.GLAZED_CAKE.get()),
                    new ItemStack(ModItems.GOLDEN_APPLE_CAKE.get()),
                    new ItemStack(ModItems.COOKIE_CAKE.get()),
                    new ItemStack(ModItems.NETHER_CAKE.get())
            ).reversed());
        });

        if (Platform.isFabric()) {
            var search = CreativeTabRegistry.defer(CreativeModeTabs.SEARCH);

            // Search
            CreativeTabRegistry.modify(search, (f, output, b) -> {
                output.acceptAllAfter(Items.CAKE, List.of(
                        new ItemStack(ModItems.GLAZED_CAKE.get()),
                        new ItemStack(ModItems.GOLDEN_APPLE_CAKE.get()),
                        new ItemStack(ModItems.COOKIE_CAKE.get()),
                        new ItemStack(ModItems.NETHER_CAKE.get())
                ).reversed());
            });
        }
    }

    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    static {
        var configPair = new ModConfigSpec.Builder().configure(ModConfig::new);
        CONFIG = configPair.getKey();
        CONFIG_SPEC = configPair.getValue();
        var serverConfigPair = new ModConfigSpec.Builder().configure(ModServerConfig::new);
        SERVER_CONFIG = serverConfigPair.getKey();
        SERVER_CONFIG_SPEC = serverConfigPair.getValue();
    }
}
