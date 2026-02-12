package net.caffeinemc.mods.nestium.client.gl.arena.staging;

import net.caffeinemc.mods.nestium.client.gl.buffer.GlBuffer;
import net.caffeinemc.mods.nestium.client.gl.device.CommandList;

import java.nio.ByteBuffer;

public interface StagingBuffer {
    void enqueueCopy(CommandList commandList, ByteBuffer data, GlBuffer dst, long writeOffset);

    void flush(CommandList commandList);

    void delete(CommandList commandList);

    void flip();
}
