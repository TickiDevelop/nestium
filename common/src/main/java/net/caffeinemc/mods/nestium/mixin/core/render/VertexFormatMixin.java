package net.caffeinemc.mods.nestium.mixin.core.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import it.unimi.dsi.fastutil.ints.IntList;
import net.caffeinemc.mods.nestium.api.vertex.format.VertexFormatExtensions;
import net.caffeinemc.mods.nestium.api.vertex.format.VertexFormatRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(VertexFormat.class)
public class VertexFormatMixin implements VertexFormatExtensions {
    private int nestium$globalId;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void afterInit(List<VertexFormatElement> elements, List<String> names, IntList offsets, int vertexSize, CallbackInfo ci) {
        this.nestium$globalId = VertexFormatRegistry.instance()
                .allocateGlobalId((VertexFormat) (Object) this);
    }

    @Unique
    public int nestium$getGlobalId() {
        return this.nestium$globalId;
    }
}
