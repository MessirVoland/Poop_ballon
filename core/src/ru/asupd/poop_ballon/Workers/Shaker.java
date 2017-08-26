package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.graphics.OrthographicCamera;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Voland on 24.08.2017.
 */

public class Shaker {
    private OrthographicCamera camera_sh;
    float elapsed,duration,intensity;
    float baseX,baseY;

    public Shaker(OrthographicCamera camera) {
        camera_sh=camera;
       baseX= camera.position.x ;
       baseY= camera.position.y ;
    }
    public void shake(float duration_in){
        elapsed=0;
        intensity=3;
        duration=duration_in;
    }

    public void update(float delta) {

        // Only shake when required.
        if(elapsed < duration) {

            // Calculate the amount of shake based on how long it has been shaking already
            float currentPower = intensity * camera_sh.zoom * ((duration - elapsed) / duration);
           //System.out.println("shaking? cp: "+currentPower);
            float x = (random.nextFloat() - 0.5f) * currentPower;
            float y = (random.nextFloat() - 0.5f) * currentPower;
            camera_sh.translate(-x, -y);
            camera_sh.update();
            // Increase the elapsed time by the delta provided.
            elapsed += delta;
        }else
        {
            camera_sh.position.x = baseX;
            camera_sh.position.y = baseY;
            camera_sh.update();
        }
    }
}