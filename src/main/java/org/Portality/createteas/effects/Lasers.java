package org.Portality.createteas.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Lasers extends MobEffect {
    protected Lasers(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int levelEffect) {
        Level level = entity.level();
        RandomSource random = level.getRandom();
        Vec3 center = entity.position();

        double eyeX = entity.getX();
        double eyeY = entity.getEyeY();
        double eyeZ = entity.getZ();

        // Получаем вектор взгляда
        Vec3 lookVec = entity.getLookAngle();

        // Рассчитываем вектор "вправо" относительно сущности
        Vec3 rightVec = new Vec3(1, 0, 0)
                .yRot((float) Math.toRadians(-entity.getYRot()));

        for(int i = 0; i < 10; i++){
            // Вычисляем точку спавна с учетом смещения
            double distance = 0.25 + i / 10f; // Дистанция перед сущностью
            double rightOffset = 0.2; // Смещение вправо

            Vec3 spawnPos = new Vec3(eyeX, eyeY, eyeZ)
                    .add(lookVec.scale(distance)) // 1 блок вперед по взгляду
                    .add(rightVec.scale(rightOffset)); // смещение вправо

            level.addParticle(
                    ParticleTypes.HAPPY_VILLAGER,
                    spawnPos.x, spawnPos.y, spawnPos.z, // позиция
                    0, 0, 0
            );

            spawnPos = new Vec3(eyeX, eyeY, eyeZ)
                    .add(lookVec.scale(distance)) // 1 блок вперед по взгляду
                    .add(rightVec.scale(rightOffset).scale(-1)); // смещение вправо

            level.addParticle(
                    ParticleTypes.HAPPY_VILLAGER,
                    spawnPos.x, spawnPos.y, spawnPos.z, // позиция
                    0, 0, 0
            );
        }

        super.applyEffectTick(entity, levelEffect);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
