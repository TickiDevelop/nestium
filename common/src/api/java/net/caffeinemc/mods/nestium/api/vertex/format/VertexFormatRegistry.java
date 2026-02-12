package net.caffeinemc.mods.nestium.api.vertex.format;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.caffeinemc.mods.nestium.api.internal.DependencyInjection;

public interface VertexFormatRegistry {
    VertexFormatRegistry INSTANCE = DependencyInjection.load(VertexFormatRegistry.class,
            "net.caffeinemc.mods.nestium.client.render.vertex.VertexFormatRegistryImpl");

    static VertexFormatRegistry instance() {
        return INSTANCE;
    }

    int allocateGlobalId(VertexFormat format);
}