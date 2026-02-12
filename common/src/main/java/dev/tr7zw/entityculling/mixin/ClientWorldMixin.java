package dev.tr7zw.entityculling.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.tr7zw.entityculling.EntityCullingClient;
import dev.tr7zw.entityculling.NMSCullingHelper;
import dev.tr7zw.entityculling.versionless.access.Cullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

@Mixin(ClientLevel.class)
public class ClientWorldMixin {

    private Minecraft mc = Minecraft.getInstance();

    @Inject(method = "tickNonPassenger", at = @At("HEAD"), cancellable = true)
    public void tickEntity(Entity entity, CallbackInfo info) {
        if (!EntityCullingClient.instance.config.tickCulling
                || EntityCullingClient.instance.config.skipEntityCulling) {
            EntityCullingClient.instance.tickedEntities++;
            return;
        }
        
        if (NMSCullingHelper.ignoresCulling(entity) || entity == mc.player
                || entity == mc.cameraEntity || entity.isPassenger() || entity.isVehicle()
                || (entity instanceof AbstractMinecart)) {
            EntityCullingClient.instance.tickedEntities++;
            return;
        }
        
        if (EntityCullingClient.instance.tickCullWhistelist.contains(entity.getType())
                || EntityCullingClient.instance.entityWhitelist.contains(entity.getType())) {
            EntityCullingClient.instance.tickedEntities++;
            return;
        }
        
        if (entity instanceof Cullable) {
            Cullable cull = (Cullable) entity;
            if (cull.isCulled() || cull.isOutOfCamera()) {
                basicTick(entity);
                EntityCullingClient.instance.skippedEntityTicks++;
                info.cancel();
                return;
            } else {
                cull.setOutOfCamera(true);
            }
        }
        EntityCullingClient.instance.tickedEntities++;
    }

    private void basicTick(Entity entity) {
        entity.setOldPosAndRot();
        ++entity.tickCount;
        if (entity instanceof LivingEntity living) {
            living.aiStep();
            if (living.hurtTime > 0)
                living.hurtTime--;
        }
        
        if (entity instanceof Warden warden) {
            if (mc.level.isClientSide() && !warden.isSilent()
                    && warden.tickCount % getWardenHeartBeatDelay(warden) == 0) {
                mc.level.playLocalSound(warden.getX(), warden.getY(), warden.getZ(), SoundEvents.WARDEN_HEARTBEAT,
                        warden.getSoundSource(), 5.0F, warden.getVoicePitch(), false);
            }
        }
    }

    private int getWardenHeartBeatDelay(Warden warden) {
        float f = warden.getClientAngerLevel() / AngerLevel.ANGRY.getMinimumAnger();
        return 40 - Mth.floor(Mth.clamp(f, 0.0F, 1.0F) * 30.0F);
    }

}
