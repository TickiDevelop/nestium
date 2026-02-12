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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DynamicLightSource {
	@Shadow
	public abstract boolean isSpectator();

	@Unique
	protected int nestium_dynamiclights$luminance;
	@Unique
	private Level sodiumdynamiclights$lastWorld;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, Level level) {
		super(type, level);
	}

	@Override
	public void ndl$dynamicLightTick() {
		if (!DynamicLightHandlers.canLightUp(this)) {
			this.nestium_dynamiclights$luminance = 0;
			return;
		}

		if (this.isOnFire() || this.isCurrentlyGlowing()) {
			this.nestium_dynamiclights$luminance = 15;
		} else {
			this.nestium_dynamiclights$luminance = Math.max(
					DynamicLightHandlers.getLuminanceFrom(this),
					NestiumDynamicLights.getLivingEntityLuminanceFromItems(this)
			);
		}

		if (this.isSpectator())
			this.nestium_dynamiclights$luminance = 0;

		if (this.sodiumdynamiclights$lastWorld != this.level()) {
			this.sodiumdynamiclights$lastWorld = this.level();
			this.nestium_dynamiclights$luminance = 0;
		}
	}

	@Override
	public int ndl$getLuminance() {
		return this.nestium_dynamiclights$luminance;
	}
}
