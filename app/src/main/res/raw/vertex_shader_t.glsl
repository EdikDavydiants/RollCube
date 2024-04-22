attribute vec4 a_Position_t;
uniform mat4 u_Matrix_t;
attribute vec2 a_Texture_t;
varying vec2 v_Texture;


void main() {
    gl_Position = u_Matrix_t * a_Position_t;
    v_Texture = a_Texture_t;
}
