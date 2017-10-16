package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.math.Vector3;

import ru.asupd.poop_ballon.States.PlayState;

import static ru.asupd.poop_ballon.States.PlayState.faq;
import static ru.asupd.poop_ballon.States.PlayState.pause;
import static ru.asupd.poop_ballon.States.PlayState.perfomancecounter;
import static ru.asupd.poop_ballon.States.PlayState.score_num;
import static ru.asupd.poop_ballon.States.PlayState.started;

/**
 * Модуль обработки нажатий
 * Created by Asup.D on 05.10.2017.
 */

public class MyInputProcessor implements com.badlogic.gdx.InputProcessor {
    Vector3 touchPos = new Vector3();
    public MyInputProcessor() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        //System.out.println("touchDown");
        //нажатие для двух режимов
        touchPos.set(screenX, screenY, 0);
        PlayState.camera.unproject(touchPos);
        screenX= (int) touchPos.x;
        screenY= (int) touchPos.y;

        //System.out.println("td screenX:"+screenX+" td screenY:"+screenY);

        //cтарт игры
        if ((!started)&(!pause)) {
            if (!PlayState.boss_balloon.isStarted()) {
                if (faq.click(screenX,screenY)) {

                }else {
                    if (!faq.isShow()) {
                        started = true;
                        score_num.setScore(0);
                    }
                }
            }
        }
        //если пауза
        if (PlayState.isPause()){
            PlayState.settings.clicked(screenX,screenY);
            return true;

        }//Если не пауза
        else{
            //Обработка нажатия на шар сздоровья
            if (PlayState.hearth_balloon.isFly()){
                int help=10;//текстуора на help пикслей больше для клика
                if ((PlayState.hearth_balloon.getPosition().x-help<screenX)&(PlayState.hearth_balloon.getPosition().x+95+help>screenX)){
                    if ((PlayState.hearth_balloon.getPosition().y-help<screenY)&(PlayState.hearth_balloon.getPosition().y+95+help>screenY)) {
                        if (!PlayState.hearth_balloon.isPooped()) {
                            PlayState.make_poop_Sound();
                            //System.out.println("Hearthballon_clicked, clickes: " + PlayState.hearth_balloon.getClicks());

                            PlayState.shaker.shake(0.40f);
                            if (PlayState.hearth_balloon.getClicks() == 2) {
                                PlayState.current_alpha_background = 2.0f;
                                PlayState.miss_ball--;
                                PlayState.hearth_balloon.setFly(false);
                                PlayState.hearth_balloon.setPooped();
                                PlayState.counter_of_h_ballons++;
                            } else
                                PlayState.hearth_balloon.clicked();
                            return true;
                        }
                    }
                }
            }
            //---------------------------------------------------------------------------------------//

            //Обработка обычных шаров
            //max_combo=0;
            PlayState.current_combo = 0;

            //Клик по шарам для проверки комбо, да долго проверять клик по шарам дважды но ничего лучше не придумал
            final int finalScreenX = screenX;
            final int finalScreenY = screenY;
            //final boolean[] wooden = {false};

            PlayState.balloons_manager.click(finalScreenX,finalScreenY);
            PlayState.bomb_balloon.clicked(finalScreenX,finalScreenY);



            if (PlayState.miss_ball>=1){
                PlayState.hearth_balloon.setCan_fly(true);
            }


            //---------------------------------------------------------------------------------------//


            return false;
        }//конец не паузы


    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return true;
    }
}
