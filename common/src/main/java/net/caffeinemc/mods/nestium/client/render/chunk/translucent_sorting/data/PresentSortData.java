package net.caffeinemc.mods.nestium.client.render.chunk.translucent_sorting.data;

import net.caffeinemc.mods.nestium.client.util.NativeBuffer;

import java.nio.IntBuffer;

public interface PresentSortData {
    NativeBuffer getIndexBuffer();

    default IntBuffer getIntBuffer() {
        return this.getIndexBuffer().getDirectBuffer().asIntBuffer();
    }
}
