#version 120 // версия шейдера
varying vec2 texcoord; // текстурная координата
uniform float randomUniform;
uniform float dislocForce;
varying float random;

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

float rand(float cof){
vec2 co = vec2(cof);
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main() {
    random += randomUniform;
    //vec3 vertdisloc = vec3(rand(vec2(random * 1.359)) - 0.5, rand(vec2(random * 1.4875)) - 0.5, rand(vec2(random * 1.2316)) - 0.5);
    vec3 vertdisloc = vec3(rand(vec2(randomUniform * 1.359)) - 0.5, rand(vec2(randomUniform * 1.4875)) - 0.5, rand(vec2(randomUniform * 1.2316)) - 0.5);
    vertdisloc *= dislocForce;
    
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex + vec4(vertdisloc, 0.);  // находим позицию вертекса, перемножая его на матрицу проекции
    //vec2 disloc = vec2(rand(vec2(random)), rand(vec2(random * 2.5142)));
    texcoord = vec2(gl_MultiTexCoord0);// + disloc;
}