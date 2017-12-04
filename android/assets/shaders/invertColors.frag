#ifdef GL_ES
    #define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
void main(){
    //как и в стандартном шейдере получаем итоговый цвет пикселя
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
    //после получения итогового цвета, меняем его на противоположный
    gl_FragColor.rgb=1.0-gl_FragColor.rgb;
}