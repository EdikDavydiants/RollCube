attribute vec4 a_Position_exp;
uniform mat4 u_Matrix_exp;
attribute vec2 a_Texture_exp;
varying vec2 v_Texture_exp;


void main() {
    gl_Position = u_Matrix_exp * a_Position_exp;
    v_Texture_exp = a_Texture_exp;
}
