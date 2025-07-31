package org.Portality.createteas.items;

import com.simibubi.create.AllItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.Portality.createteas.AllTeas;
import org.Portality.createteas.Createteas;

import static org.Portality.createteas.Createteas.TEA_REGISTRATE;
import static org.Portality.createteas.Createteas.getItemFromName;

public class TeasTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Createteas.ID);

    public static final RegistryObject<CreativeModeTab> TEAS_TAB = CREATIVE_MODE_TAB.register("teas_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AllItems.BUILDERS_TEA.get()))
                    .title(Component.translatable("creativetab.teas_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        for(AllTeas tea : AllTeas.values()){
                            ItemStack stack = getItemFromName(tea.TeaName);
                            if(stack != null){
                                pOutput.accept(stack);
                            }
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
