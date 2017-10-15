package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.Sprites.Balloon;
import ru.asupd.poop_ballon.States.PlayState;

import static com.badlogic.gdx.math.MathUtils.random;

/** менеджер шаров
 * Created by Asup.D on 09.10.2017.
 */

public class Balloons_manager {
    private static int current_difficult;
    private static boolean get_clicked;
    private static Array<Balloon> balloons;//массив шаров

    public Balloons_manager(Array<Balloon> balloons_input) {
        current_difficult=(PlayState.settings.hi_score()/500+1);
        balloons = balloons_input;
        get_clicked = false;
    }

    public int getCurrent_difficult_up() {
        current_difficult=(PlayState.settings.hi_score()/500+1);
        if (current_difficult>=10){
            current_difficult=10;//ограничение для золота
        }
        return current_difficult;
    }
    public boolean get_clicked(){
        return get_clicked;
    }

    public void click(final int finalScreenX, final int finalScreenY){
        get_clicked=false;
        Gdx.app.postRunnable(new Runnable() {

            @Override
            public void run() {
                boolean wooden = false;
                boolean g_clicked = false;
                for (Balloon balloon : PlayState.balloons) {
                    if ((balloon.getPosition().x < finalScreenX) & (balloon.getPosition().x + 100 > finalScreenX)) {
                        if ((balloon.getPosition().y < finalScreenY) & (balloon.getPosition().y + 200 > finalScreenY)) {
                            if (!balloon.isPooped()) {
                                get_clicked = true;
                                //System.out.println("true");
                                PlayState.current_combo++;
                                PlayState.current_step++;
                                balloon.setCombo(PlayState.current_combo);
                                if (!wooden) {
                                    wooden = balloon.isN_ST_color();
                                }
                                g_clicked = true;
                                // System.out.println("Current combo on click: "+current_combo);

                            }
                        }
                    }
                }
                if (!g_clicked) {
                    if ((480 - 69 < finalScreenX) & (480 - 69 + 64 > finalScreenX)) {
                        if ((720 < finalScreenY) & (720 + 64 > finalScreenY)) {
                            if (!PlayState.isPause()) {
                                PlayState.setPAUSE(true);
                            }
                        }
                    }
                } else {
                    int mini_step = 0;
                    for (Balloon balloon : PlayState.balloons) {
                        if ((balloon.getPosition().x < finalScreenX) & (balloon.getPosition().x + 100 > finalScreenX)) {
                            if ((balloon.getPosition().y < finalScreenY) & (balloon.getPosition().y + 200 > finalScreenY)) {
                                if (!balloon.isPooped()) {
                                    //if (800-finalScreenY < 750) {
                                    //System.out.println("touched the ball :");
                                    PlayState.make_poop_Sound();

                                    PlayState.shaker.shake(0.276f); // 0.2f
                                    PlayState.cautch_ball++;


                                    if (PlayState.counter_of_h_ballons <= PlayState.score_num.getScore() / 120 * getCurrent_difficult_up()) {
                                        if (PlayState.miss_ball >= 1) {
                                            PlayState.hearth_balloon.setCan_fly(true);
                                            PlayState.hearth_balloon.setFly(true);
                                        } else {
                                            PlayState.counter_of_h_ballons++;
                                        }
                                    }

                                    if (PlayState.current_combo >= 2) {
                                        mini_step++;
                                        if (mini_step == 1) {
                                            balloon.start_combo_part();
                                        }
                                        if (wooden) {
                                            balloon.setWooden_color(true);
                                        }
                                        if (wooden) {
                                            PlayState.score_num.addScore(PlayState.current_combo * getCurrent_difficult_up());
                                            //System.out.println("Added W "+PlayState.current_combo *(PlayState.settings.hi_score()/500+1));
                                        } else {
                                            PlayState.score_num.addScore(PlayState.current_combo);
                                            //System.out.println("Added Nw"+(PlayState.current_combo));
                                        }
                                        PlayState.score_num.setCombo(PlayState.current_combo);

                                        balloon.setMax_combo(PlayState.current_combo);

                                    } else {
                                        if (wooden) {
                                            PlayState.score_num.addScore(getCurrent_difficult_up());
                                           // System.out.println("Added 2 W score");
                                        } else {
                                            PlayState.score_num.addScore(1);
                                          //  System.out.println("Added 1 nW score");
                                        }
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
                        // }
                        PlayState.index++;
                    }
                }
            }
        });
    }

    public void draw(SpriteBatch sb){
        for (Balloon balloon : balloons) {
            sb.setColor(1,1,1,0.9f);
            if (!balloon.isAnim_end()) {
                switch (balloon.getColor_of_balloon()) {

                    case 0:
                        sb.draw(Assets.instance.manager.get(Assets.balloon_green, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 1:
                        sb.draw(Assets.instance.manager.get(Assets.balloon_yellow, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 2:
                        sb.draw(Assets.instance.manager.get(Assets.balloon_blue, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 3:
                        sb.draw(Assets.instance.manager.get(Assets.balloon_red, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 4:
                        sb.draw(Assets.instance.manager.get(Assets.balloon_purple, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 5:
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 6:
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 7:
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 8:
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 9:
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 10:
                        sb.setColor(1, 1, 1, 1);
                        sb.draw(Assets.instance.manager.get(Assets.balloon_orange, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 11:
                        sb.setColor(1, 1, 1, 1);
                        sb.draw(balloon.getFrames(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 12:
                        switch (PlayState.settings.hi_score()/500) {
                            case 0:
                                break;
                            case 1:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_ice), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                            case 2:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_wooden), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                            case 3:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_stone), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                            case 4:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_kript), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                            case 5:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_blueinit), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                            case 6:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_pheon), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                            case 7:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_bronze), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                            case 8:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_silver), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                            case 9:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_gold), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                            default:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_gold), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                                break;
                        }break;
                    case 13:
                        sb.setColor(1, 1, 1, 1);
                        sb.draw(balloon.getFrames(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                }
            }
        }
        sb.setColor(1,1,1,1);
    }
}
