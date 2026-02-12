package net.caffeinemc.mods.nestium.client.gl.device;

import net.caffeinemc.mods.nestium.client.gl.tessellation.GlIndexType;

public interface DrawCommandList extends AutoCloseable {
    void multiDrawElementsBaseVertex(MultiDrawBatch batch, GlIndexType indexType);

    void endTessellating();

    void flush();

    @Override
    default void close() {
        this.flush();
    }
}
