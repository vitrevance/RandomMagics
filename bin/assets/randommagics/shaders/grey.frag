#version 120
varying vec2 texcoord;
uniform sampler2D texture;
//varying vec4 color;

void main() {
    vec4 color = texture2D(texture, texcoord);
    gl_FragColor = vec4(vec3(sqrt((color.x * color.x + color.y * color.y + color.z * color.z))/6.), color.w);
}