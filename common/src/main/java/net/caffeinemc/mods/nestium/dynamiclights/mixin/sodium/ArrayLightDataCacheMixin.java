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
import net.caffeinemc.mods.nestium.client.NestiumClientMod;
import net.caffeinemc.mods.nestium.client.model.light.data.ArrayLightDataCache;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ArrayLightDataCache.class, remap = false)
public abstract class ArrayLightDataCacheMixin {

	@Dynamic
	@Inject(method = "get(III)I", at = @At("HEAD"), require = 0)
	private void sodiumdynamiclights$storeLightPos(int x, int y, int z, CallbackInfoReturnable<Integer> cir) {
		if (!NestiumClientMod.options().dynamicLights.mode.isEnabled())
			return;

		// Store the current light position.
		// This is possible under smooth lighting scenarios, because AoFaceData in Sodium runs a get() call
		// before getting the lightmap.
		NestiumDynamicLightHandler.POS.get().set(x, y, z);
	}
}
