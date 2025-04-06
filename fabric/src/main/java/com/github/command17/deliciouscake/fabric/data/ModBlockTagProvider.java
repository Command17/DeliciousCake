package com.github.command17.deliciouscake.fabric.data;

import com.github.command17.deliciouscake.block.ModBlocks;
import com.github.command17.deliciouscake.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ModTags.BlockTags.CAKE)
                .add(ModBlocks.GLAZED_CAKE.get())
                .add(ModBlocks.GOLDEN_APPLE_CAKE.get())
                .add(ModBlocks.COOKIE_CAKE.get())
                .add(ModBlocks.NETHER_CAKE.get());
    }
}
