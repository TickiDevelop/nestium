package net.caffeinemc.mods.nestium.api.vertex.serializer;

public interface VertexSerializer {
    void serialize(long srcBuffer, long dstBuffer, int count);
}
