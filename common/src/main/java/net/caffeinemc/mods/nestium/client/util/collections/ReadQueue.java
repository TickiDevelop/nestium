package net.caffeinemc.mods.nestium.client.util.collections;

import org.jetbrains.annotations.Nullable;

public interface ReadQueue<E> {
    @Nullable E dequeue();
}
