package org.Portality.createteas.dataget;

import com.simibubi.create.AllFluids;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import org.Portality.createteas.AllTeas;
import org.Portality.createteas.Createteas;

import java.util.function.UnaryOperator;

public class TeaRecipeGen extends MixingRecipeGen {
    public TeaRecipeGen(PackOutput output) {
        super(output, Createteas.ID);

        for(AllTeas tea : AllTeas.values()){
            if(tea.craftItem.isEmpty()){continue;}

            if(tea.craftItem.split(" ").length == 2){
                String[] craftItems = tea.craftItem.split(" ");

                Item ingridientItem = Createteas.getItemFromString(craftItems[0]);
                Item secondIngridientItem = Createteas.getItemFromString(craftItems[1]);

                create(tea.TeaName, b -> b
                        .require(AllFluids.TEA.get(), 250)
                        .require(Items.GLASS_BOTTLE)
                        .require(ingridientItem)
                        .require(secondIngridientItem)
                        .output(Createteas.getItemFromName(tea.TeaName))
                        .requiresHeat(HeatCondition.HEATED)
                );
            }

            Item ingridientItem = Createteas.getItemFromString(tea.craftItem);

            if(ingridientItem == null){
                continue;
            }

            create(tea.TeaName, b -> b
                    .require(AllFluids.TEA.get(), 250)
                    .require(ingridientItem)
                    .require(Items.GLASS_BOTTLE)
                    .output(Createteas.getItemFromName(tea.TeaName))
                    .requiresHeat(HeatCondition.HEATED)
            );
        }
    }
}
