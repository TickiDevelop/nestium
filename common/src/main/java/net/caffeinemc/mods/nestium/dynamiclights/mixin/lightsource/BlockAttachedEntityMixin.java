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
import net.caffeinemc.mods.nestium.client.NestiumClientMod;
import net.caffeinemc.mods.nestium.dynamiclights.api.DynamicLightHandlers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.decoration.HangingEntity;

@Mixin(HangingEntity.class)
public abstract class BlockAttachedEntityMixin extends Entity implements DynamicLightSource {
	public BlockAttachedEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Override
	public void tick() {
		// We do not want to update the entity on the server.
		if (this.level().isClientSide()) {
			if (this.isRemoved()) {
				this.ndl$setDynamicLightEnabled(false);
			} else {
				if (!NestiumClientMod.options().dynamicLights.entities || !DynamicLightHandlers.canLightUp(this))
					this.ndl$resetDynamicLight();
				else
					this.ndl$dynamicLightTick();
				NestiumDynamicLights.updateTracking(this);
			}
		}
	}
}
