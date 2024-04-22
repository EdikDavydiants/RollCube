precision mediump float;

uniform sampler2D u_TextureUnit_t;
varying vec2 v_Texture;

void main() {
    gl_FragColor = texture2D(u_TextureUnit_t, v_Texture);
}
