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
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractHurtingProjectile.class)
public abstract class AbstractHurtingProjectileEntityMixin extends Entity implements DynamicLightSource {
	public AbstractHurtingProjectileEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Override
	public void ndl$dynamicLightTick() {
		if (!this.sdl$isDynamicLightEnabled())
			this.ndl$setDynamicLightEnabled(true);
	}

	@Override
	public int ndl$getLuminance() {
		if (net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.entities && DynamicLightHandlers.canLightUp(this))
			return 14;
		return 0;
	}
}
