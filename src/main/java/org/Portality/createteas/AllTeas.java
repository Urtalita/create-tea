package org.Portality.createteas;

import com.simibubi.create.Create;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import org.Portality.createteas.effects.TeaEffects;
import org.Portality.createteas.items.TeaActions;
import org.Portality.createteas.items.TeaDrinkingActions;
import org.Portality.createteas.items.TeaInfo;

import java.util.function.Function;

import static net.minecraft.world.effect.MobEffects.*;

public enum AllTeas {

    ABSOLUTE_TEANIMA("absolute_teanima", teaEffectS(BLINDNESS, 5),
            TeaActions.absoluteTea(), false, duo(minecraft("gray_dye") , create("builders_tea"))),

    ACTUAL_BUILDER_S_TEA("actual_builder_s_tea", teaEffectM(DIG_SPEED, 2, 1), duo(tea("absolute_teanima"), minecraft("bricks"))),

    AET("aet",teaEffectM(DIG_SLOWDOWN, 3),
            TeaActions.aet(), false, minecraft("warped_wart_block")),

    ANDESITE_TEA("andesite_tea",teaEffectS(MOVEMENT_SLOWDOWN, 10),
            TeaActions.andesiteAlloy(), false, create("andesite_alloy")),

    ANONYMOUS_TEA("anonymous_tea", teaEffectS(INVISIBILITY, 10), minecraft("clay_ball")),

    ANTIFREEZE("antifreeze", teaEffectS(FIRE_RESISTANCE, 40),
            TeaActions.antiFreeze(), false, minecraft("magma_block")),

    BEE_SWARM_TEAMULATOR("bee_swarm_teamulator", teaEffectM(SATURATION, 1),
            TeaActions.beeTea(), false, minecraft("honeycomb")),

    BEER_IN_A_CUP("beer_in_a_cup",new MobEffectInstance[]{
            teaEffectS(CONFUSION, 40),
            teaEffectS(MOVEMENT_SLOWDOWN, 30),
    }, TeaActions.drivingInMyBeer(), false, minecraft("brown_mushroom")),

    BLACK_TEA("black_tea",teaEffectM(MOVEMENT_SPEED, 3), minecraft("ink_sac")),

    BLAZE_TEA("blaze_tea",teaEffectS(FIRE_RESISTANCE, 40),
            TeaActions.blazeTea(), false, minecraft("blaze_rod")),

    BLOOD("blood", teaEffectM(REGENERATION, 1, 2),
            TeaActions.boneTea(), false, minecraft("redstone")),

    BREAZING_TEA("breazing_tea",teaEffectS(LEVITATION, 10),
            TeaActions.spawnParticleTypeTea(ParticleTypes.FIREWORK), false, minecraft("snowball")),

    BUBLE_GUM_TEA("buble_gum_tea",teaEffectS(JUMP, 10, 1), duo(minecraft("pink_dye"), minecraft("light_blue_dye"))),

    CARDBOARD_TEA("cardboard_tea",teaEffectM(SATURATION, 1),
            TeaActions.cardboardTea(), false , create("cardboard")),

    CARROT_TEA("carrot_tea",teaEffectM(SATURATION, 2),
            TeaActions.spawnParticleTypeTea(ParticleTypes.HEART), false, "carrot"),

    CAT_S_NOT_SIMI_TEA("cat_s_not_simi_tea",teaEffectM(SATURATION, 2), duo(minecraft("cooked_cod"), minecraft("pink_dye"))),

    CHOCOLATE_TEA("chocolate_tea",teaEffectM(SATURATION, 2),
            TeaActions.spawnParticleTypeTea(ParticleTypes.HEART), false, create("bar_of_chocolate")),

    COCOA("cocoa",teaEffectM(SATURATION, 2),
            TeaActions.spawnParticleTypeTea(ParticleTypes.HEART), false, minecraft("cocoa_beans")),

    COMMUNIST_TEA("communist_tea",teaEffectM(SATURATION, 1),
            TeaActions.communistTea(), false, duo(minecraft("red_dye"), minecraft("yellow_dye"))),

    CRYSTAL_SLIME_IN_A_CUP("crystal_slime_in_a_cup",
            TeaActions.slimeTea(20), false , duo(minecraft("slime_ball"), minecraft("diamond"))),

    CYBER_TEA("cyber_tea",
            TeaActions.cyberTea(30), false, duo(minecraft("redstone"), minecraft("sticky_piston"))),

    DOOMED_TEA("doomed_tea",teaEffectM(SATURATION, 1),
            TeaDrinkingActions.limitedType(TeaActions.lavaTea(), 8), TeaActions.lavaTea(), minecraft("lava_bucket")),

