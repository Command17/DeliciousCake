package com.github.command17.deliciouscake.util;

import com.github.command17.deliciouscake.DeliciousCake;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ModTags {
    private ModTags() {}

    public static class ItemTags {
        private ItemTags() {}

        public static final TagKey<Item> FOODS = conventionTag("foods");
        public static final TagKey<Item> FOODS_EDIBLE_WHEN_PLACES = conventionTag("foods/edible_when_placed");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, DeliciousCake.resource(name));
        }

        private static TagKey<Item> conventionTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }

    public static class BlockTags {
        private BlockTags() {}

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, DeliciousCake.resource(name));
        }

        private static TagKey<Block> conventionTag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }
}
