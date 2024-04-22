precision mediump float;

uniform sampler2D u_TextureUnit_exp;
varying vec2 v_Texture_exp;

void main() {
    gl_FragColor = texture2D(u_TextureUnit_exp, v_Texture_exp);
}
