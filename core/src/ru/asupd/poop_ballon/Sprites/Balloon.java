package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Voland on 04.08.2017.
 */

public class Balloon extends Creature {
    private static final int MOVEMENT = 100;
    private int GRAVITY = -100;
    private Vector3 position;
    private Vector3 velosity;
    private Rectangle bounds;
    private Texture texture_b,texture_g,texture_r,texture_y;
    private Texture texture;
    int color_of_balloon;
    private float currentTime;
    boolean pooped,live_out;

    public void setVelosity(int grav) {

        this.velosity.y = grav;
    }
    public boolean isPooped() {
        return pooped;
    }

    public void setPooped(int i) {
        if (i<150){
            texture = new Texture("blow.png");
        }
        else
        {
            texture = new Texture("Blood_Splatter.png");
        }
        this.pooped = true;
    }

    public Balloon(int x, int y, int grav){
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
       // zerovelosity = new Vector3(0, 0, 0);
        this.velosity.y = grav;
        pooped=false;
        live_out=false;
        currentTime=0;

        //color_of_balloon=random(3);
        if (grav<260){
            color_of_balloon=0;
        }else
            if (grav<320){
                color_of_balloon=1;
            }
            else
                if (grav<380){
                    color_of_balloon=2;
                }
                else
                    if(grav<420){
                        color_of_balloon=3;
                    }
                    else{
                        color_of_balloon=4;
                    }
        switch (color_of_balloon){
            case 2:
                texture =  new Texture("Balloon_blue.png");
                break;
            case 3:
                texture =  new Texture("Balloon_red.png");
                break;
            case 0:
                texture =  new Texture("Balloon_green.png");
                break;
            case 1:
                texture =  new Texture("Balloon_yellow.png");
                break;
            case 4:
                texture =  new Texture("Balloon_purple.png");
                break;
        }

        bounds = new Rectangle(x, y, 95 , 190);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setPosition(float x,float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public boolean isLive_out() {
        return live_out;
    }

    public void update(float dt){
   /*     if (position.y > 0)
            velosity.add(0, -GRAVITY, 0);

        velosity.scl(dt);
        position.add(0, velosity.y, 0);
        if (position.y < 0)
            position.y = 0;

        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
        */
   if (pooped){
        currentTime+=dt;
       if (currentTime>=0.355f)
       {
           live_out=true;
       }

   }
   else {
            velosity.scl(dt);
            position.add(0, velosity.y, 0);
            // if (position.y < 0)position.y = 0;
      velosity.scl(1 / dt);
            bounds.setPosition(position.x, position.y);

        }
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
    }

}

