package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.graphics.OrthographicCamera;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Voland on 24.08.2017.
 */

public class Shaker {
    private final static float SPEED_OF_COMEBACK=1.8f;//скорость возвращения экрана после шейка в обыное состояние
    private OrthographicCamera camera_sh;
    private float elapsed,duration,intensity;
    private float baseX=0,baseY=0;
    private float x, y;

    public Shaker(OrthographicCamera camera) {
        camera_sh=camera;
       baseX+= camera.position.x ;
       baseY+= camera.position.y ;
    }
    public void shake(float duration_in){
        elapsed=0;
        intensity=4;
        duration=duration_in;
    }
    public void inc(){
        intensity+=0.5;
        intensity=intensity+0.5f;
        duration+=0.5f;
        //duration=0,5f;
    }

    public OrthographicCamera getCamera_sh() {
        return camera_sh;
    }

    public void update(float delta) {

        // Only shake when required.
        if(elapsed < duration) {

            // Calculate the amount of shake based on how long it has been shaking already
            float currentPower = intensity * camera_sh.zoom * ((duration - elapsed) / duration);
           //System.out.println("shaking? cp: "+currentPower);

            x = (random.nextFloat() - 0.5f) * currentPower;

            y = (random.nextFloat() - 0.5f) * currentPower;
            if ((baseX<=camera_sh.position.x+10)&(baseX>=camera_sh.position.x-10)){
                camera_sh.translate(-x, 0);
            }
            if ((baseY<=camera_sh.position.y+10)&(baseY>=camera_sh.position.y-10)){
                camera_sh.translate(0, -y);
            }
            //camera_sh.translate(-x, -y);
            camera_sh.update();
            // Increase the elapsed time by the delta provided.
            elapsed += delta;

        }else
        {
            if (camera_sh.position.x!=baseX) {
                if (camera_sh.position.x > baseX) {
                    camera_sh.translate(-SPEED_OF_COMEBACK,0);
                }else
                {
                    camera_sh.translate(SPEED_OF_COMEBACK,0);
                }
                if (((camera_sh.position.x - baseX)<=SPEED_OF_COMEBACK)&((camera_sh.position.x - baseX)>=-SPEED_OF_COMEBACK)){
                    camera_sh.position.x = baseX;
                }
            }

            if (camera_sh.position.y!=baseY) {
                if (camera_sh.position.y > baseY) {
                    camera_sh.translate(0,-SPEED_OF_COMEBACK);
                }else
                {
                    camera_sh.translate(0,SPEED_OF_COMEBACK);
                }
                if (((camera_sh.position.y - baseY)<=SPEED_OF_COMEBACK)&((camera_sh.position.y - baseY)>=-SPEED_OF_COMEBACK)){
                    camera_sh.position.y = baseY;
                }
            }


            /* из учебника пример
            final float shakeAmplitudeInDegrees = 5.0f;
            float shake = MathUtils.sin(rot) * shakeAmplitudeInDegrees;
            sprite.setRotation(shake);
             */
           // camera_sh.position.x = baseX;
           // camera_sh.position.y = baseY;
            camera_sh.update();
        }
    }
}
