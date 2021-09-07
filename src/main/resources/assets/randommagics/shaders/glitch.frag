#version 120
varying vec2 texcoord;
varying float random;
uniform sampler2D texture; // текстура полигона
uniform float randomUniform;

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

vec2 rand2(float f) {
    vec2 co = vec2(f);
    return vec2(fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453), rand(vec2(f, fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453))));
}

void main() {
    vec4 original = texture2D(texture, texcoord + rand2(randomUniform)); // получаем оригинальный цвет текстуры
    float alpha = original.w;
    vec4 result = original * vec4(rand(vec2(randomUniform * 1.9564)), rand(vec2(randomUniform * 5.468)), rand(vec2(randomUniform * 12.489)), 1);
    gl_FragColor = result;
}