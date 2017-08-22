package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Voland on 04.08.2017.
 */

public class Balloon {
    private static final int MOVEMENT = 100;
    private int GRAVITY = -100;
    private Vector3 position;
    private Vector3 velosity;
    private Rectangle bounds;
    private Texture texture;

    public void setVelosity(int grav) {

        this.velosity.y = grav;
    }

    public Balloon(int x, int y){
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
       // zerovelosity = new Vector3(0, 0, 0);
        velosity.add(MOVEMENT, -GRAVITY, 0);
        texture = new Texture("Blue-Balloon.png");
        bounds = new Rectangle(x, y, texture.getWidth() , texture.getHeight());
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
        velosity.scl(dt);
        position.add(0, velosity.y, 0);
       // if (position.y < 0)position.y = 0;

        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);

        bounds.setPosition(position.x, position.y);
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
