package net.caffeinemc.mods.nestium.mixin.platform.neoforge;

import net.caffeinemc.mods.nestium.client.services.NestiumModelData;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ModelData.class)
public class ModelDataMixin implements NestiumModelData {
}
