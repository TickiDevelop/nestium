/*
 * Copyright © 2021 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of NestiumDynamicLights.
 *
 * Licensed under the MIT License. For more information,
 * see the LICENSE file.
 */

package net.caffeinemc.mods.nestium.dynamiclights.mixin;

import net.minecraft.network.chat.Component;
import net.caffeinemc.mods.nestium.dynamiclights.NestiumDynamicLights;
import net.caffeinemc.mods.nestium.dynamiclights.accessor.DynamicLightHandlerHolder;
import net.caffeinemc.mods.nestium.dynamiclights.api.DynamicLightHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin<T extends BlockEntity> implements DynamicLightHandlerHolder<T> {
	@Unique
	private DynamicLightHandler<T> sodiumdynamiclights$lightHandler;
	@Unique
	private Boolean sodiumdynamiclights$setting;

	@Override
	public @Nullable DynamicLightHandler<T> sodiumdynamiclights$getDynamicLightHandler() {
		return this.sodiumdynamiclights$lightHandler;
	}

	@Override
	public void sodiumdynamiclights$setDynamicLightHandler(DynamicLightHandler<T> handler) {
		this.sodiumdynamiclights$lightHandler = handler;
	}

	@Override
	public boolean sodiumdynamiclights$getSetting() {
		if (this.sodiumdynamiclights$setting == null) {
			var self = (BlockEntityType<?>) (Object) this;
			var id = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(self);
			if (id == null) {
				return false;
			}

			var path = "light_sources.settings.block_entities." + id.getNamespace() + '.' + id.getPath().replace('/', '.');

			var config = net.caffeinemc.mods.nestium.client.NestiumClientMod.options().dynamicLights.entitiesSettings;
			if (!config.containsKey(path)) {
				this.sodiumdynamiclights$setting = false;
				return false;
			}

			this.sodiumdynamiclights$setting = config.getOrDefault(path, false);
		}

		return this.sodiumdynamiclights$setting;
	}

	@Override
	public Component sodiumdynamiclights$getName() {
		var self = (BlockEntityType<?>) (Object) this;
		var id = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(self);
		if (id == null) {
			return Component.empty();
		}
		return Component.literal(id.getNamespace() + ':' + id.getPath());
	}
}
