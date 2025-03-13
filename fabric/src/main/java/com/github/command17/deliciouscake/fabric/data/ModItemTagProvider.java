package com.github.command17.deliciouscake.fabric.data;

import com.github.command17.deliciouscake.item.ModItems;
import com.github.command17.deliciouscake.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ModTags.ItemTags.FOODS)
                .add(ModItems.GLAZED_CAKE.get())
                .add(ModItems.GOLDEN_APPLE_CAKE.get())
                .add(ModItems.COOKIE_CAKE.get())
                .add(ModItems.NETHER_CAKE.get());

        getOrCreateTagBuilder(ModTags.ItemTags.FOODS_EDIBLE_WHEN_PLACES)
                .add(ModItems.GLAZED_CAKE.get())
                .add(ModItems.GOLDEN_APPLE_CAKE.get())
                .add(ModItems.COOKIE_CAKE.get())
                .add(ModItems.NETHER_CAKE.get());
    }
}
