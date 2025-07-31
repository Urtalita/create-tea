package org.Portality.createteas.dataget;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.Portality.createteas.Createteas;
import org.Portality.createteas.items.TeaItems;
import org.Portality.createteas.items.TeasItem;

public class TeaItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public TeaItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Createteas.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    private ItemModelBuilder simpleItem(ItemEntry<TeasItem> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Createteas.ID,"item/" + item.getId().getPath()));
    }
}
