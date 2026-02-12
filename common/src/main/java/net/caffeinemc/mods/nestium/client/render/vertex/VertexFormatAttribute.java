package net.caffeinemc.mods.nestium.client.render.vertex;

import net.caffeinemc.mods.nestium.client.gl.attribute.GlVertexAttributeFormat;

public record VertexFormatAttribute(String name, GlVertexAttributeFormat format, int count, boolean normalized, boolean intType) {

}