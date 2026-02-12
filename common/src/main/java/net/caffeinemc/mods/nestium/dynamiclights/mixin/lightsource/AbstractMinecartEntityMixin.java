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
import net.caffeinemc.mods.nestium.dynamiclights.NestiumDynamicLights;
import net.caffeinemc.mods.nestium.dynamiclights.api.DynamicLightHandlers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Adds the tick method for dynamic light source tracking in minecart entities.
 *
 * @author LambdAurora
 * @version 2.0.2
 * @since 1.3.2
 */
@Mixin(AbstractMinecart.class)
public abstract class AbstractMinecartEntityMixin extends Entity implements DynamicLightSource {
	@Shadow
	public abstract BlockState getDisplayBlockState();

	@Unique
	private int nestium_dynamiclights$luminance;

	public AbstractMinecartEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void onTick(CallbackInfo ci) {
		// We do not want to update the entity on the server.
		if (this.level().isClientSide()) {
			if (this.isRemoved()) {
				this.ndl$setDynamicLightEnabled(false);
			} else {
				if (!net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.entities || !DynamicLightHandlers.canLightUp(this))
					this.nestium_dynamiclights$luminance = 0;
				else
					this.ndl$dynamicLightTick();
				NestiumDynamicLights.updateTracking(this);
			}
		}
	}

	@Override
	public void ndl$dynamicLightTick() {
		this.nestium_dynamiclights$luminance = Math.max(
				Math.max(
						this.isOnFire() ? 15 : 0,
						this.getDisplayBlockState().getLightEmission()
				),
				DynamicLightHandlers.getLuminanceFrom(this)
		);
	}

	@Override
	public int ndl$getLuminance() {
		return this.nestium_dynamiclights$luminance;
	}
}
