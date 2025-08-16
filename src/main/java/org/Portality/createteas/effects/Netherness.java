package org.Portality.createteas.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.Portality.createteas.items.TeaActions;
import org.Portality.createteas.items.TeaInfo;

import java.util.function.Function;

public class Netherness extends MobEffect {
    protected Netherness(MobEffectCategory p_19451_, int p_19452_) {
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

        if (effect.getDuration() <= 1) {
            TeaActions.portalTea().apply(new TeaInfo(entity.level(), new ItemStack(Items.STICK), entity, 0));
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

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
