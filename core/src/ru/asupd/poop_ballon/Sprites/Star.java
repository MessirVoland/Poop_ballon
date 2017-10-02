package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import ru.asupd.poop_ballon.Workers.Assets;
import ru.asupd.poop_ballon.Workers.Resizer;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Asup.D on 02.10.2017.
 */

public class Star extends Creature {
    private Vector3 position;
    public Resizer resizer;
    private Texture texture;

    public Star(int x, int y) {
        position=new Vector3(x,y,0);
        int rand_star;
        rand_star=random(6);
        switch (rand_star){
            case 0:
                texture = Assets.instance.manager.get(Assets.star1);
                resizer = new Resizer(Assets.instance.manager.get(Assets.star1).getWidth(),Assets.instance.manager.get(Assets.star1).getHeight());
                break;
            case 1:
                texture = Assets.instance.manager.get(Assets.star2);
                resizer = new Resizer(Assets.instance.manager.get(Assets.star2).getWidth(),Assets.instance.manager.get(Assets.star2).getHeight());
                break;
            case 2:
                texture = Assets.instance.manager.get(Assets.star3);
                resizer = new Resizer(Assets.instance.manager.get(Assets.star3).getWidth(),Assets.instance.manager.get(Assets.star3).getHeight());
                break;
            case 3:
                texture = Assets.instance.manager.get(Assets.star4);
                resizer = new Resizer(Assets.instance.manager.get(Assets.star4).getWidth(),Assets.instance.manager.get(Assets.star4).getHeight());
                break;
            case 4:
                texture = Assets.instance.manager.get(Assets.star5);
                resizer = new Resizer(Assets.instance.manager.get(Assets.star5).getWidth(),Assets.instance.manager.get(Assets.star5).getHeight());
                break;
            case 5:
                texture = Assets.instance.manager.get(Assets.star6);
                resizer = new Resizer(Assets.instance.manager.get(Assets.star6).getWidth(),Assets.instance.manager.get(Assets.star6).getHeight());
                break;
            case 6:
                texture = Assets.instance.manager.get(Assets.star7);
                resizer = new Resizer(Assets.instance.manager.get(Assets.star7).getWidth(),Assets.instance.manager.get(Assets.star7).getHeight());
                break;
        }
        //texture = Assets.instance.manager.get(Assets.star1);
        //resizer = new Resizer(40,40);
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void update(float dt) {
        resizer.update(dt);

    }

    public Vector3 getPosition() {
        return position;
    }

    @Override
    public void dispose() {

    }
}
