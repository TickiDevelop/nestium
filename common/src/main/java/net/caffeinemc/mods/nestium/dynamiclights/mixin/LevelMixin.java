/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of NestiumDynamicLights.
 *
 * Licensed under the MIT License. For more information,
 * see the LICENSE file.
 */

package net.caffeinemc.mods.nestium.dynamiclights.mixin;

import net.minecraft.util.profiling.ProfilerFiller;

import net.caffeinemc.mods.nestium.dynamiclights.DynamicLightSource;
import net.caffeinemc.mods.nestium.dynamiclights.NestiumDynamicLights;
import net.caffeinemc.mods.nestium.client.NestiumClientMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(Level.class)
public abstract class LevelMixin {
	@Shadow
	public abstract boolean isClientSide();

	@Shadow
	public abstract @Nullable BlockEntity getBlockEntity(BlockPos pos);

	@Inject(
			method = "tickBlockEntities",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/block/entity/TickingBlockEntity;tick()V",
					shift = At.Shift.BEFORE
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void onBlockEntityTick(CallbackInfo ci, ProfilerFiller profiler, Iterator iter, boolean bl, TickingBlockEntity blockEntityTickInvoker) {
		if (this.isClientSide() && NestiumClientMod.options().dynamicLights.blockEntities) {
			var blockEntity = this.getBlockEntity(blockEntityTickInvoker.getPos());
			if (blockEntity != null)
				((DynamicLightSource) blockEntity).ndl$dynamicLightTick();
		}
	}
}
