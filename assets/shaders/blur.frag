#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
uniform sampler2D u_texture;
uniform vec2 u_dir;      // (1.0, 0.0) = blur ngang, (0.0, 1.0) = blur dọc
uniform float u_radius;  // bán kính blur (số pixel mẫu)

void main() {
    vec4 sum = vec4(0.0);
    float blurSize = u_radius / 512.0; // 512 = chiều rộng/chiều cao ảnh, bạn có thể thay đổi

    sum += texture2D(u_texture, v_texCoord - 4.0 * u_dir * blurSize) * 0.05;
    sum += texture2D(u_texture, v_texCoord - 3.0 * u_dir * blurSize) * 0.09;
    sum += texture2D(u_texture, v_texCoord - 2.0 * u_dir * blurSize) * 0.12;
    sum += texture2D(u_texture, v_texCoord - 1.0 * u_dir * blurSize) * 0.15;
    sum += texture2D(u_texture, v_texCoord) * 0.16;
    sum += texture2D(u_texture, v_texCoord + 1.0 * u_dir * blurSize) * 0.15;
    sum += texture2D(u_texture, v_texCoord + 2.0 * u_dir * blurSize) * 0.12;
    sum += texture2D(u_texture, v_texCoord + 3.0 * u_dir * blurSize) * 0.09;
    sum += texture2D(u_texture, v_texCoord + 4.0 * u_dir * blurSize) * 0.05;

    gl_FragColor = sum;
}
