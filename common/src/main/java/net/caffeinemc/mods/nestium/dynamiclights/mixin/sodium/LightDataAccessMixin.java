/*
 * Copyright © 2023 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of NestiumDynamicLights.
 *
 * Licensed under the MIT License. For more information,
 * see the LICENSE file.
 */

package net.caffeinemc.mods.nestium.dynamiclights.mixin.sodium;

import net.caffeinemc.mods.nestium.dynamiclights.util.NestiumDynamicLightHandler;
import net.caffeinemc.mods.nestium.client.model.light.data.LightDataAccess;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LightDataAccess.class, remap = false)
public abstract class LightDataAccessMixin {
	@Dynamic
	@Inject(method = "getLightmap", at = @At("RETURN"), require = 0, cancellable = true)
	private static void sodiumdynamiclights$getLightmap(int word, CallbackInfoReturnable<Integer> cir) {
		int lightmap = NestiumDynamicLightHandler.getLightmap(NestiumDynamicLightHandler.POS.get(), word, cir.getReturnValueI());
		cir.setReturnValue(lightmap);
	}
}
