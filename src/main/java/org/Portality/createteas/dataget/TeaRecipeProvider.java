package org.Portality.createteas.dataget;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TeaRecipeProvider extends RecipeProvider {

    static final List<ProcessingRecipeGen> GENERATORS = new ArrayList<>();

    public TeaRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {}

    public static void registerAllProcessing(DataGenerator gen, PackOutput output) {
        GENERATORS.add(new TeaRecipeGen(output));

        gen.addProvider(true, new DataProvider() {

            @Override
            public String getName() {
                return "CreateTeas Processing Recipes";
            }

            @Override
            public CompletableFuture<?> run(CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream()
                        .map(gen -> gen.run(dc))
                        .toArray(CompletableFuture[]::new));
            }
        });
    }
}
