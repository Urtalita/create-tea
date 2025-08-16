package org.Portality.createteas.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.Portality.createteas.items.TeaActions;
import org.Portality.createteas.items.TeaInfo;

import java.util.ArrayList;

public class NetherTea extends MobEffect {
    protected NetherTea(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int levelEffect) {
        Level level = entity.level();
        RandomSource random = level.getRandom();
        Vec3 center = entity.position();
        double radius = 3;

        MobEffectInstance effect = entity.getEffect(this);
        if (effect == null) return;

        if (effect.getDuration() % 20 == 0) {
            int ringRadius = 5 - effect.getDuration() / 20;
            ArrayList<BlockPos> blocks = new ArrayList<>();
            BlockPos entityPos = entity.blockPosition().below();
            RandomSource source = level.random;
            Block[] allowedBlocks = new Block[]{
                  Blocks.DIRT,
                  Blocks.GRASS_BLOCK,
                  Blocks.DIRT_PATH,
                  Blocks.STONE,
                  Blocks.COBBLESTONE,
                  Blocks.ANDESITE,
                  Blocks.DEEPSLATE
            };

            for(int x = -ringRadius; x < ringRadius+1; x++){
                for(int y = -ringRadius; y < ringRadius+1; y++){
                    for(int z = -ringRadius; z < ringRadius+1; z++){
                        blocks.add(new BlockPos(entityPos.getX() + x, entityPos.getY() + y, entityPos.getZ() + z));
                    }
                }
            }

            for(BlockPos pos : blocks){
                if(pos.getCenter().distanceTo(entityPos.getCenter()) > ringRadius){continue;}

                Block block = level.getBlockState(pos).getBlock();

                for(Block allowedBlock : allowedBlocks){
                    if(allowedBlock == block){
                        replace(level, pos, source);
                    }
                }
            }
        }

        if(!(level.getGameTime() % 5 == 0)){super.applyEffectTick(entity, levelEffect); return;}

        for (int i = 0; i < 10; i++) {
            // Генерация случайных углов и расстояния
            double theta = random.nextDouble() * 2 * Math.PI;
            double phi = random.nextDouble() * Math.PI;
            double r = radius * random.nextDouble();

            // Преобразование сферических координат в декартовы
            double x = r * Math.sin(phi) * Math.cos(theta);
            double y = r * Math.cos(phi);
            double z = r * Math.sin(phi) * Math.sin(theta);

            // Конечная позиция частицы
            Vec3 pos = center.add(x, y, z);

            // Отправка частиц всем игрокам в радиусе
            level.addParticle(
                    ParticleTypes.PORTAL,
                    pos.x, pos.y, pos.z, // позиция
                    0, // количество частиц в точке
                    0, 0  // смещение (дельта)
                    // скорость
            );
        }

        super.applyEffectTick(entity, levelEffect);
    }

    public void replace(Level level, BlockPos pos, RandomSource source){
        int chance = source.nextInt(0, 40);

        if (chance < 4){
            level.setBlock(pos, Blocks.NETHER_GOLD_ORE.defaultBlockState(), 3);
            return;
        }

        if(chance == 6){
            level.setBlock(pos, Blocks.NETHER_QUARTZ_ORE.defaultBlockState(), 3);
            return;
        }

        level.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
