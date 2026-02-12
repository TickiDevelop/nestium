package net.caffeinemc.mods.nestium.client.render.chunk.vertex.format;

import net.caffeinemc.mods.nestium.client.gl.attribute.GlVertexFormat;

public interface ChunkVertexType {
    GlVertexFormat getVertexFormat();

    ChunkVertexEncoder getEncoder();
}
