package org.Portality.createteas.items;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.Portality.createteas.AllTeas;
import org.Portality.createteas.Createteas;
import org.Portality.createteas.effects.TeaEffects;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

import static com.simibubi.create.content.logistics.box.PackageItem.getPackageVelocity;
import static org.Portality.createteas.items.additionalTeaFunctions.*;

public class TeaActions {
    public static HashMap<String, Function<TeaInfo, Void>> allTeaActions = new HashMap<>();

    public static Function<TeaInfo, Void> portalTea(){
        return (info) -> {
            Level level = info.level;
            LivingEntity entity = info.entity;
            if(level == null){return null;}
            if(entity == null){return null;}

            if (!level.isClientSide) {
                double d0 = entity.getX();
                double d1 = entity.getY();
                double d2 = entity.getZ();

                for(int i = 0; i < 16; ++i) {
                    double d3 = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
                    double d4 = Mth.clamp(entity.getY() + (double)(entity.getRandom().nextInt(16) - 8), (double)level.getMinBuildHeight(), (double)(level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1));
                    double d5 = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
                    if (entity.isPassenger()) {
                        entity.stopRiding();
                    }

                    Vec3 vec3 = entity.position();
                    level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entity));
                    net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(entity, d3, d4, d5);
                    if (event.isCanceled()) return null;
                    if (entity.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                        SoundEvent soundevent = entity instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
                        level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                        entity.playSound(soundevent, 1.0F, 1.0F);
                        break;
                    }
                }
            }
            return null;
        };
    }

    public static Function<TeaInfo, Void> cardboardTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level world = info.level;

            if (!(entity instanceof Player player))
                return null;

            float f = getPackageVelocity(10);
            if (world.isClientSide)
                return null;

            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW,
                    SoundSource.NEUTRAL, 0.5F, 0.5F);

            ItemStack copy = new ItemStack(PackageStyles.STANDARD_BOXES.get(world.random.nextInt(0, PackageStyles.STANDARD_BOXES.size() - 1))).copy();

            Vec3 vec = new Vec3(entity.getX(), entity.getY() + entity.getBoundingBox()
                    .getYsize() / 2f, entity.getZ());
            Vec3 motion = entity.getLookAngle()
                    .scale(f * 2);
            vec = vec.add(motion);

            PackageEntity packageEntity = new PackageEntity(world, vec.x, vec.y, vec.z);
            packageEntity.setBox(copy);
            packageEntity.setDeltaMovement(motion);
            packageEntity.tossedBy = new WeakReference<>(player);
            world.addFreshEntity(packageEntity);

            return null;
        };
    }

    public static Function<TeaInfo, Void> copperTea() {
        return (info) -> {
            if(info.stack.getOrCreateTag().getBoolean("drinked")){return null;}

            Level level = info.level;
            LivingEntity entity = info.entity;
            Vec3 pos = entity.position();
            RandomSource source = level.random;
            int xShift = source.nextInt(-4, 4);
            int zShift = source.nextInt(-4, 4);
            Vec3 lightningPos = new Vec3(pos.x + xShift, pos.y, pos.z + zShift);

            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);

            if (lightning != null) {
                lightning.moveTo(lightningPos);

                lightning.setVisualOnly(true);

                level.addFreshEntity(lightning);
            }

            BlockPos lightningRoadPos = additionalTeaFunctions.findSafeY(level, BlockPos.containing(lightningPos), 3);

            if(lightningRoadPos == null){return null;}

            BlockState state = level.getBlockState(BlockPos.containing(lightningPos));
            if(state.isAir()){
                level.setBlock(BlockPos.containing(lightningPos), Blocks.LIGHTNING_ROD.defaultBlockState(), 3);
            }

            info.stack.getOrCreateTag().putBoolean("drinked", true);
            return null;
        };
    }

    public static Function<TeaInfo, Void> ironTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;

            IronGolem golem = EntityType.IRON_GOLEM.create(level);
            golem.setPos(new Vec3(entity.getX(), entity.getY(), entity.getZ()));
            level.addFreshEntity(golem);

            return null;
        };
    }

    public static Function<TeaInfo, Void> lavaTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            entity.setSecondsOnFire(10);
            DamageSources damageSources = info.level.damageSources();
            entity.hurt(damageSources.lava(), 4.0f);
            return null;
        };
    }

    public static Function<TeaInfo, Void> boneTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;

            Skeleton skeleton = EntityType.SKELETON.create(level);
            skeleton.setPos(new Vec3(entity.getX(), entity.getY(), entity.getZ()));
            level.addFreshEntity(skeleton);

            return null;
        };
    }

    public static Function<TeaInfo, Void> spawnParticleTypeTea(ParticleOptions particleT) {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;
            RandomSource random = level.getRandom();
            Vec3 center = entity.position();
            double radius = 3;

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
                if(particleT == ParticleTypes.HEART){
                    level.addParticle(
                            particleT,
                            pos.x, pos.y, pos.z, // позиция
                            1, // количество частиц в точке
                            0, 0  // смещение (дельта)
                            // скорость
                    );
                } else {
                    level.addParticle(
                            particleT,
                            pos.x, pos.y, pos.z, // позиция
                            0, // количество частиц в точке
                            0, 0  // смещение (дельта)
                            // скорость
                    );
                }
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> coldTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;
            BlockPos center = entity.blockPosition();

            setStateSafe(center.below(), Blocks.ICE, level);

            setRingSafe(center, Blocks.ICE, level);
            setRingSafe(center.above(), Blocks.ICE, level);

            setStateSafe(center.above(2), Blocks.ICE, level);

            return null;
        };
    }

    public static Function<TeaInfo, Void> aet() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(!(entity instanceof Player player)){return null;}
            if(level.isClientSide){return null;}

            FoodData foodData = player.getFoodData();
            foodData.setFoodLevel(Math.max(0, foodData.getFoodLevel() - 1));

            ((ServerPlayer) player).connection.send(new ClientboundSetHealthPacket(player.getHealth(), foodData.getFoodLevel(), foodData.getSaturationLevel()));
            return null;
        };
    }

    public static Function<TeaInfo, Void> andesiteAlloy() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof Player player){
                player.addItem(AllItems.ANDESITE_ALLOY.asStack());
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> absoluteTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof ServerPlayer player){
                player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("Absolute")));
                player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("Teanima")));
                player.connection.send(new ClientboundSetTitlesAnimationPacket(20, 60, 20));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> antiFreeze() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            entity.clearFire();

            return null;
        };
    }

    public static Function<TeaInfo, Void> beeTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            spawnInARandomAround(level, entity, EntityType.BEE, 3, 1);

            return null;
        };
    }

    public static Function<TeaInfo, Void> drivingInMyBeer() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof Player player){
                player.addEffect(AllTeas.teaEffectS(TeaEffects.CRAWL.get(), 20));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> blazeTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            entity.setSecondsOnFire(10);

            return null;
        };
    }

    public static Function<TeaInfo, Void> communistTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof ServerPlayer player){
                player.connection.send(new ClientboundSetTitleTextPacket(
                        Component.literal("☭").withStyle(ChatFormatting.RED)
                ));
                player.connection.send(new ClientboundSetTitlesAnimationPacket(20, 60, 20));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> slimeTea(int sec) {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof Player player){
                player.addEffect(AllTeas.teaEffectS(TeaEffects.SLIMENESS.get(), sec));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> cyberTea(int sec) {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof Player player){
                player.addEffect(AllTeas.teaEffectS(TeaEffects.REDSTONES.get(), sec));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> fishTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            additionalTeaFunctions.spawnInARandomAround(level, entity, EntityType.SALMON, 3, 1);

            return null;
        };
    }

    public static Function<TeaInfo, Void> moltenFishTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            additionalTeaFunctions.spawnInARandomAround(level, entity, EntityType.PUFFERFISH, 3, 1);

            return null;
        };
    }

    public static Function<TeaInfo, Void> netherness(int sec) {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof Player player){
                player.addEffect(AllTeas.teaEffectS(TeaEffects.NETHERNESS.get(), sec));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> pfTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof Player player){
                Item boneMeal = Items.BONE_MEAL;
                boneMeal.useOn(new UseOnContext(level, player, InteractionHand.MAIN_HAND, new ItemStack(boneMeal),
                        new BlockHitResult(player.blockPosition().below().getCenter().add(new Vec3(0, 0.5, 0)), Direction.UP, player.blockPosition().below(), false)));
                boneMeal.useOn(new UseOnContext(level, player, InteractionHand.MAIN_HAND, new ItemStack(boneMeal),
                        new BlockHitResult(player.blockPosition().getCenter().add(new Vec3(0, 0.5, 0)), Direction.UP, player.blockPosition(), false)));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> rotateTea() {
        return (info) -> {
            LivingEntity entity = info.entity;

            if(entity instanceof Player player){
                if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == info.stack.getItem()){
                    if(player.isShiftKeyDown()){
                        player.setYRot(player.getYRot() - 10);
                    } else {
                        player.setYRot(player.getYRot() + 10);
                    }
                } else {
                    if(player.isShiftKeyDown()){
                        player.setXRot(player.getXRot() - 10);
                    } else {
                        player.setXRot(player.getXRot() + 10);
                    }
                }
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> schematicTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof ServerPlayer player){
                player.connection.send(new ClientboundSetTitleTextPacket(
                        Component.literal("DOWNLOAD SCHEMATIC").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.UNDERLINE)
                ));
                player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("Click here to download")));
                player.connection.send(new ClientboundSetTitlesAnimationPacket(20, 60, 20));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> sticerTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof Player player){
                player.addItem(new ItemStack(Items.PAPER).setHoverName(Component.literal("sticer")));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> susTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            if(entity instanceof ServerPlayer player){
                player.connection.send(new ClientboundSetTitleTextPacket(
                        Component.literal("SUS").withStyle(ChatFormatting.RED)
                ));
                player.connection.send(new ClientboundSetTitlesAnimationPacket(20, 60, 20));
            }

            return null;
        };
    }

    public static Function<TeaInfo, Void> defestoTea(int s) {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            entity.addEffect(AllTeas.teaEffectS(TeaEffects.DEFFESTO.get(), s));

            return null;
        };
    }

    public static Function<TeaInfo, Void> exampleTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            Level level = info.level;
            ItemStack stack = info.stack;

            return null;
        };
    }

    public static void putAction(String name, Function<TeaInfo, Void> function){
        allTeaActions.put(Createteas.ID + ":" + name, function);
    }

    public static void register(){}
}
