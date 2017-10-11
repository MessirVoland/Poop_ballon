package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.States.PlayState;

/**
 * Менеджер ачивок
 * Created by Asup.D on 05.10.2017.
 */

public class Achievement {
    Resizer resizer_medal;
    Array<Texture> medals;
    private int local_x=270,local_y=200;
    public Achievement() {
        medals=new Array<Texture>();
        medals.add(Assets.instance.manager.get(Assets.medal_ice));
        medals.add(Assets.instance.manager.get(Assets.medal_wooden));   //1
        medals.add(Assets.instance.manager.get(Assets.medal_stone));    //2
            //3
        medals.add(Assets.instance.manager.get(Assets.medal_stone));    //4
        medals.add(Assets.instance.manager.get(Assets.medal_stone));    //5
        medals.add(Assets.instance.manager.get(Assets.medal_stone));    //6
        medals.add(Assets.instance.manager.get(Assets.medal_stone));    //7
        medals.add(Assets.instance.manager.get(Assets.medal_bronze));    //8
        medals.add(Assets.instance.manager.get(Assets.medal_silver));    //9
        medals.add(Assets.instance.manager.get(Assets.medal_gold));    //2
        resizer_medal = new Resizer(local_x,local_y);
    }
    public void draw_current_medal(SpriteBatch sb,int x,int y){
        int diff= PlayState.balloons_manager.getCurrent_difficult_up();
        if (diff>=2){
            sb.draw(medals.get(diff-2),x,y);
        }
    }
    public void start_anim(){
        resizer_medal.start();
    }
    public void draw_current_medal(SpriteBatch sb){
        int diff= PlayState.balloons_manager.getCurrent_difficult_up();
        if (diff>=2){
            sb.draw(medals.get(diff-2),local_x-(resizer_medal.getSize_x()/2),local_y-(resizer_medal.getSize_y()/2),resizer_medal.getSize_x(),resizer_medal.getSize_y());
        }
    }
    public void update(float dt){
        resizer_medal.update(dt);
    }
}
