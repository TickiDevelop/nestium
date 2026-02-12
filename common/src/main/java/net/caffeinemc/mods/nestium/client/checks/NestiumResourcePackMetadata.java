package net.caffeinemc.mods.nestium.client.checks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.server.packs.metadata.MetadataSectionType;

/**
 * Reads additional metadata for Nestium from a resource pack's `pack.mcmeta` file. This allows the
 * resource pack author to specify which shaders from their pack are not usable with Nestium, but that
 * the author is aware of and is fine with being ignored.
 */
public record NestiumResourcePackMetadata(List<String> ignoredShaders) {
    public static final Codec<NestiumResourcePackMetadata> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(Codec.STRING.listOf().fieldOf("ignored_shaders")
                    .forGetter(NestiumResourcePackMetadata::ignoredShaders))
                    .apply(instance, NestiumResourcePackMetadata::new)
    );
    public static final MetadataSectionType<NestiumResourcePackMetadata> SERIALIZER =
            MetadataSectionType.fromCodec("nestium", CODEC);
}
