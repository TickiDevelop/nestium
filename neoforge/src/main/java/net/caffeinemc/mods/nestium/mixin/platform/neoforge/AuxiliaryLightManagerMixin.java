package net.caffeinemc.mods.nestium.mixin.platform.neoforge;

import net.caffeinemc.mods.nestium.client.world.NestiumAuxiliaryLightManager;
import net.neoforged.neoforge.common.world.AuxiliaryLightManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AuxiliaryLightManager.class)
public interface AuxiliaryLightManagerMixin extends NestiumAuxiliaryLightManager {
}
