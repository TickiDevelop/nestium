package dev.tr7zw.entityculling.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import dev.tr7zw.entityculling.EntityCullingClient;
import dev.tr7zw.entityculling.NMSCullingHelper;
import dev.tr7zw.entityculling.access.EntityRendererInter;
import dev.tr7zw.entityculling.versionless.access.Cullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

    private EntityRenderDispatcher entityCulling$entityRenderDispatcher = Minecraft.getInstance()
            .getEntityRenderDispatcher();

    @Inject(at = @At("HEAD"), method = "renderEntity", cancellable = true)
    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta,
            PoseStack matrices, MultiBufferSource vertexConsumers, CallbackInfo info) {
        if (EntityCullingClient.instance.config.skipEntityCulling) {
            return;
        }
        Cullable cullable = (Cullable) entity;
        if (!cullable.isForcedVisible() && cullable.isCulled() && !NMSCullingHelper.ignoresCulling(entity)) {
            EntityRenderer<?> entityRenderer = this.entityCulling$entityRenderDispatcher.getRenderer(entity);
            if (entityRenderer instanceof EntityRendererInter entityRendererInter) {
                if (EntityCullingClient.instance.config.renderNametagsThroughWalls && matrices != null
                        && vertexConsumers != null && entityRendererInter.shadowShouldShowName(entity)) {
                    double x = Mth.lerp((double) tickDelta, (double) entity.xOld, (double) entity.getX()) - cameraX;
                    double y = Mth.lerp((double) tickDelta, (double) entity.yOld, (double) entity.getY()) - cameraY;
                    double z = Mth.lerp((double) tickDelta, (double) entity.zOld, (double) entity.getZ()) - cameraZ;
                    var vec3d = NMSCullingHelper.getRenderOffset(entityRenderer, entity, tickDelta);
                    double d = x + vec3d.x;
                    double e = y + vec3d.y;
                    double f = z + vec3d.z;
                    matrices.pushPose();
                    matrices.translate(d, e, f);
                    entityRendererInter.shadowRenderNameTag(entity, entity.getDisplayName(), matrices, vertexConsumers,
                            this.entityCulling$entityRenderDispatcher.getPackedLightCoords(entity, tickDelta),
                            tickDelta);
                    matrices.popPose();
                }
            }
            EntityCullingClient.instance.skippedEntities++;
            info.cancel();
            return;
        }
        EntityCullingClient.instance.renderedEntities++;
        cullable.setOutOfCamera(false);
    }

}
