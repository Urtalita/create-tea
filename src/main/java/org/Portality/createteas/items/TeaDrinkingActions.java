package org.Portality.createteas.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.Portality.createteas.AllTeas;
import org.Portality.createteas.Createteas;

import java.util.HashMap;
import java.util.function.Function;

import static org.Portality.createteas.Createteas.getCreateItemFromName;

public class TeaDrinkingActions {
    public static HashMap<String, Function<TeaInfo, Void>> allDrinkingTeaActions = new HashMap<>();

    public static Function<TeaInfo, Void> portalTea(){
        return limitedType((info -> {
            Level level = info.level;
            LivingEntity livingEntity = info.entity;
            ItemStack stack = info.stack;

            Function<TeaInfo, Void> action = getTeaAction(AllTeas.PORTAL_TEA);
            if(action == null){return null;}
            action.apply(new TeaInfo(level, stack, livingEntity, 0));

            return null;
        }), 10);
    }

    public static Function<TeaInfo, Void> kotarbuzTea(){
        return limitedType((info -> {
            Level level = info.level;
            LivingEntity entity = info.entity;

            additionalTeaFunctions.growWatterMelon(level, entity);

            return null;
        }), 10);
    }

    public static Function<TeaInfo, Void> spreadXPType(int limit){
        return limitedType((info -> {
            Level level = info.level;
            LivingEntity livingEntity = info.entity;

            additionalTeaFunctions.spawnEntity(level, livingEntity);

            return null;
        }), limit);
    }

    public static Function<TeaInfo, Void> spreadItemType(String item, int limit){
        return limitedType((info -> {
            Level level = info.level;
            LivingEntity entity = info.entity;

            additionalTeaFunctions.spawnItems(level, entity, getCreateItemFromName(item));

            return null;
        }), limit);
    }

    public static Function<TeaInfo, Void> limitedType(Function<TeaInfo, Void> function, int limit){
        return (info) -> {
            int time_remaining = info.time_remaining;
            ItemStack stack = info.stack;

            if(time_remaining % (TeasItem.EAT_DURATION / limit) == 0){
                if (stack.getOrCreateTag().getInt("drinked") <= limit){

                    function.apply(info);

                    stack.getOrCreateTag().putInt("drinked", stack.getOrCreateTag().getInt("drinked") + 1);
                }
            }
            return null;
        };
    }

    public static Function<TeaInfo, Void> getTeaAction(AllTeas tea){
        return TeaActions.allTeaActions.get(Createteas.ID + ":" + tea.TeaName);
    }

    public static void putDrinkingAction(String name, Function<TeaInfo, Void> function){
        allDrinkingTeaActions.put(Createteas.ID + ":" + name, function);
    }

    public static void register(){}
}
