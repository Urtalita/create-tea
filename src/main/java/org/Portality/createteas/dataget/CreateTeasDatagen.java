package org.Portality.createteas.dataget;

import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.Portality.createteas.Createteas;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Createteas.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreateTeasDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput output = generator.getPackOutput();

        if (event.includeServer()) {
            TeaRecipeProvider.registerAllProcessing(generator, output);
        }
    }
}
