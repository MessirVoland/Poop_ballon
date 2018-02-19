package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.States.PlayState;

import static ru.asupd.poop_ballon.States.PlayState.getCurrent_difficult_up;

/**
 * Менеджер ачивок
 * Created by Asup.D on 05.10.2017.
 */

public class Achievement {
    Resizer resizer_medal;
    Array<Texture> medals;
    BitmapFont font=new BitmapFont();


    int st_x1=0,nst_x1=0;
    int st_x2=0,nst_x2=0;
    int st_x3=0,nst_x3=0;
    int st_x4=0,nst_x4=0;
    int st_x5=0,nst_x5=0;

    private int local_x=5,local_y=95;
    public Achievement() {
        font.setColor(Color.RED);
        medals=new Array<Texture>();
        medals.add(Assets.instance.manager.get(Assets.medal_ice));
        medals.add(Assets.instance.manager.get(Assets.medal_wooden));   //1
        medals.add(Assets.instance.manager.get(Assets.medal_stone));    //2
            //3
        medals.add(Assets.instance.manager.get(Assets.medal_kript));    //4
        medals.add(Assets.instance.manager.get(Assets.medal_blueinit));    //5
        medals.add(Assets.instance.manager.get(Assets.medal_pheon));    //6//7
        medals.add(Assets.instance.manager.get(Assets.medal_bronze));    //7
        medals.add(Assets.instance.manager.get(Assets.medal_silver));    //8
        medals.add(Assets.instance.manager.get(Assets.medal_gold));    //9
        resizer_medal = new Resizer(225,290);
        Assets.make_medals_linear();
    }

    public Achievement(boolean use_medal_without_x2){
        medals=new Array<Texture>();
        medals.add(Assets.instance.manager.get(Assets.medal_w_ice));
        medals.add(Assets.instance.manager.get(Assets.medal_w_wooden));   //1
        medals.add(Assets.instance.manager.get(Assets.medal_w_stone));    //2
        //3
        medals.add(Assets.instance.manager.get(Assets.medal_w_kript));    //4
        medals.add(Assets.instance.manager.get(Assets.medal_w_blueinit));    //5
        medals.add(Assets.instance.manager.get(Assets.medal_w_pheon));    //6//7
        medals.add(Assets.instance.manager.get(Assets.medal_w_bronze));    //7
        medals.add(Assets.instance.manager.get(Assets.medal_w_silver));    //8
        medals.add(Assets.instance.manager.get(Assets.medal_w_gold));    //9
        resizer_medal = new Resizer(225,290);
        Assets.make_medals_w_linear();
    }
    public void draw_current_medal(SpriteBatch sb,int x,int y){
        int diff= getCurrent_difficult_up();
        if (diff>=2){
            sb.draw(medals.get(9),x,y);
        }
    }
    public void start_anim(){

        resizer_medal.start();
    }
    public void refr(){
        int st_x1=0,nst_x1=0;
        int st_x2=0,nst_x2=0;
        int st_x3=0,nst_x3=0;
        int st_x4=0,nst_x4=0;
        int st_x5=0,nst_x5=0;
    }
    public boolean draw_current_medal(SpriteBatch sb){
        int diff= getCurrent_difficult_up();
        //font.draw(sb," x1 :"+st_x1+" x2 :"+st_x2+" x3 :"+st_x3+" x4 :"+st_x4+" x5 :"+st_x5,10,780);
        //font.draw(sb," n1 :"+nst_x1+" n2 :"+nst_x2+" n3 :"+nst_x3+" n4 :"+nst_x4+" n5 :"+nst_x5,10,760);
        if (diff>=1){
                sb.draw(medals.get(diff - 1), local_x , local_y , 60,80);
        return true;
        }

        return false;
    }
    public boolean draw_current_medal(SpriteBatch sb,float pos_x,float pos_y,float width,float height){
        int diff= getCurrent_difficult_up();
        //font.draw(sb," x1 :"+st_x1+" x2 :"+st_x2+" x3 :"+st_x3+" x4 :"+st_x4+" x5 :"+st_x5,10,780);
        //font.draw(sb," n1 :"+nst_x1+" n2 :"+nst_x2+" n3 :"+nst_x3+" n4 :"+nst_x4+" n5 :"+nst_x5,10,760);
        if (diff>=1){
            sb.draw(medals.get(diff - 1), pos_x , pos_y , width,height);
            return true;
        }

        return false;
    }
    public void clicked(boolean wooden, short current_combo, int current_step_wooden) {
        if (wooden) {
            switch (current_combo) {
                case 0:
                    break;
                case 1:
                    st_x1++;
                    break;
                case 2:
                    st_x2++;
                    break;
                case 3:
                    st_x3++;
                    break;
                case 4:
                    st_x4++;
                    break;
                default:
                    st_x5++;
                    break;
            }
        } else {
            switch (current_combo) {
                case 0:
                    break;
                case 1:
                    nst_x1++;
                    break;
                case 2:
                    nst_x2++;
                    break;
                case 3:
                    nst_x3++;
                    break;
                case 4:
                    nst_x4++;
                    break;
                default:
                    nst_x5++;
                    break;
            }

        }
    }
    public void update(float dt){
        resizer_medal.update(dt);
    }
}
