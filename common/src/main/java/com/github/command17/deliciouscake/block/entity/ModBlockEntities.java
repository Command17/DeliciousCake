package com.github.command17.deliciouscake.block.entity;

import com.github.command17.deliciouscake.DeliciousCake;
import com.github.command17.deliciouscake.block.ModBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public final class ModBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(DeliciousCake.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<CustomCakeBlockEntity>> CUSTOM_CAKE = REGISTRY.register("custom_cake",
            () -> BlockEntityType.Builder.of(CustomCakeBlockEntity::new,
                    ModBlocks.GLAZED_CAKE.get(),
                    ModBlocks.GOLDEN_APPLE_CAKE.get(),
                    ModBlocks.COOKIE_CAKE.get(),
                    ModBlocks.NETHER_CAKE.get()
            ).build(null));

    private static<T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> blockEntityType) {
        return REGISTRY.register(name, blockEntityType);
    }

    public static void register() {
        REGISTRY.register();
        DeliciousCake.LOGGER.info("Registered Block Entities.");
    }
}
