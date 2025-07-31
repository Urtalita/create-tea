package org.Portality.createteas.items;

import com.simibubi.create.content.equipment.BuildersTeaItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

public class TeasItem extends BuildersTeaItem {
    public TeasItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {

        String name = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
        Function<TeaInfo, Void> action = TeaActions.allTeaActions.get(name);

        if(action != null){
            action.apply(new TeaInfo(level, stack, livingEntity, 0));
        }

        stack.setTag(new CompoundTag());
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int time_remaining) {
        Function<TeaInfo, Void> action = TeaDrinkingActions.allDrinkingTeaActions.get(ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());

        if(action != null){
            action.apply(new TeaInfo(level, stack, entity, time_remaining));
        }

        super.onUseTick(level, entity, stack, time_remaining);
    }
}
