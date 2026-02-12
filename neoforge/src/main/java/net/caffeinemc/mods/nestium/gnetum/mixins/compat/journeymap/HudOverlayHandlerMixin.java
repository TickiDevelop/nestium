package net.caffeinemc.mods.nestium.gnetum.mixins.compat.journeymap;

import net.caffeinemc.mods.nestium.gnetum.Gnetum;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "journeymap.client.event.handlers.HudOverlayHandler", remap = false)
public class HudOverlayHandlerMixin {
	@Inject(method = "renderWaypointDecos", at = @At("HEAD"), cancellable = true)
	private void gnetum$cancelRenderWaypointDecos(GuiGraphics graphics, CallbackInfo ci) {
		if (Gnetum.rendering) {
			ci.cancel();
		}
	}
}
