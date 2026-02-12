package net.caffeinemc.mods.nestium.mixin.neoforge;

import net.caffeinemc.mods.nestium.client.world.LevelRendererExtension;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "iterateVisibleBlockEntities", at = @At("HEAD"), cancellable = true)
    public void replaceBlockEntityIteration(Consumer<BlockEntity> blockEntityConsumer, CallbackInfo ci) {
        ci.cancel();

        ((LevelRendererExtension) this).nestium$getWorldRenderer().iterateVisibleBlockEntities(blockEntityConsumer);
    }
}
