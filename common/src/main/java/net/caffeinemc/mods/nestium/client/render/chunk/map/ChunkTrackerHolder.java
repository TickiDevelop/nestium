package net.caffeinemc.mods.nestium.client.render.chunk.map;

import net.minecraft.client.multiplayer.ClientLevel;

public interface ChunkTrackerHolder {
    static ChunkTracker get(ClientLevel level) {
        return ((ChunkTrackerHolder) level).nestium$getTracker();
    }

    ChunkTracker nestium$getTracker();
}
