package org.Portality.createteas;

import com.mojang.logging.LogUtils;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.infrastructure.data.CreateDatagen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.Portality.createteas.dataget.CreateTeasDatagen;
import org.Portality.createteas.items.TeaActions;
import org.Portality.createteas.items.TeaDrinkingActions;
import org.Portality.createteas.items.TeaItems;
import org.Portality.createteas.items.TeasTab;
import org.slf4j.Logger;

import static org.Portality.createteas.items.TeasTab.TEAS_TAB;

@Mod(Createteas.ID)
public class Createteas {

    public static final String ID = "createteas";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate TEA_REGISTRATE = CreateRegistrate.create(Createteas.ID);

    public Createteas() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        TEA_REGISTRATE.registerEventListeners(modEventBus);

        TeaItems.register();
        TeasTab.register(modEventBus);
        TeaActions.register();
        TeaDrinkingActions.register();
    }

    public static ItemStack getItemFromName(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            return null;
        }

        itemName = ID + ":" + itemName;

        ResourceLocation resLoc = ResourceLocation.tryParse(itemName);

        if (resLoc == null) {
            return null;
        }

        return new ItemStack(ForgeRegistries.ITEMS.getValue(resLoc));
    }

    public static Item getCreateItemFromName(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            return null;
        }

        itemName = Create.ID + ":" + itemName;

        ResourceLocation resLoc = ResourceLocation.tryParse(itemName);

        if (resLoc == null) {
            return null;
        }

        ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(resLoc));
        return stack.getItem();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID,  path);
    }
}
