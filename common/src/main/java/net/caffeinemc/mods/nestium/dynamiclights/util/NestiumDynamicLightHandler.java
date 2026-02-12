/*
 * Copyright © 2023 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of NestiumDynamicLights.
 *
 * Licensed under the MIT License. For more information,
 * see the LICENSE file.
 */

package net.caffeinemc.mods.nestium.dynamiclights.util;

import net.caffeinemc.mods.nestium.dynamiclights.NestiumDynamicLights;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface NestiumDynamicLightHandler {
	// Stores the current light position being used by ArrayLightDataCache#get
	// We use ThreadLocal because Sodium's chunk builder is multithreaded, otherwise it will break
	// catastrophically.
	ThreadLocal<BlockPos.MutableBlockPos> POS = ThreadLocal.withInitial(BlockPos.MutableBlockPos::new);

	static int getLightmap(BlockPos pos, int word, int lightmap) {
		if (!net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.mode.isEnabled())
			return lightmap;

		// Equivalent to world.getBlockState(pos).isOpaqueFullCube(world, pos)
		if (/*LightDataAccess.unpackFO(word)*/ (word >>> 30 & 1) != 0)
			return lightmap;

		double dynamic = NestiumDynamicLights.get().getDynamicLightLevel(pos);
		return NestiumDynamicLights.get().getLightmapWithDynamicLight(dynamic, lightmap);
	}
}
