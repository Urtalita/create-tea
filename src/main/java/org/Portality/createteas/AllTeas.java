package org.Portality.createteas;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.Portality.createteas.items.TeaActions;
import org.Portality.createteas.items.TeaDrinkingActions;
import org.Portality.createteas.items.TeaInfo;

import java.util.function.Function;

import static net.minecraft.world.effect.MobEffects.*;

public enum AllTeas {

    PORTAL_TEA("portal", teaEffectM(NIGHT_VISION, 1), TeaDrinkingActions.portalTea(), TeaActions.portalTea()),
    KOTARBUZ_TEA("arbuz", teaEffectM(SATURATION, 1), TeaDrinkingActions.kotarbuzTea(), true),

    BONE_TEA("bone", teaEffectM(SATURATION, 1)),

    BRASS_TEA("brass", new MobEffectInstance[]{
            teaEffectS(MOVEMENT_SLOWDOWN, 5),
            teaEffectS(REGENERATION, 10, 2),
            teaEffectS(HEALTH_BOOST, 20, 2),
            teaEffectS(FIRE_RESISTANCE, 10),
    }, TeaDrinkingActions.spreadItemType("brass_nugget", 8), true),

    CARDBOARD_TEA("cardboard", teaEffectM(SATURATION, 1), TeaActions.cardboardTea(), false),

    CHOCOLATE_TEA("chocolate", teaEffectM(SATURATION, 1)),
    COLD_TEA("cold", teaEffectM(SATURATION, 1)),
    CYBER_TEA("cyber", teaEffectM(SATURATION, 1)),
    DEFESTO_TEA("defesto", teaEffectM(SATURATION, 1)),
    DIAMOND_TEA("diamond", teaEffectM(SATURATION, 1)),

    DOOM_TEA("doom", teaEffectM(SATURATION, 1), TeaDrinkingActions.limitedType(TeaActions.lavaTea(), 8), TeaActions.lavaTea()),

    EXP_TEA("exp", teaEffectM(SATURATION, 1), TeaDrinkingActions.spreadXPType(10), true),

    IRON_TEA("iron", teaEffectM(SATURATION, 1)),
    MARK_TEA("mark", teaEffectM(SATURATION, 1)),

    METTER_TEA("metter", teaEffectM(SATURATION, 1), TeaActions.copperTea(), false),

    NETHERITE_TEA("netherite", teaEffectM(SATURATION, 1)),
    PURPLE_TEA("purple", teaEffectM(SATURATION, 1)),
    ROSE_QUARTS_TEA("rose_quarts", teaEffectM(SATURATION, 1)),
    ROSE_TEA("rose", teaEffectM(SATURATION, 1)),
    SCHEMATIC_TEA("schematic", teaEffectM(SATURATION, 1)),
    SOME_TEA("some", teaEffectM(SATURATION, 1)),
    SPRING_ALLOY_TEA("spring_alloy", teaEffectM(SATURATION, 1)),
    TARHUN_TEA("tarhun", teaEffectM(SATURATION, 1)),
    THING_TEA("thing", teaEffectM(SATURATION, 1)),

    TOMAT_TEA("tomat", teaEffectM(SATURATION, 3)),

    URANIUM_TEA("uranium", new MobEffectInstance[]{
            teaEffectM(GLOWING, 5),
            teaEffectM(MOVEMENT_SLOWDOWN, 10),
            teaEffectM(WEAKNESS, 15),
            teaEffectM(POISON, 5),
    }),
    ;

    AllTeas(String teaName, MobEffectInstance[] mobEffectInstances) {
        this.mobEffectInstances = mobEffectInstances;
        TeaName = teaName + "_tea";
    }

    AllTeas(String teaName, MobEffectInstance mobEffectInstance) {
        this.mobEffectInstances = new MobEffectInstance[]{mobEffectInstance};
        TeaName = teaName + "_tea";
    }

    AllTeas(String teaName) {
        this.mobEffectInstances = new MobEffectInstance[]{};
        TeaName = teaName + "_tea";
    }

    AllTeas(String teaName, MobEffectInstance[] mobEffectInstances, Function<TeaInfo, Void> action, boolean drinking) {
        this.mobEffectInstances = mobEffectInstances;
        TeaName = teaName + "_tea";
        manageAction(action, drinking);
    }

    AllTeas(String teaName, MobEffectInstance mobEffectInstance, Function<TeaInfo, Void> action, boolean drinking) {
        this.mobEffectInstances = new MobEffectInstance[]{mobEffectInstance};
        TeaName = teaName + "_tea";
        manageAction(action, drinking);
    }

    AllTeas(String teaName, Function<TeaInfo, Void> action, boolean drinking) {
        this.mobEffectInstances = new MobEffectInstance[]{};
        TeaName = teaName + "_tea";
        manageAction(action, drinking);
    }

    AllTeas(String teaName, MobEffectInstance[] mobEffectInstances, Function<TeaInfo, Void> drinkingAction, Function<TeaInfo, Void> action) {
        this.mobEffectInstances = mobEffectInstances;
        TeaName = teaName + "_tea";
        TeaDrinkingActions.putDrinkingAction(TeaName, drinkingAction);
        TeaActions.putAction(TeaName, action);
    }

    AllTeas(String teaName, MobEffectInstance mobEffectInstance, Function<TeaInfo, Void> drinkingAction, Function<TeaInfo, Void> action) {
        this.mobEffectInstances = new MobEffectInstance[]{mobEffectInstance};
        TeaName = teaName + "_tea";

        TeaDrinkingActions.putDrinkingAction(TeaName, drinkingAction);
        TeaActions.putAction(TeaName, action);
    }

    AllTeas(String teaName, Function<TeaInfo, Void> drinkingAction, Function<TeaInfo, Void> action) {
        this.mobEffectInstances = new MobEffectInstance[]{};
        TeaName = teaName + "_tea";
        TeaDrinkingActions.putDrinkingAction(TeaName, drinkingAction);
        TeaActions.putAction(TeaName, action);
    }

    public final MobEffectInstance[] mobEffectInstances;
    public final String TeaName;

    private void manageAction(Function<TeaInfo, Void> action, boolean drinking){
        if(drinking){
            TeaDrinkingActions.putDrinkingAction(TeaName, action);
        } else {
            TeaActions.putAction(TeaName, action);
        }
    }

    private static MobEffectInstance teaEffectM(MobEffect effect, int timeMin){
        return teaEffectM(effect, timeMin, 0);
    }

    private static MobEffectInstance teaEffectS(MobEffect effect, int timeSec){
        return teaEffectS(effect, timeSec, 0);
    }

    private static MobEffectInstance teaEffectT(MobEffect effect, int timeTicks){
        return teaEffectT(effect, timeTicks, 0);
    }

    private static MobEffectInstance teaEffectM(MobEffect effect, int timeMin, int level){
        return teaEffectS(effect, timeMin * 60, level);
    }

    private static MobEffectInstance teaEffectS(MobEffect effect, int timeSec, int level){
        return teaEffectT(effect, timeSec * 20, level);
    }

    private static MobEffectInstance teaEffectT(MobEffect effect, int timeTicks, int level){
        return new MobEffectInstance(effect, timeTicks, level, false, false, false);
    }
}
