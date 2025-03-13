package com.github.command17.deliciouscake.item;

import com.github.command17.deliciouscake.DeliciousCake;
import com.github.command17.deliciouscake.block.ModBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public final class ModItems {
    private static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(DeliciousCake.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> GLAZED_CAKE = registerSimpleCakeItem("glazed_cake", ModBlocks.GLAZED_CAKE, ofCake());
    public static final RegistrySupplier<Item> GOLDEN_APPLE_CAKE = registerSimpleCakeItem("golden_apple_cake", ModBlocks.GOLDEN_APPLE_CAKE, ofCake());
    public static final RegistrySupplier<Item> COOKIE_CAKE = registerSimpleCakeItem("cookie_cake", ModBlocks.COOKIE_CAKE, ofCake());
    public static final RegistrySupplier<Item> NETHER_CAKE = registerSimpleCakeItem("nether_cake", ModBlocks.NETHER_CAKE, ofCake());

    private static RegistrySupplier<Item> registerSimpleCakeItem(String name, Supplier<Block> block, Item.Properties properties) {
        return register(name, () -> new BlockItem(block.get(), properties));
    }

    private static RegistrySupplier<Item> register(String name, Supplier<Item> item) {
        return REGISTRY.register(name, item);
    }

    private static Item.Properties of() {
        return new Item.Properties();
    }

    private static Item.Properties ofCake() {
        return of().stacksTo(1);
    }

    public static void register() {
        REGISTRY.register();
        DeliciousCake.LOGGER.info("Registered Items.");
    }
}
