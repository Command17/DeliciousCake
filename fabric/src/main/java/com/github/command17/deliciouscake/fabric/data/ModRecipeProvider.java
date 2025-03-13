package com.github.command17.deliciouscake.fabric.data;

import com.github.command17.deliciouscake.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        simpleCakeRecipe(ModItems.GLAZED_CAKE.get(), RecipeCategory.MISC, Items.SUGAR).save(output);
        simpleCakeRecipe(ModItems.COOKIE_CAKE.get(), RecipeCategory.MISC, Items.COOKIE).save(output);
        simpleCakeRecipe(ModItems.NETHER_CAKE.get(), RecipeCategory.MISC, Items.NETHER_WART).save(output);
        cakeRecipe(ModItems.GOLDEN_APPLE_CAKE.get(), RecipeCategory.MISC, Items.GOLD_NUGGET, Items.GOLDEN_APPLE, Items.GOLD_NUGGET).save(output);
    }

    private ShapedRecipeBuilder cakeRecipe(Item output, RecipeCategory category, Item ingredient, Item ingredient2, Item ingredient3) {
        return ShapedRecipeBuilder.shaped(category, output)
                .pattern("MMM")
                .pattern("XYZ")
                .pattern("WWW")
                .define('M', Items.MILK_BUCKET)
                .define('W', Items.WHEAT)
                .define('X', ingredient)
                .define('Y', ingredient2)
                .define('Z', ingredient3)
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    private ShapedRecipeBuilder simpleCakeRecipe(Item output, RecipeCategory category, Item ingredient) {
        return cakeRecipe(output, category, ingredient, ingredient, ingredient);
    }
}
