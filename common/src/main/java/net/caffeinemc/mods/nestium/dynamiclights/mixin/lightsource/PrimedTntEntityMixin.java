/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of NestiumDynamicLights.
 *
 * Licensed under the MIT License. For more information,
 * see the LICENSE file.
 */

package net.caffeinemc.mods.nestium.dynamiclights.mixin.lightsource;

import net.caffeinemc.mods.nestium.dynamiclights.DynamicLightSource;
import net.caffeinemc.mods.nestium.dynamiclights.ExplosiveLightingMode;
import net.caffeinemc.mods.nestium.dynamiclights.NestiumDynamicLights;
import net.caffeinemc.mods.nestium.dynamiclights.api.DynamicLightHandlers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PrimedTnt.class)
public abstract class PrimedTntEntityMixin extends Entity implements DynamicLightSource {
	@Shadow
	public abstract int getFuse();

	@Unique
	private int startFuseTimer = 80;
	@Unique
	private int nestium_dynamiclights$luminance;

	public PrimedTntEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
	private void onNew(EntityType<? extends PrimedTnt> type, Level level, CallbackInfo ci) {
		this.startFuseTimer = this.getFuse();
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void onTick(CallbackInfo ci) {
		// We do not want to update the entity on the server.
		if (this.level().isClientSide()) {
			if (!net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.tnt.isEnabled())
				return;

			if (this.isRemoved()) {
				this.ndl$setDynamicLightEnabled(false);
			} else {
				if (!net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.entities || !DynamicLightHandlers.canLightUp(this))
					this.ndl$resetDynamicLight();
				else
					this.ndl$dynamicLightTick();
				NestiumDynamicLights.updateTracking(this);
			}
		}
	}

	@Override
	public void ndl$dynamicLightTick() {
		if (this.isOnFire()) {
			this.nestium_dynamiclights$luminance = 15;
		} else {
			ExplosiveLightingMode lightingMode = net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.tnt;
			if (lightingMode == ExplosiveLightingMode.FANCY) {
				var fuse = this.getFuse() / this.startFuseTimer;
				this.nestium_dynamiclights$luminance = (int) (-(fuse * fuse) * 10.0) + 10;
			} else {
				this.nestium_dynamiclights$luminance = 10;
			}
		}
	}

	@Override
	public int ndl$getLuminance() {
		return this.nestium_dynamiclights$luminance;
	}
}
