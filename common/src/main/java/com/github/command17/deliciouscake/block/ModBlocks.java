package com.github.command17.deliciouscake.block;

import com.github.command17.deliciouscake.DeliciousCake;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.List;
import java.util.function.Supplier;

public final class ModBlocks {
    private static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(DeliciousCake.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> GLAZED_CAKE = registerSimpleCake("glazed_cake", ofCake().mapColor(MapColor.COLOR_GREEN));

    public static final RegistrySupplier<Block> GOLDEN_APPLE_CAKE = registerSimpleEffectCake("golden_apple_cake",
            () -> List.of(
                    new MobEffectInstance(
                            MobEffects.REGENERATION,
                            DeliciousCake.SERVER_CONFIG.goldenAppleCakeRegenerationDuration.get(),
                            DeliciousCake.SERVER_CONFIG.goldenAppleCakeRegenerationAmplifier.get()
                    ),
                    new MobEffectInstance(
                            MobEffects.ABSORPTION,
                            DeliciousCake.SERVER_CONFIG.goldenAppleCakeAbsorptionDuration.get(),
                            DeliciousCake.SERVER_CONFIG.goldenAppleCakeAbsorptionAmplifier.get()
                    )
            ), ofCake());

    public static final RegistrySupplier<Block> NETHER_CAKE = register("nether_cake",
            () -> new NetherCakeBlock(ofCake().mapColor(MapColor.COLOR_RED)));

    public static final RegistrySupplier<Block> COOKIE_CAKE = register("cookie_cake",
            () -> new CookieCakeBlock(ofCake().mapColor(MapColor.COLOR_BROWN)));

    private static RegistrySupplier<Block> registerSimpleEffectCake(String name, Supplier<List<MobEffectInstance>> effectFactory, BlockBehaviour.Properties properties) {
        return register(name, () -> new EffectCakeBlock(effectFactory, properties));
    }

    private static RegistrySupplier<Block> registerSimpleCake(String name, BlockBehaviour.Properties properties) {
        return register(name, () -> new CustomCakeBlock(properties));
    }

    private static RegistrySupplier<Block> register(String name, Supplier<Block> block) {
        return REGISTRY.register(name, block);
    }

    private static BlockBehaviour.Properties copyOf(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }

    private static BlockBehaviour.Properties ofCake() {
        return copyOf(Blocks.CAKE).lightLevel((state) -> state.getValue(CustomCakeBlock.LIT) ? 3 : 0);
    }

    public static void register() {
        REGISTRY.register();
        DeliciousCake.LOGGER.info("Registered Blocks.");
    }
}