    DUMPLING_TEA("dumpling_tea",teaEffectM(SATURATION, 4),
            TeaActions.spawnParticleTypeTea(ParticleTypes.HEART), false, duo(create("dough"), minecraft("cooked_porkchop"))),

    ELECTRONIC_TEODE("electronic_teode",teaEffectM(SATURATION, 1),
            TeaActions.cyberTea(60), false, create("electron_tube")),

    EXPERIENCED_TEA("experienced_tea",teaEffectM(SATURATION, 1),
            TeaDrinkingActions.spreadXPType(10), true, create("experience_nugget")),

    GAMBLER_S_TEA("gambler_s_tea",teaEffectM(SATURATION, 1), minecraft("moss_block")),

    GREEN_TEA("green_tea",teaEffectM(REGENERATION, 3), minecraft("oak_leaves")),

    HONEY_SLIME_IN_A_CUP("honey_slime_in_a_cup",
            TeaActions.slimeTea(20), false, duo(minecraft("slime_ball"), minecraft("honey_bottle"))),

    LAVENDER_TEA("lavender_tea",teaEffectM(HEALTH_BOOST, 3), minecraft("allium")),

    LITERALLY_FISH("literally_fish",teaEffectM(SATURATION, 1),
            TeaActions.fishTea(), false, minecraft("salmon")),

    MAGMA_IN_A_CUP("magma_in_a_cup",teaEffectM(SATURATION, 1),
            TeaActions.lavaTea(), false, minecraft("magma_cream")),

    MELON_TEA("melon_tea",teaEffectM(SATURATION, 1),
            TeaDrinkingActions.kotarbuzTea(), true , minecraft("melon")),

    MILKY_MINT_TEA("milky_mint_tea",teaEffectM(DAMAGE_BOOST, 1, 1), duo(tea("mint_tea"), minecraft("milk_bucket"))),

    MINT_TEA("mint_tea",teaEffectM(DAMAGE_BOOST, 3), minecraft("grass")),

    MOLTEN_BRASS("molten_brass", new MobEffectInstance[]{
            teaEffectS(MOVEMENT_SLOWDOWN, 10),
            teaEffectS(REGENERATION, 5, 2),
            teaEffectS(HEALTH_BOOST, 10, 2),
            teaEffectS(FIRE_RESISTANCE, 5),
    }, TeaDrinkingActions.spreadItemType("brass_nugget", 4), true,  create("brass_ingot")),

    MOLTEN_COPPER("molten_copper",teaEffectM(SATURATION, 1),
            TeaActions.copperTea(), false, minecraft("copper_block")),

    MOLTEN_FISH("molten_fish",teaEffectS(POISON, 30),
            TeaActions.moltenFishTea(), false, minecraft("pufferfish")),

    MOLTEN_IRON("molten_iron",teaEffectM(SATURATION, 1),
            TeaActions.ironTea(), false, minecraft("iron_block")),

    MOLTEN_NETHER_PORTAL("molten_nether_portal",teaEffectM(SATURATION, 1),
            TeaActions.netherness(30), false, minecraft("crying_obsidian")),

    MOLTEN_NETHERITE("molten_netherite", new MobEffectInstance[]{
            teaEffectS(MOVEMENT_SLOWDOWN, 5),
            teaEffectS(REGENERATION, 50, 2),
            teaEffectS(HEALTH_BOOST, 50, 2),
            teaEffectS(NIGHT_VISION, 25),
            teaEffectS(FIRE_RESISTANCE, 25),
    } , duo(tea("molten_brass"), minecraft("netherite_scrap"))),

    MOLTEN_OBSIDIAN("molten_obsidian",teaEffectM(SATURATION, 1),
            TeaActions.netherness(30), false, create("powdered_obsidian")),

    MOLTEN_SPRING_ALLOY("molten_spring_alloy",teaEffectM(SATURATION, 1),
            TeaActions.slimeTea(120), false, duo(create("brass_ingot"), minecraft("slime_ball"))),

    NUCLEAR_TEA("nuclear_tea", new MobEffectInstance[]{
            teaEffectM(GLOWING, 5),
            teaEffectM(MOVEMENT_SLOWDOWN, 10),
            teaEffectM(WEAKNESS, 15),
            teaEffectM(POISON, 5),
    }, duo(minecraft("deepslate_iron_ore"), minecraft("deepslate_copper_ore"))),

    ORANGE_TEA("orange_tea",teaEffectM(DOLPHINS_GRACE, 1), minecraft("orange_dye")),

    OXIDIZED_TEA("oxidized_tea",teaEffectS(POISON, 45),
            TeaActions.copperTea(), false, minecraft("oxidized_copper")),

