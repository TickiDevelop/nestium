package net.caffeinemc.mods.nestium.mixin.platform.neoforge;

import net.caffeinemc.mods.nestium.client.render.frapi.render.AbstractBlockRenderContext;
import net.caffeinemc.mods.nestium.client.services.NestiumModelData;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlockRenderContext.class)
public abstract class AbstractBlockRenderContextMixin implements RenderContext {
    @Shadow
    protected RenderType type;

    @Shadow
    protected NestiumModelData modelData;

    @Override
    public ModelData getModelData() {
        return (ModelData) (Object) this.modelData;
    }

    @Override
    public RenderType getRenderType() {
        return type;
    }
}
