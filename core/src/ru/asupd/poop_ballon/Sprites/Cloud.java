package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Voland on 24.08.2017.
 */

public class Cloud extends Creature {
    private Vector3 position;
    private Vector3 velosity;
    private Texture texture;
    private Rectangle bounds;
    int color_of_cloud;

    public Texture getTexture() {
        return texture;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public Vector3 getPosition() {

        return position;
    }

    public int getColor_of_cloud() {
        return color_of_cloud;
    }

    public Cloud(int x, int y, int grav) {
       // texture =  new Texture("cloud1.png");
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
        this.velosity.x = grav;

        color_of_cloud=random(3);
        switch (color_of_cloud){
            case 0:
                texture =  new Texture("cloud1.png");
                break;
            case 1:
                texture =  new Texture("cloud2.png");
                break;

            case 2:
                texture =  new Texture("cloud3.png");
                break;
            case 3:
                texture =  new Texture("cloud4.png");
                break;
        }
    }

    @Override
    public void update(float dt) {
        velosity.scl(dt);
        position.add(velosity.x, 0, 0);
        // if (position.y < 0)position.y = 0;
        velosity.scl(1 / dt);
       // bounds.setPosition(position.x, position.y);

    }
    public void change_texture(){
        color_of_cloud=random(3);
        switch (color_of_cloud){
            case 0:
                texture =  new Texture("cloud1.png");
                break;
            case 1:
                texture =  new Texture("cloud2.png");
                break;
            case 2:
                texture =  new Texture("cloud3.png");
                break;
            case 3:
                texture =  new Texture("cloud4.png");
                break;
        }

    }

    @Override
    public void dispose() {
    texture.dispose();
    }
}
