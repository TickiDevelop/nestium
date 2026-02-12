/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of NestiumDynamicLights.
 *
 * Licensed under the MIT License. For more information,
 * see the LICENSE file.
 */

package net.caffeinemc.mods.nestium.dynamiclights;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a dynamic light source.
 *
 * @author LambdAurora
 * @version 3.0.0
 * @since 1.0.0
 */
public interface DynamicLightSource {
	/**
	 * Returns the dynamic light source X coordinate.
	 *
	 * @return the X coordinate
	 */
	double ndl$getDynamicLightX();

	/**
	 * Returns the dynamic light source Y coordinate.
	 *
	 * @return the Y coordinate
	 */
	double ndl$getDynamicLightY();

	/**
	 * Returns the dynamic light source Z coordinate.
	 *
	 * @return the Z coordinate
	 */
	double ndl$getDynamicLightZ();

	/**
	 * Returns the dynamic light source world.
	 *
	 * @return the world instance
	 */
	Level sdl$getDynamicLightLevel();

	/**
	 * Returns whether the dynamic light is enabled or not.
	 *
	 * @return {@code true} if the dynamic light is enabled, else {@code false}
	 */
	default boolean sdl$isDynamicLightEnabled() {
		return net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.mode.isEnabled() && NestiumDynamicLights.get().containsLightSource(this);
	}

	/**
	 * Sets whether the dynamic light is enabled or not.
	 * <p>
	 * Note: please do not call this function in your mod or you will break things.
	 *
	 * @param enabled {@code true} if the dynamic light is enabled, else {@code false}
	 */
	@ApiStatus.Internal
	default void ndl$setDynamicLightEnabled(boolean enabled) {
		this.ndl$resetDynamicLight();
		if (enabled)
			NestiumDynamicLights.get().addLightSource(this);
		else
			NestiumDynamicLights.get().removeLightSource(this);
	}

	void ndl$resetDynamicLight();

	/**
	 * Returns the luminance of the light source.
	 * The maximum is 15, below 1 values are ignored.
	 *
	 * @return the luminance of the light source
	 */
	int ndl$getLuminance();

	/**
	 * Executed at each tick.
	 */
	void ndl$dynamicLightTick();

	/**
	 * Returns whether this dynamic light source should update.
	 *
	 * @return {@code true} if this dynamic light source should update, else {@code false}
	 */
	boolean sdl$shouldUpdateDynamicLight();

	boolean nestium_dynamiclights$updateDynamicLight(@NotNull LevelRenderer renderer);

	void sodiumdynamiclights$scheduleTrackedChunksRebuild(@NotNull LevelRenderer renderer);
}
