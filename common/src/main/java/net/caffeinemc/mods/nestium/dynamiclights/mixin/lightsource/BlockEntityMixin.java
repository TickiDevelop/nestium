/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of NestiumDynamicLights.
 *
 * Licensed under the MIT License. For more information,
 * see the LICENSE file.
 */

package net.caffeinemc.mods.nestium.dynamiclights.mixin.lightsource;

import net.minecraft.util.Mth;
import net.caffeinemc.mods.nestium.dynamiclights.DynamicLightSource;
import net.caffeinemc.mods.nestium.dynamiclights.NestiumDynamicLights;
import net.caffeinemc.mods.nestium.dynamiclights.api.DynamicLightHandlers;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements DynamicLightSource {
	@Final
	@Shadow
	protected BlockPos worldPosition;

	@Shadow
	@Nullable
	protected Level level;

	@Shadow
	protected boolean remove;

	@Unique
	private int luminance = 0;
	@Unique
	private int lastLuminance = 0;
	@Unique
	private long lastUpdate = 0;
	@Unique
	private final LongOpenHashSet sodiumdynamiclights$trackedLitChunkPos = new LongOpenHashSet();

	@Override
	public double ndl$getDynamicLightX() {
		return this.worldPosition.getX() + 0.5;
	}

	@Override
	public double ndl$getDynamicLightY() {
		return this.worldPosition.getY() + 0.5;
	}

	@Override
	public double ndl$getDynamicLightZ() {
		return this.worldPosition.getZ() + 0.5;
	}

	@Override
	public Level sdl$getDynamicLightLevel() {
		return this.level;
	}

	@Inject(method = "setRemoved", at = @At("TAIL"))
	private void onRemoved(CallbackInfo ci) {
		this.ndl$setDynamicLightEnabled(false);
	}

	@Override
	public void ndl$resetDynamicLight() {
		this.lastLuminance = 0;
	}

	@Override
	public void ndl$dynamicLightTick() {
		// We do not want to update the entity on the server.
		if (this.level == null || !this.level.isClientSide())
			return;
		if (!this.remove) {
			this.luminance = DynamicLightHandlers.getLuminanceFrom((BlockEntity) (Object) this);
			NestiumDynamicLights.updateTracking(this);

			if (!this.sdl$isDynamicLightEnabled()) {
				this.lastLuminance = 0;
			}
		}
	}

	@Override
	public int ndl$getLuminance() {
		return this.luminance;
	}

	@Override
	public boolean sdl$shouldUpdateDynamicLight() {
		var mode = net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.mode;
		if (!mode.isEnabled())
			return false;
		if (mode.hasDelay()) {
			long currentTime = System.currentTimeMillis();
			if (currentTime < this.lastUpdate + mode.getDelay()) {
				return false;
			}

			this.lastUpdate = currentTime;
		}
		return true;
	}

	@Override
	public boolean nestium_dynamiclights$updateDynamicLight(@NotNull LevelRenderer renderer) {
		if (!this.sdl$shouldUpdateDynamicLight())
			return false;

		int luminance = this.ndl$getLuminance();

		if (luminance != this.lastLuminance) {
			this.lastLuminance = luminance;

			if (this.sodiumdynamiclights$trackedLitChunkPos.isEmpty()) {
				var chunkPos = new BlockPos.MutableBlockPos(Math.floorDiv(this.worldPosition.getX(), 16),
						Mth.floorDiv(this.worldPosition.getY(), 16),
						Mth.floorDiv(this.worldPosition.getZ(), 16));

				NestiumDynamicLights.updateTrackedChunks(chunkPos, null, this.sodiumdynamiclights$trackedLitChunkPos);

				var directionX = (this.worldPosition.getX() & 15) >= 8 ? Direction.EAST : Direction.WEST;
				var directionY = (this.worldPosition.getY() & 15) >= 8 ? Direction.UP : Direction.DOWN;
				var directionZ = (this.worldPosition.getZ() & 15) >= 8 ? Direction.SOUTH : Direction.NORTH;

				for (int i = 0; i < 7; i++) {
					if (i % 4 == 0) {
						chunkPos.move(directionX); // X
					} else if (i % 4 == 1) {
						chunkPos.move(directionZ); // XZ
					} else if (i % 4 == 2) {
						chunkPos.move(directionX.getOpposite()); // Z
					} else {
						chunkPos.move(directionZ.getOpposite()); // origin
						chunkPos.move(directionY); // Y
					}
					NestiumDynamicLights.updateTrackedChunks(chunkPos, null, this.sodiumdynamiclights$trackedLitChunkPos);
				}
			}

			// Schedules the rebuild of chunks.
			this.sodiumdynamiclights$scheduleTrackedChunksRebuild(renderer);
			return true;
		}
		return false;
	}

	@Override
	public void sodiumdynamiclights$scheduleTrackedChunksRebuild(@NotNull LevelRenderer renderer) {
		if (this.level == Minecraft.getInstance().level)
			for (long pos : this.sodiumdynamiclights$trackedLitChunkPos) {
				NestiumDynamicLights.scheduleChunkRebuild(renderer, pos);
			}
	}
}
