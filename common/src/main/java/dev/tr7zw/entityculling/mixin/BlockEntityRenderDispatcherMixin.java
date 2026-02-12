package dev.tr7zw.entityculling.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import dev.tr7zw.entityculling.EntityCullingClient;
import dev.tr7zw.entityculling.versionless.access.Cullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V", at = @At("HEAD"), cancellable = true)
    public <E extends BlockEntity> void render(E blockEntity, float f, PoseStack poseStack,
            MultiBufferSource multiBufferSource, CallbackInfo info) {
        if (EntityCullingClient.instance.config.skipBlockEntityCulling) {
            return;
        }
        BlockEntityRenderer<E> blockEntityRenderer = getRenderer(blockEntity);
        if (blockEntityRenderer == null) {
            return;
        }
        if (blockEntityRenderer.shouldRenderOffScreen(blockEntity)) {
            EntityCullingClient.instance.renderedBlockEntities++;
            return;
        }

        if (!((Cullable) blockEntity).isForcedVisible() && ((Cullable) blockEntity).isCulled()) {
            EntityCullingClient.instance.skippedBlockEntities++;
            info.cancel();
            return;
        }
        EntityCullingClient.instance.renderedBlockEntities++;
    }

    @Shadow
    public abstract <E extends BlockEntity> BlockEntityRenderer<E> getRenderer(E blockEntity);

}
