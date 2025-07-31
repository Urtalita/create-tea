package org.Portality.createteas.items;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ChorusFruitItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.Portality.createteas.Createteas;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.function.Function;

import static com.simibubi.create.content.logistics.box.PackageItem.getPackageVelocity;

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

    public static Function<TeaInfo, Void> lavaTea() {
        return (info) -> {
            LivingEntity entity = info.entity;
            entity.setSecondsOnFire(10);
            DamageSources damageSources = info.level.damageSources();
            entity.hurt(damageSources.lava(), 4.0f);
            return null;
        };
    }

    public static void putAction(String name, Function<TeaInfo, Void> function){
        allTeaActions.put(Createteas.ID + ":" + name, function);
    }

    public static void register(){}
}
