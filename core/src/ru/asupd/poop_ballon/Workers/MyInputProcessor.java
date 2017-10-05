package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

import ru.asupd.poop_ballon.Sprites.Balloon;
import ru.asupd.poop_ballon.States.PlayState;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Asup.D on 05.10.2017.
 */

public class MyInputProcessor implements com.badlogic.gdx.InputProcessor {
    Vector3 touchPos;

    public MyInputProcessor() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //System.out.println("touchDown");
        //нажатие для двух режимов
        touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        PlayState.camera.unproject(touchPos);
        screenX= (int) touchPos.x;
        screenY= (int) touchPos.y;

       // System.out.println("td screenX:"+screenX+" td screenY:"+screenY);
        if ((480 - 69 < screenX) & (480 - 69 + 64 > screenY)) {
            if ((800-20 < screenY) & (800-20 + 64 > screenY)) {
                PlayState.setPAUSE(!PlayState.isPause());
            }
        }
        //если пауза
        if (PlayState.isPause()){
            PlayState.settings.clicked(screenX,screenY);
        }//Если не пауза
        else{
            //Обработка нажатия на шар сздоровья
            if (PlayState.hearth_balloon.isFly()){
                int help=10;//текстуора на help пикслей больше для клика
                if ((PlayState.hearth_balloon.getPosition().x-help<screenX)&(PlayState.hearth_balloon.getPosition().x+95+help>screenX)){
                    if ((PlayState.hearth_balloon.getPosition().y-help<800-screenY)&(PlayState.hearth_balloon.getPosition().y+95+help>800-screenY)) {
                        if (!PlayState.hearth_balloon.isPooped()) {
                            PlayState.poop_Sound.play(PlayState.volume);
                            System.out.println("Hearthballon_clicked, clickes: " + PlayState.hearth_balloon.getClicks());

                            PlayState.shaker.shake(0.40f);
                            if (PlayState.hearth_balloon.getClicks() == 2) {
                                PlayState.current_alpha_background = 2.0f;
                                PlayState.miss_ball--;
                                PlayState.hearth_balloon.setFly(false);
                                PlayState.hearth_balloon.setPooped();
                                PlayState.counter_of_h_ballons++;
                            } else
                                PlayState.hearth_balloon.clicked();
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

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    for(Balloon balloon :PlayState.balloons) {
                        if ((balloon.getPosition().x < finalScreenX) & (balloon.getPosition().x + 100 > finalScreenX)) {
                            if ((balloon.getPosition().y < finalScreenY) & (balloon.getPosition().y + 200 > finalScreenY)) {
                                if (!balloon.isPooped()) {
                                    PlayState.current_combo++;
                                    PlayState.current_step++;
                                    balloon.setCombo(PlayState.current_combo);
                                    // System.out.println("Current combo on click: "+current_combo);
                                }
                            }
                        }
                    }
                    int mini_step=0;
                    for (Balloon balloon :PlayState.balloons) {
                        if ((balloon.getPosition().x < finalScreenX) & (balloon.getPosition().x + 100 > finalScreenX)) {
                            if ((balloon.getPosition().y < finalScreenY) & (balloon.getPosition().y + 200 > finalScreenY)) {
                                if (!balloon.isPooped()) {
                                    if (800-finalScreenY < 750) {
                                        //System.out.println("touched the ball :");

                                        long id = PlayState.poop_Sound.play(PlayState.volume);
                                        Random rand = new Random();
                                        float finalX = rand.nextFloat() * (PlayState.maxX - PlayState.minX) + PlayState.minX;
                                        PlayState.poop_Sound.setPitch(id, finalX);
                                        PlayState.poop_Sound.setVolume(id, PlayState.volume);

                                        PlayState.shaker.shake(0.276f); // 0.2f
                                        PlayState.cautch_ball++;
                                        PlayState.score_num.addScore(1);


                                        if (PlayState.counter_of_h_ballons<=PlayState.score_num.getScore()/400){
                                            if (PlayState.miss_ball>=1) {
                                                PlayState.hearth_balloon.setCan_fly(true);
                                                PlayState.hearth_balloon.setFly(true);
                                            }
                                            else
                                            {
                                                PlayState.counter_of_h_ballons++;
                                            }
                                        }

                                        if (PlayState.current_combo >= 2) {
                                            mini_step++;
                                            if (mini_step==1){
                                                balloon.start_combo_part();
                                            }

                                            PlayState.score_num.addScore(PlayState.current_combo * PlayState.current_combo - PlayState.current_combo);
                                            PlayState.score_num.setCombo(PlayState.current_combo);

                                            balloon.setMax_combo(PlayState.current_combo);

                                        } else {
                                            balloon.setCombo(0);
                                        }
                                        balloon.setPooped();
                                        if ((PlayState.current_step >= PlayState.STEP_for_balloon) & (PlayState.cautch_ball <= 500)) {
                                            PlayState.current_step = 0;
                                            // System.out.println("New balloon generated");
                                            PlayState.STEP_for_balloon += 20;
                                            PlayState.balloons_count++;
                                            PlayState.balloons_number++;
                                            PlayState.balloons.add(new Balloon(random(4) * 96, -195 - random(50), PlayState.get_speed_for_balloon(), !PlayState.boss_balloon.isStarted()));

                                        }
                                        PlayState.balloons_number++;
                                        PlayState.balloons.add(new Balloon(random(4) * 96, -195 - random(50), PlayState.get_speed_for_balloon(), !PlayState.boss_balloon.isStarted()));
                                    }
                                }
                            }
                        }
                        PlayState.index++;
                    }
                }
            });



            if (PlayState.miss_ball>=1){
                PlayState.hearth_balloon.setCan_fly(true);
            }


            //---------------------------------------------------------------------------------------//

        }//конец не паузы

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
