package com.github.command17.deliciouscake.fabric.data;

import com.github.command17.deliciouscake.block.ModBlocks;
import com.github.command17.deliciouscake.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        basicCake(ModBlocks.GLAZED_CAKE.get(), generator);
        basicCake(ModBlocks.GOLDEN_APPLE_CAKE.get(), generator);
        basicCake(ModBlocks.COOKIE_CAKE.get(), generator);
        basicCake(ModBlocks.NETHER_CAKE.get(), generator);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        simpleItem(ModItems.GLAZED_CAKE.get(), generator);
        simpleItem(ModItems.GOLDEN_APPLE_CAKE.get(), generator);
        simpleItem(ModItems.COOKIE_CAKE.get(), generator);
        simpleItem(ModItems.NETHER_CAKE.get(), generator);
    }

    private void simpleItem(Item item, ItemModelGenerators generator) {
        generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void basicCake(Block cake, BlockModelGenerators generator) {
        TextureMapping textures = new TextureMapping()
                .put(TextureSlot.BOTTOM, ResourceLocation.withDefaultNamespace("block/cake_bottom"))
                .put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(cake, "_side"))
                .put(TextureSlot.TOP, ModelLocationUtils.getModelLocation(cake, "_top"))
                .put(TextureSlot.INSIDE, ModelLocationUtils.getModelLocation(cake, "_inner"))
                .put(TextureSlot.PARTICLE, ModelLocationUtils.getModelLocation(cake, "_inner"));

        ResourceLocation base = ModModelTemplates.CAKE.create(cake, textures, generator.modelOutput);
        ResourceLocation slice1 = ModModelTemplates.CAKE_SLICE1.create(cake, textures, generator.modelOutput);
        ResourceLocation slice2 = ModModelTemplates.CAKE_SLICE2.create(cake, textures, generator.modelOutput);
        ResourceLocation slice3 = ModModelTemplates.CAKE_SLICE3.create(cake, textures, generator.modelOutput);
        ResourceLocation slice4 = ModModelTemplates.CAKE_SLICE4.create(cake, textures, generator.modelOutput);
        ResourceLocation slice5 = ModModelTemplates.CAKE_SLICE5.create(cake, textures, generator.modelOutput);
        ResourceLocation slice6 = ModModelTemplates.CAKE_SLICE6.create(cake, textures, generator.modelOutput);

        var blockStates = PropertyDispatch.property(BlockStateProperties.BITES)
                        .select(0, Variant.variant().with(VariantProperties.MODEL, base))
                        .select(1, Variant.variant().with(VariantProperties.MODEL, slice1))
                        .select(2, Variant.variant().with(VariantProperties.MODEL, slice2))
                        .select(3, Variant.variant().with(VariantProperties.MODEL, slice3))
                        .select(4, Variant.variant().with(VariantProperties.MODEL, slice4))
                        .select(5, Variant.variant().with(VariantProperties.MODEL, slice5))
                        .select(6, Variant.variant().with(VariantProperties.MODEL, slice6));

        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(cake).with(blockStates));
    }

    public static final class ModModelTemplates {
        public static ModelTemplate CAKE = ofVanillaBlock("cake", TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE);
        public static ModelTemplate CAKE_SLICE1 = ofVanillaBlock("cake_slice1", "_slice1", TextureSlot.BOTTOM, TextureSlot.INSIDE, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE);
        public static ModelTemplate CAKE_SLICE2 = ofVanillaBlock("cake_slice2", "_slice2", TextureSlot.BOTTOM, TextureSlot.INSIDE, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE);
        public static ModelTemplate CAKE_SLICE3 = ofVanillaBlock("cake_slice3", "_slice3", TextureSlot.BOTTOM, TextureSlot.INSIDE, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE);
        public static ModelTemplate CAKE_SLICE4 = ofVanillaBlock("cake_slice4", "_slice4", TextureSlot.BOTTOM, TextureSlot.INSIDE, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE);
        public static ModelTemplate CAKE_SLICE5 = ofVanillaBlock("cake_slice5", "_slice5", TextureSlot.BOTTOM, TextureSlot.INSIDE, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE);
        public static ModelTemplate CAKE_SLICE6 = ofVanillaBlock("cake_slice6", "_slice6", TextureSlot.BOTTOM, TextureSlot.INSIDE, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE);

        private static ModelTemplate ofVanillaBlock(String modelLocation, TextureSlot... textureSlots) {
            return new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("block/" + modelLocation)), Optional.empty(), textureSlots);
        }

        private static ModelTemplate ofVanillaBlock(String modelLocation, String suffix, TextureSlot... textureSlots) {
            return new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("block/" + modelLocation)), Optional.of(suffix), textureSlots);
        }
    }
}
