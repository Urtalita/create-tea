package org.Portality.createteas.items;

import com.simibubi.create.AllItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class additionalTeaFunctions {
    public static void growWatterMelon(Level level, LivingEntity entity){
        Block blockOn = level.getBlockState(entity.getOnPos()).getBlock();
        if(blockOn != Blocks.FARMLAND){
            if(blockOn == Blocks.DIRT || blockOn == Blocks.GRASS_BLOCK || blockOn == Blocks.SAND){
                level.setBlock(entity.getOnPos(), Blocks.FARMLAND.defaultBlockState(), 3);
            }
            return;
        }

        BlockState melonState = level.getBlockState(entity.getOnPos().above());
        if(melonState.getBlock() != Blocks.MELON_STEM){
            level.setBlock(entity.getOnPos().above(), Blocks.MELON_STEM.defaultBlockState(), 3);
            return;
        }

        if(melonState.getValue(StemBlock.AGE) < 7){
            level.setBlock(entity.getOnPos().above(), Blocks.MELON_STEM.defaultBlockState().setValue(StemBlock.AGE ,melonState.getValue(StemBlock.AGE) + 1), 3);
        } else {
            if(level.getBlockState(entity.getOnPos().above().relative(getFreeDirection(level, entity.getOnPos().above()))).isAir()){
                level.setBlock(entity.getOnPos().above().relative(getFreeDirection(level, entity.getOnPos().above())), Blocks.MELON.defaultBlockState(), 3);
            }
        }
    }

    public static Direction getFreeDirection(Level level, BlockPos pos){
        Direction direction = Direction.NORTH;
        for (int i = 0; i < 4; i++){
            if(level.getBlockState(pos.relative(direction)).isAir()){
                return direction;
            }
            direction = direction.getClockWise();
        }
        return Direction.UP;
    }

    public static void spawnItems(Level level, LivingEntity entity, Item items){
        Vec3 from = entity.position().add(new Vec3(0, entity.getEyeHeight(), 0));
        Random random = new Random();
        BlockPos playerPos = entity.blockPosition();
        Vec3 eyePosition = entity.getEyePosition();

        ItemStack stack = new ItemStack(items);
        ItemEntity item = new ItemEntity(level, eyePosition.x, eyePosition.y, eyePosition.z, stack);

        // Настраиваем физику вылета
        double speed = 0.15 * 2;

        // Вектор направления (случайный + небольшое смещение вверх)
        double motionX = (random.nextDouble() - 0.5) * 2 * speed;
        double motionY = random.nextDouble() * speed + 0.1;
        double motionZ = (random.nextDouble() - 0.5) * 2 * speed;

        item.setDeltaMovement(motionX, motionY, motionZ);
        item.setPickUpDelay(20); // Задержка 1 секунда (20 тиков)

        // Небольшая задержка перед исчезновением (5 минут)
        item.lifespan = 60 * 20 * 5;

        level.addFreshEntity(item);
    }

    public static void spawnEntity(Level level, LivingEntity entity){
        if(level.isClientSide){return;}

        Vec3 from = entity.position().add(new Vec3(0, entity.getEyeHeight(), 0));
        RandomSource random = level.getRandom();
        BlockPos playerPos = entity.blockPosition();
        Vec3 eyePosition = entity.getEyePosition();

        Entity entry = new ExperienceOrb(level, eyePosition.x, eyePosition.y + 1, eyePosition.z, 2);

        if (entry instanceof ExperienceOrb xpOrb) {
            xpOrb.value = 20; // Количество опыта
        }

        // Настраиваем физику вылета
        double speed = 0.15 * 2;

        // Вектор направления (случайный + небольшое смещение вверх)
        double motionX = (random.nextDouble() - 0.5) * 2 * speed;
        double motionY = random.nextDouble() * speed + 0.1;
        double motionZ = (random.nextDouble() - 0.5) * 2 * speed;

        entry.setDeltaMovement(motionX, motionY, motionZ);

        level.addFreshEntity(entry);
    }

    public static BlockPos findSafeY(Level level, BlockPos start, int limit){
        for(int i = 0; i <= limit; i++){
            if(level.getBlockState(new BlockPos(start.getX(), start.getY() + i, start.getZ())).isAir() &&
                    !level.getBlockState(new BlockPos(start.getX(), start.getY() + i - 1, start.getZ())).isAir()){
                return new BlockPos(start.getX(), start.getY() + i, start.getZ());
            }
            if(level.getBlockState(new BlockPos(start.getX(), start.getY() - i, start.getZ())).isAir() &&
                    !level.getBlockState(new BlockPos(start.getX(), start.getY() - i + 1, start.getZ())).isAir()){
                return new BlockPos(start.getX(), start.getY() - i, start.getZ());
            }
        }
        return null;
    }

    public static void setRingSafe(BlockPos pos, Block block, Level level){
        setStateSafe(pos.north(), block, level);
        setStateSafe(pos.south(), block, level);
        setStateSafe(pos.east(), block, level);
        setStateSafe(pos.west(), block, level);
    }

    public static void setStateSafe(BlockPos pos, Block block, Level level){
        if(level.getBlockState(pos).isAir()){
            level.setBlock(pos, block.defaultBlockState(), 3);
        }
    }

    public static void spawnInARandomAround(Level level, LivingEntity archnor, EntityType type, int range, int amount){
        RandomSource source = level.random;

        for(int i = 0; i < amount; i++){
            Entity entity = type.create(level);

            double xShift = source.nextInt(-range, range) + archnor.getX();
            double zShift = source.nextInt(-range, range) + archnor.getZ();
            double yShift = source.nextInt(0, range) + archnor.getY();

            if(!level.getBlockState(BlockPos.containing(new Vec3(xShift, yShift, zShift))).isAir()){return;}

            spawnEntity(level, new Vec3(xShift, yShift, zShift), entity);
        }
    }

    public static void spawnEntity(Level level, Vec3 pos, Entity entity){
        entity.setPos(pos);
        level.addFreshEntity(entity);
    }
}