    PEACH_TEA("peach_tea",teaEffectM(SATURATION, 3),
            TeaActions.spawnParticleTypeTea(ParticleTypes.HEART), false, duo(minecraft("glow_berries"), minecraft("blaze_powder"))),

    PINEAPLE_TEA("pineaple_tea",teaEffectM(SATURATION, 3),
            TeaActions.spawnParticleTypeTea(ParticleTypes.HEART), false, duo(minecraft("glow_berries"), minecraft("honeycomb"))),

    PINK_SLIME_IN_A_CUP("pink_slime_in_a_cup",
            TeaActions.slimeTea(25), false, duo(minecraft("slime_ball"), minecraft("pink_dye"))),

    PISTACHIO_TEA("pistachio_tea",teaEffectM(SATURATION, 3),
            TeaActions.pfTea(), false, duo(minecraft("lime_dye"), minecraft("scute"))),

    PORTAL_S_TEA("portal_s_tea",teaEffectM(NIGHT_VISION, 1),
            TeaDrinkingActions.portalTea(), TeaActions.portalTea(), minecraft("popped_chorus_fruit")),

    PUDDLE_SLIME_IN_A_CUP("puddle_slime_in_a_cup",
            TeaActions.slimeTea(30), false, duo(minecraft("slime_ball"), minecraft("cyan_dye"))),

    QUANTUM_SLIME_IN_A_CUP("quantum_slime_in_a_cup",
            TeaActions.slimeTea(200), false, tea("quantum_slime_in_a_cup")),

    RICH_TEA("rich_tea",teaEffectM(MOVEMENT_SLOWDOWN, 1), minecraft("gold_ingot")),

    ROSE_QUARTZ_TEA("rose_quartz_tea" ,
            TeaActions.cyberTea(50), false, create("rose_quartz")),

    ROTEATOR("roteator",teaEffectM(SATURATION, 1),
            TeaActions.rotateTea(), true, create("mechanical_bearing")),

    SCHEMATIC_OF_CUP("schematic_of_cup", teaEffectM(MOVEMENT_SPEED, 1), TeaActions.schematicTea()
            , false, create("schematic ")),

    SMART_TEA_WITH_GOGGLES_SCHEMATICS_DOWNLOAD("smart_tea_with_goggles_schematics_download",teaEffectS(MOVEMENT_SPEED, 30, 1)
            , TeaActions.schematicTea(), false, create("goggles")),

    SMART_TEA_WITH_MONOCLE_SCHEMATICS_DOWNLOAD("smart_tea_with_monocle_schematics_download",teaEffectM(MOVEMENT_SPEED, 40, 1)
            , TeaActions.schematicTea(), false, minecraft("glass_pane")),

    SMART_TEA_WITHOUT_GOGGLES_SCHEMATICS_DOWNLOAD("smart_tea_without_goggles_schematics_download",teaEffectM(MOVEMENT_SPEED, 1)
            , TeaActions.schematicTea(), false, create("content_observer")),

    SPACE_TEAVELER("space_teaveler",teaEffectS(JUMP, 5, 5), duo(minecraft("black_dye"), minecraft("magenta_dye"))),

    STICER_S_TEA("sticer_s_tea",teaEffectM(SATURATION, 1),
            TeaActions.sticerTea(), false, minecraft("paper")),

    STRONG_TEA("strong_tea",teaEffectM(DAMAGE_BOOST, 1), minecraft("blaze_rod")),

    SUSPICIOUS_TEA("suspicious_tea",teaEffectM(SATURATION, 1), TeaActions.susTea(), false, minecraft("red_dye")),

    SWEET_BERRIES_TEA("sweet_berries_tea", teaEffectM(SATURATION, 3)
            , TeaActions.spawnParticleTypeTea(ParticleTypes.HAPPY_VILLAGER), false, minecraft("glow_berries")),

    TARRAGON_LEMONADE("tarragon_lemonade",teaEffectM(SATURATION, 3)
            , TeaActions.spawnParticleTypeTea(ParticleTypes.HAPPY_VILLAGER), false, minecraft("cactus")),

    TASTY_AIR("tasty_air", ""),

    TEA_NOT_FOUND("tea_not_found",teaEffectM(SATURATION, 1), tea("quantum_slime_in_a_cup")),

    TEA_RICHARD("tea_richard",teaEffectM(SLOW_FALLING, 1), minecraft("golden_carrot")),

    TEADH("teadh",teaEffectM(FIRE_RESISTANCE, 1), TeaActions.defestoTea(5),
            false, minecraft("orange_banner")),

    TEAMAN("teaman",teaEffectM(SATURATION, 1), tea("tea_not_found")),

