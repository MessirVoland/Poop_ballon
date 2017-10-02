package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import ru.asupd.poop_ballon.Workers.Assets;

/**
 * Created by Voland on 01.10.2017.
 */

public class Hearth_balloon extends Creature {
    private Vector3 position;
    private Vector3 velosity;
    private boolean fly=false;

    public boolean isCan_fly() {
        return can_fly;
    }

    private boolean can_fly=true;

    private boolean sin_grav_bool;
    Texture texture;

    public Hearth_balloon() {
        position = new Vector3(-95,400-84,0);
        velosity = new Vector3(80,0,0);
        texture = Assets.instance.manager.get(Assets.heart_baloon);

    }

    public boolean isFly() {
        return fly;
    }
    public void restart(){
        position.x=-95;
    }

    public Vector3 getPosition() {

        return position;
    }

    @Override
    public void update(float dt) {
        int sin_grav = 50;

        if ((velosity.y< sin_grav)&(sin_grav_bool)){
            velosity.y++;
        }
        if ((velosity.y>-sin_grav)&(!sin_grav_bool)){
            velosity.y--;
        }

        if (velosity.y> sin_grav){
            sin_grav_bool=false;
        }
        if (velosity.y<-sin_grav){
            sin_grav_bool=true;
        }

        velosity.scl(dt);
        position.add(velosity.x, velosity.y, 0);
        velosity.scl(1 / dt);

    }

    public void setCan_fly(boolean can_fly) {
        this.can_fly = can_fly;
    }

    public void setFly(boolean fly) {
        if (fly){
            can_fly=false;

        }
        this.fly = fly;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void dispose() {

    }
}
