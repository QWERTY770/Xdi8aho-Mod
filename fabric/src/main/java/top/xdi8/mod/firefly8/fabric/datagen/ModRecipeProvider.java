package top.xdi8.mod.firefly8.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import top.xdi8.mod.firefly8.ModDataGen;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput recipeOutput) {
        return new ModRecipeProviderInner(provider, recipeOutput);
    }

    @Override
    public @NotNull String getName() {
        return "Firefly8 Recipes";
    }

    private static class ModRecipeProviderInner extends RecipeProvider {
        private ModRecipeProviderInner(HolderLookup.Provider provider, RecipeOutput output) {
            super(provider, output);
        }

        @Override
        public void buildRecipes() {
            buildRecipes(ModDataGen.REDWOOD_FAMILY);
            buildRecipes(ModDataGen.SYMBOL_STONE_FAMILY);
            this.woodenBoat(FireflyItems.CEDAR_BOAT.get(), FireflyBlocks.CEDAR_PLANKS.get());
            this.chestBoat(FireflyItems.CEDAR_CHEST_BOAT.get(), FireflyBlocks.CEDAR_PLANKS.get());
            this.hangingSignBuilder(FireflyItems.CEDAR_HANGING_SIGN.get(), Ingredient.of(FireflyBlocks.STRIPPED_CEDAR_LOG.get()));
            this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FireflyBlocks.SYMBOL_STONE_BRICK_SLAB.get(), FireflyBlocks.SYMBOL_STONE_BRICKS.get(), 2);
            this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FireflyBlocks.SYMBOL_STONE_BRICK_STAIRS.get(), FireflyBlocks.SYMBOL_STONE_BRICKS.get());
        }

        public void buildRecipes(BlockFamily blockFamily) {
            this.generateRecipes(blockFamily, FeatureFlags.VANILLA_SET);
        }
    }
}
