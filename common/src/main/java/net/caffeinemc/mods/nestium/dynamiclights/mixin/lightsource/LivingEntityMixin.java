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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements DynamicLightSource {
	@Unique
	protected int nestium_dynamiclights$luminance;

	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Override
	public void ndl$dynamicLightTick() {
		if (!net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.entities || !DynamicLightHandlers.canLightUp(this)) {
			this.nestium_dynamiclights$luminance = 0;
			return;
		}

		if (this.isOnFire() || this.isCurrentlyGlowing()) {
			this.nestium_dynamiclights$luminance = 15;
		} else {
			this.nestium_dynamiclights$luminance = NestiumDynamicLights.getLivingEntityLuminanceFromItems((LivingEntity) (Object) this);
		}

		int luminance = DynamicLightHandlers.getLuminanceFrom(this);
		if (luminance > this.nestium_dynamiclights$luminance)
			this.nestium_dynamiclights$luminance = luminance;
	}

	@Override
	public int ndl$getLuminance() {
		return this.nestium_dynamiclights$luminance;
	}
}
