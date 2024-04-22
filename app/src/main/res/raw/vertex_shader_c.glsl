attribute vec4 a_Position_c;
uniform mat4 u_Matrix_c;


void main() {
    gl_Position = u_Matrix_c * a_Position_c;
}
