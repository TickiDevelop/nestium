package dev.tr7zw.entityculling.mixin;

import java.text.DecimalFormat;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.tr7zw.entityculling.EntityCullingClient;
import net.minecraft.client.gui.components.DebugScreenOverlay;

@Mixin(DebugScreenOverlay.class)
public class DebugHudMixin {

    private int lastTickedEntities = 0;
    private int lastSkippedEntityTicks = 0;
    private final DecimalFormat entityCullingFormatter = new DecimalFormat("###.##");

    @Inject(method = "getGameInformation", at = @At("RETURN"))
    public void getLeftText(CallbackInfoReturnable<List<String>> callback) {
        if (EntityCullingClient.instance.tickedEntities != 0
                || EntityCullingClient.instance.skippedEntityTicks != 0) {
            lastTickedEntities = EntityCullingClient.instance.tickedEntities;
            lastSkippedEntityTicks = EntityCullingClient.instance.skippedEntityTicks;
            EntityCullingClient.instance.tickedEntities = 0;
            EntityCullingClient.instance.skippedEntityTicks = 0;
        }
        
        if (EntityCullingClient.instance.config.disableF3) {
            return;
        }
        
        List<String> list = callback.getReturnValue();
        list.add(
                "[Culling] Last pass: " + entityCullingFormatter.format(EntityCullingClient.instance.cullTask.lastTime)
                        + "ms/" + entityCullingFormatter.format(EntityCullingClient.instance.lastTickTime) + "ms");
        
        if (!EntityCullingClient.instance.config.skipBlockEntityCulling) {
            list.add("[Culling] Rendered Block Entities: " + EntityCullingClient.instance.renderedBlockEntities
                    + " Skipped: " + EntityCullingClient.instance.skippedBlockEntities);
        }
        
        if (!EntityCullingClient.instance.config.skipEntityCulling) {
            list.add("[Culling] Rendered Entities: " + EntityCullingClient.instance.renderedEntities + " Skipped: "
                    + EntityCullingClient.instance.skippedEntities);
            list.add("[Culling] Ticked Entities: " + lastTickedEntities + " Skipped: " + lastSkippedEntityTicks);
        }

        EntityCullingClient.instance.renderedBlockEntities = 0;
        EntityCullingClient.instance.skippedBlockEntities = 0;
        EntityCullingClient.instance.renderedEntities = 0;
        EntityCullingClient.instance.skippedEntities = 0;
    }

}
