package net.caffeinemc.mods.nestium.mixin.core.render;

import net.caffeinemc.mods.nestium.client.render.texture.SpriteFinderCache;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureAtlas.class)
public class TextureAtlasMixin {
    @Shadow
    @Final
    private ResourceLocation location;

    @Inject(method = "upload", at = @At("RETURN"))
    private void nestium$deleteSpriteFinder(SpriteLoader.Preparations preparations, CallbackInfo ci) {
        if (this.location.equals(TextureAtlas.LOCATION_BLOCKS)) {
            SpriteFinderCache.resetSpriteFinder();
        }
    }
}
