package net.caffeinemc.mods.nestium.mixin.features.options.weather;

import net.caffeinemc.mods.nestium.client.NestiumClientMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Redirect(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;useFancyGraphics()Z"))
    private boolean redirectGetFancyWeather() {
        return NestiumClientMod.options().quality.weatherQuality.isFancy(Minecraft.getInstance().options.graphicsMode().get());
    }
}