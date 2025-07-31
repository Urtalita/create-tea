package org.Portality.createteas.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TeaInfo {
    public Level level;
    public ItemStack stack;
    public LivingEntity entity;
    public int time_remaining;

    public TeaInfo(Level level, ItemStack stack, LivingEntity entity, int timeRemaining) {
        this.level = level;
        this.stack = stack;
        this.entity = entity;
        time_remaining = timeRemaining;
    }

    public TeaInfo(Level level, ItemStack stack, LivingEntity entity) {
        new TeaInfo(level, stack, entity, 0);
    }
}
