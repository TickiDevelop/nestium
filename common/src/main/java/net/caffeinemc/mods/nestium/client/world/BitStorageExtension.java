package net.caffeinemc.mods.nestium.client.world;

import net.minecraft.world.level.chunk.Palette;

public interface BitStorageExtension {
    <T> void nestium$unpack(T[] out, Palette<T> palette);
}
