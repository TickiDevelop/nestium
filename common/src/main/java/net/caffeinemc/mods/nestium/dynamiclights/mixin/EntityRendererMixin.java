/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of NestiumDynamicLights.
 *
 * Licensed under the MIT License. For more information,
 * see the LICENSE file.
 */

package net.caffeinemc.mods.nestium.dynamiclights.mixin;

import net.caffeinemc.mods.nestium.dynamiclights.DynamicLightSource;
import net.caffeinemc.mods.nestium.dynamiclights.NestiumDynamicLights;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
	@Inject(method = "getBlockLightLevel", at = @At("RETURN"), cancellable = true)
	private void onGetBlockLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
		if (!net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.mode.isEnabled())
			return; // Do not touch to the value.

		int vanilla = cir.getReturnValueI();
		int entityLuminance = ((DynamicLightSource) entity).ndl$getLuminance();
		if (entityLuminance >= 15)
			cir.setReturnValue(entityLuminance);

		int posLuminance = (int) NestiumDynamicLights.get().getDynamicLightLevel(pos);

		cir.setReturnValue(Math.max(Math.max(vanilla, entityLuminance), posLuminance));
	}
}
