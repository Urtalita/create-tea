package org.Portality.createteas.items;

import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import org.Portality.createteas.AllTeas;

import static org.Portality.createteas.Createteas.TEA_REGISTRATE;

public class TeaItems {

    public static void register() {
        for(AllTeas tea : AllTeas.values()){
            FoodProperties.Builder foodPropertiesBuilder = new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationMod(.6F)
                    .alwaysEat();

            for(MobEffectInstance instance : tea.mobEffectInstances){
                foodPropertiesBuilder.effect(instance, 1F);
            }

            FoodProperties foodProperties = foodPropertiesBuilder.build();

            TEA_REGISTRATE.item(tea.TeaName, TeasItem::new)
                    .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
                    .properties(p -> p
                            .stacksTo(16)
                            .food(foodProperties)).register();
        }
    }
}
