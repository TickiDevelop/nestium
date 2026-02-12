package net.caffeinemc.mods.nestium.client.gl.functions;

import net.caffeinemc.mods.nestium.client.gl.device.RenderDevice;

public class DeviceFunctions {
    private final BufferStorageFunctions bufferStorageFunctions;

    public DeviceFunctions(RenderDevice device) {
        this.bufferStorageFunctions = BufferStorageFunctions.pickBest(device);
    }

    public BufferStorageFunctions getBufferStorageFunctions() {
        return this.bufferStorageFunctions;
    }
}
