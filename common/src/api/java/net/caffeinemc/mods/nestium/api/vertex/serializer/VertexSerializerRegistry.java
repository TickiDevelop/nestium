package net.caffeinemc.mods.nestium.api.vertex.serializer;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.caffeinemc.mods.nestium.api.internal.DependencyInjection;

public interface VertexSerializerRegistry {
    VertexSerializerRegistry INSTANCE = DependencyInjection.load(VertexSerializerRegistry.class,
            "net.caffeinemc.mods.nestium.client.render.vertex.serializers.VertexSerializerRegistryImpl");

    static VertexSerializerRegistry instance() {
        return INSTANCE;
    }

    VertexSerializer get(VertexFormat srcFormat, VertexFormat dstFormat);

    void registerSerializer(VertexFormat srcFormat, VertexFormat dstFormat, VertexSerializer serializer);
}
