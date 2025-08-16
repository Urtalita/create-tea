package org.Portality.createteas.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.Portality.createteas.Createteas;

public class TeaEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Createteas.ID);

    public static final RegistryObject<MobEffect> CRAWL = MOB_EFFECTS.register("crawl",
            () -> new CrawlEffect(MobEffectCategory.HARMFUL, 3124687));

    public static final RegistryObject<MobEffect> SLIMENESS = MOB_EFFECTS.register("slimeness",
            () -> new Slimeness(MobEffectCategory.BENEFICIAL, 3124687));

    public static final RegistryObject<MobEffect> REDSTONES = MOB_EFFECTS.register("redstones",
            () -> new Lasers(MobEffectCategory.NEUTRAL, 3124687));

    public static final RegistryObject<MobEffect> NETHERNESS = MOB_EFFECTS.register("netherness",
            () -> new Netherness(MobEffectCategory.NEUTRAL, 3124687));

    public static final RegistryObject<MobEffect> DEFFESTO = MOB_EFFECTS.register("nether_tea",
            () -> new NetherTea(MobEffectCategory.NEUTRAL, 3124687));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
