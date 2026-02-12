package net.caffeinemc.mods.nestium.client.gl.buffer;

import net.caffeinemc.mods.nestium.client.gl.attribute.GlVertexFormat;
import net.caffeinemc.mods.nestium.client.util.NativeBuffer;

/**
 * Helper type for tagging the vertex format alongside the raw buffer data.
 */
public record IndexedVertexData(GlVertexFormat vertexFormat,
                                NativeBuffer vertexBuffer,
                                NativeBuffer indexBuffer) {
    public void delete() {
        this.vertexBuffer.free();
        this.indexBuffer.free();
    }
}
