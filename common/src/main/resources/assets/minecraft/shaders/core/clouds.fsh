#version 150

#moj_import <fog.glsl>

uniform mat4 ProjMat;
uniform vec4 ColorModulator;
uniform vec4 FogColor;
uniform float FogStart;
uniform float FogEnd;

in vec4 vertexColor;
in float vertexDistance;

out vec4 fragColor;

// Custom cloud fog algorithm by Balint, for use in Nestium
void main() {
    vec4 color = vertexColor * ColorModulator;

    if (color.a < 0.1) {
        discard;
    }

    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
