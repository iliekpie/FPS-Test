#version 150 core

uniform mat4 MVP;

in vec4 in_VertexPosition;
in vec4 in_Color;

out vec4 pass_Color;

void main(void) {
    gl_Position = MVP * in_VertexPosition;
    pass_Color = in_Color;
}