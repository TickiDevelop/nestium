/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of NestiumDynamicLights.
 *
 * Licensed under the MIT License. For more information,
 * see the LICENSE file.
 */

package net.caffeinemc.mods.nestium.dynamiclights.mixin;

import net.caffeinemc.mods.nestium.dynamiclights.NestiumDynamicLights;
import net.caffeinemc.mods.nestium.dynamiclights.accessor.WorldRendererAccessor;
import net.caffeinemc.mods.nestium.client.NestiumClientMod;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LevelRenderer.class, priority = 900)
public abstract class CommonLevelRendererMixin implements WorldRendererAccessor {
	@Invoker("setSectionDirty")
	@Override
	public abstract void sodiumdynamiclights$scheduleChunkRebuild(int x, int y, int z, boolean important);

	@Inject(
			method = "getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I",
			at = @At("TAIL"),
			cancellable = true
	)
	private static void onGetLightmapCoordinates(BlockAndTintGetter level, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
		if (!level.getBlockState(pos).isSolidRender(level, pos) && NestiumClientMod.options().dynamicLights.mode.isEnabled())
			cir.setReturnValue(NestiumDynamicLights.get().getLightmapWithDynamicLight(pos, cir.getReturnValue()));
	}
}
