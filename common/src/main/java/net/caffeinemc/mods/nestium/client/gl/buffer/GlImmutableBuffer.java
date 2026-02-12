package net.caffeinemc.mods.nestium.client.gl.buffer;

import net.caffeinemc.mods.nestium.client.gl.util.EnumBitField;

public class GlImmutableBuffer extends GlBuffer {
    private final EnumBitField<GlBufferStorageFlags> flags;

    public GlImmutableBuffer(EnumBitField<GlBufferStorageFlags> flags) {
        this.flags = flags;
    }

    public EnumBitField<GlBufferStorageFlags> getFlags() {
        return this.flags;
    }
}
