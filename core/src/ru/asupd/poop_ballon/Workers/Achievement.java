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
    Array<Texture> medals;
    public Achievement() {
        medals=new Array<Texture>();
        medals.add(Assets.instance.manager.get(Assets.medal_wooden));
        medals.add(Assets.instance.manager.get(Assets.medal_stone));
    }
    public void draw_current_medal(SpriteBatch sb,int x,int y){
        int diff= PlayState.balloons_manager.getCurrent_difficult_up();
        if (diff>=2){
            sb.draw(medals.get(diff-2),x,y);
        }
    }
}