    THREE_LAYER_TEA_LATTE("three_layer_tea_latte",new MobEffectInstance[]{
            teaEffectS(MOVEMENT_SPEED, 1),
            teaEffectS(DIG_SPEED, 1)
    }, duo(minecraft("cocoa_beans"), minecraft("cocoa_beans"))),

    TIME_IN_A_TEA("time_in_a_tea",teaEffectM(SLOW_FALLING, 1), tea("time_in_a_tea")),

    TIRAMISU("tiramisu",teaEffectM(SATURATION, 3),
            TeaActions.spawnParticleTypeTea(ParticleTypes.HEART), false, duo(create("cocoa_beans"), minecraft("dough"))),

    TOMATO_TEA("tomato_tea",teaEffectM(SATURATION, 3),
            TeaActions.spawnParticleTypeTea(ParticleTypes.HEART), false, duo(minecraft("red_dye"), minecraft("potato"))),

    WARPED_TEA("warped_tea",teaEffectM(SATURATION, 1), TeaActions.portalTea(), false, minecraft("end_stone")),

    WATER_REFERENCE("water_reference", minecraft("water_bucket")),

    WINTER_WIND_TEA("winter_wind_tea", teaEffectM(FIRE_RESISTANCE, 1),
            TeaActions.coldTea(), false, duo(tea("breazing_tea"), create("propeller"))),

    ;

    AllTeas(String teaName, MobEffectInstance[] mobEffectInstances, String craftItem) {
        this.mobEffectInstances = mobEffectInstances;
        TeaName = teaName;
        this.craftItem = craftItem;
    }

    AllTeas(String teaName, MobEffectInstance mobEffectInstance, String craftItem) {
        this.mobEffectInstances = new MobEffectInstance[]{mobEffectInstance};
        TeaName = teaName;
        this.craftItem = craftItem;
    }

    AllTeas(String teaName, String craftItem) {
        this.mobEffectInstances = new MobEffectInstance[]{};
        TeaName = teaName;
        this.craftItem = craftItem;
    }

    AllTeas(String teaName, MobEffectInstance[] mobEffectInstances, Function<TeaInfo, Void> action, boolean drinking, String craftItem) {
        this.mobEffectInstances = mobEffectInstances;
        TeaName = teaName;
        manageAction(action, drinking);
        this.craftItem = craftItem;
    }

    AllTeas(String teaName, MobEffectInstance mobEffectInstance, Function<TeaInfo, Void> action, boolean drinking, String craftItem) {
        this.mobEffectInstances = new MobEffectInstance[]{mobEffectInstance};
        TeaName = teaName;
        manageAction(action, drinking);
        this.craftItem = craftItem;
    }

    AllTeas(String teaName, Function<TeaInfo, Void> action, boolean drinking, String craftItem) {
        this.mobEffectInstances = new MobEffectInstance[]{};
        TeaName = teaName;
        manageAction(action, drinking);
        this.craftItem = craftItem;
    }

    AllTeas(String teaName, MobEffectInstance[] mobEffectInstances, Function<TeaInfo, Void> drinkingAction, Function<TeaInfo, Void> action, String craftItem) {
        this.mobEffectInstances = mobEffectInstances;
        TeaName = teaName;
        TeaDrinkingActions.putDrinkingAction(TeaName, drinkingAction);
        TeaActions.putAction(TeaName, action);
        this.craftItem = craftItem;
    }

    AllTeas(String teaName, MobEffectInstance mobEffectInstance, Function<TeaInfo, Void> drinkingAction, Function<TeaInfo, Void> action, String craftItem) {
        this.mobEffectInstances = new MobEffectInstance[]{mobEffectInstance};
        TeaName = teaName;

        TeaDrinkingActions.putDrinkingAction(TeaName, drinkingAction);
        TeaActions.putAction(TeaName, action);
        this.craftItem = craftItem;
    }

    AllTeas(String teaName, Function<TeaInfo, Void> drinkingAction, Function<TeaInfo, Void> action, String craftItem) {
        this.mobEffectInstances = new MobEffectInstance[]{};
        TeaName = teaName;
        TeaDrinkingActions.putDrinkingAction(TeaName, drinkingAction);
        TeaActions.putAction(TeaName, action);
        this.craftItem = craftItem;
    }

    public final MobEffectInstance[] mobEffectInstances;
    public final String TeaName;
    public final String craftItem;

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

    public static MobEffectInstance teaEffectS(MobEffect effect, int timeSec){
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

    private static String minecraft(String craftItem){
        return "minecraft:" + craftItem;
    }

    public static String create(String craftItem){
        return Create.ID + ":" + craftItem;
    }

    public static String tea(String craftItem){
        return Createteas.ID + ":" + craftItem;
    }

    public static String duo(String craftItem1, String craftItem2){
        return craftItem1 + " " + craftItem2;
    }
}
