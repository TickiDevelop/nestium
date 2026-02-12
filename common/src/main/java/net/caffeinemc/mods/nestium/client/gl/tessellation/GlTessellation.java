package net.caffeinemc.mods.nestium.client.gl.tessellation;

import net.caffeinemc.mods.nestium.client.gl.device.CommandList;

public interface GlTessellation {
    void delete(CommandList commandList);

    void bind(CommandList commandList);

    void unbind(CommandList commandList);

    GlPrimitiveType getPrimitiveType();
}
