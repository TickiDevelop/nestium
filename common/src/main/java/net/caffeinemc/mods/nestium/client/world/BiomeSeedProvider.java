package net.caffeinemc.mods.nestium.client.world;

import net.minecraft.client.multiplayer.ClientLevel;

public interface BiomeSeedProvider {
    static long getBiomeZoomSeed(ClientLevel level) {
        return ((BiomeSeedProvider) level).nestium$getBiomeZoomSeed();
    }

    long nestium$getBiomeZoomSeed();
}
