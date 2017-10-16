package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static ru.asupd.poop_ballon.MyGdxGame.showed_ads;

/**
 * меню подсказки
 *
 * Created by Voland on 16.10.2017.
 */

public class Faq {
    boolean show;
    int b_y=140;
    private int current_slide;
    public Faq() {
        show = false;
        current_slide=0;
    }

    public boolean click(int x, int y){
        if (showed_ads){
            b_y+=70;
        }

        if (((x>340)&(y<b_y))&(!show)){
            show=true;

            return true;
        }else {
            if (show) {
                if (((x>165)&(x<165+150))&
                    ((y>80)&(y<80+130)))
                {
                    show=false;
                    return true;
                }
                //обработчик когда была нажата
                //System.out.println("Show");
                if (((x>87)&(x<87+45))&
                        ((y>270)&(y<270+45)))
                {
                    current_slide--;
                    if (current_slide<0){
                        current_slide=2;
                    }
                }
                if (((x>362)&(x<362+45))&
                        ((y>270)&(y<270+45)))
                {
                    current_slide++;
                    if (current_slide>2){
                        current_slide=0;
                    }
                }
            }
            return false;
        }
    }
    public void update(float dt){

    }

    public void draw(SpriteBatch sb){
        if (show){
            sb.draw(Assets.instance.manager.get(Assets.instr_pause_screen),0,0);
            sb.draw(Assets.instance.manager.get(Assets.plate),54,90);
            switch (current_slide) {
                case 0:
                    sb.draw(Assets.instance.manager.get(Assets.instr1), 85, 322);
                    break;
                case 1:
                    sb.draw(Assets.instance.manager.get(Assets.instr2), 85, 322);
                    break;
                case 2:
                    sb.draw(Assets.instance.manager.get(Assets.instr3), 85, 322);
                    break;
            }
            sb.draw(Assets.instance.manager.get(Assets.arrow),  85, 270,45,45,0,0,45,45,true,false);
            sb.draw(Assets.instance.manager.get(Assets.arrow), 362, 270,45,45);
            sb.draw(Assets.instance.manager.get(Assets.restart_ico),165,80,150,130);
        }
    }

    public boolean isShow() {
        return show;
    }

}
