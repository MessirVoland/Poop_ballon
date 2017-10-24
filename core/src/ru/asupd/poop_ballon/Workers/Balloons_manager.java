package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.Sprites.Balloon;
import ru.asupd.poop_ballon.States.PlayState;

import static com.badlogic.gdx.math.MathUtils.random;
import static ru.asupd.poop_ballon.States.GameoverState.achievement;
import static ru.asupd.poop_ballon.States.PlayState.MEDAL_SCORE;
import static ru.asupd.poop_ballon.States.PlayState.SEQUENCE_OF_HEARTH_BALLOON;
import static ru.asupd.poop_ballon.States.PlayState.SIZE_OF_COMBO_FOR_BOMB_SPAWN;
import static ru.asupd.poop_ballon.States.PlayState.current_combo;
import static ru.asupd.poop_ballon.States.PlayState.getCurrent_difficult_up;
import static ru.asupd.poop_ballon.States.PlayState.settings;

/** менеджер шаров
 * Created by Asup.D on 09.10.2017.
 */

public class Balloons_manager {
    private static int current_difficult;
    private static boolean get_clicked;
    int current_step_wooden;
    public boolean wooden;
    private static Array<Balloon> balloons;//массив шаров

    public Balloons_manager(Array<Balloon> balloons_input) {
        current_difficult=((settings.hi_score()-500)/MEDAL_SCORE+1);
        balloons = balloons_input;
        get_clicked = false;
    }

    public boolean get_clicked(){
        return get_clicked;
    }


    public void click(final int finalScreenX, final int finalScreenY){
        get_clicked=false;

                wooden = false;
                boolean g_clicked = false;
                current_step_wooden=0;

                for (Balloon balloon : PlayState.balloons) {
                   if (balloon.getSprite().getBoundingRectangle().contains(finalScreenX,finalScreenY))
                     {
                            if (!balloon.isPooped()) {
                                get_clicked = true;
                                //System.out.println("true");
                                PlayState.current_combo++;
                                PlayState.current_step++;
                                balloon.setCombo(PlayState.current_combo);


                                if (balloon.isN_ST_color()){
                                    current_step_wooden++;
                                }
                                if (!wooden) {
                                    wooden = balloon.isN_ST_color();
                                }


                                g_clicked = true;
                                // System.out.println("Current combo on click: "+current_combo);

                            }
                        }

                }
                if (PlayState.current_combo>=SIZE_OF_COMBO_FOR_BOMB_SPAWN){
                    PlayState.bomb_balloon.try_to_fly();
                }
                achievement.clicked(wooden,current_combo,current_step_wooden);

                if (!g_clicked) {
                    if (((480 - 69 < finalScreenX) & (480 - 69 + 64 > finalScreenX)) &
                         ((720 < finalScreenY) & (720 + 64 > finalScreenY))) {
                            if (!PlayState.isPause()) {
                                PlayState.setPAUSE(true);
                            }
                        }

                } else {
                    int mini_step = 0;
                    for (Balloon balloon : PlayState.balloons) {
                        if (balloon.getSprite().getBoundingRectangle().contains(finalScreenX,finalScreenY)) {
                                if (!balloon.isPooped()) {
                                    //if (800-finalScreenY < 750) {
                                    //System.out.println("touched the ball :");
                                    PlayState.make_poop_Sound();

                                    PlayState.shaker.shake(0.276f); // 0.2f
                                    PlayState.cautch_ball++;


                                    if (PlayState.counter_of_h_ballons <= PlayState.score_num.getScore() /SEQUENCE_OF_HEARTH_BALLOON * getCurrent_difficult_up()) {
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
                                            PlayState.score_num.addScore((int) (PlayState.current_combo * (getCurrent_difficult_up()*current_step_wooden)));
                                            //System.out.println("Added W "+ (PlayState.current_combo * (getCurrent_difficult_up()*current_step_wooden)));
                                            //System.out.println("cur combo "+ PlayState.current_combo +"diff " +getCurrent_difficult_up()+"Curr wood "+current_step_wooden);
                                        } else {
                                            PlayState.score_num.addScore(PlayState.current_combo);
                                            //System.out.println("Added Nw"+(PlayState.current_combo));
                                        }
                                        PlayState.score_num.setCombo(PlayState.current_combo,wooden,current_step_wooden);

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

                        // }
                        PlayState.index++;
                    }
                }
    }

    public void draw(SpriteBatch sb){
        //PlayState.perfomancecounter.start();
        for (Balloon balloon : balloons) {
            //sb.draw(Assets.instance.manager.get(Assets.balloon_green, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);

            sb.setColor(1,1,1,0.9f);
            if (!balloon.isLive_out()) {
                switch (balloon.getColor_of_balloon()) {

                    case 0:
                        //sb.draw(Assets.instance.manager.get(Assets.balloon_green, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                       // balloon.getSprite().draw(sb);
                       // break;
                    case 1:
                      //  sb.draw(Assets.instance.manager.get(Assets.balloon_yellow, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                      //  break;
                    case 2:
                       // sb.draw(Assets.instance.manager.get(Assets.balloon_blue, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                      //  break;
                    case 3:
                      // sb.draw(Assets.instance.manager.get(Assets.balloon_red, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                       // break;
                    case 4:
                      //  sb.draw(Assets.instance.manager.get(Assets.balloon_purple, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        balloon.getSprite().draw(sb);
                        break;
                    case 5:
                        //sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 6:
                       // sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 7:
                       // sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 8:
                       // sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 9:
                      //  sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                    case 10:
                        sb.setColor(1, 1, 1, 1);
                        //sb.draw(Assets.instance.manager.get(Assets.balloon_orange, Texture.class), balloon.getPosition().x, balloon.getPosition().y);
                        balloon.getSprite().draw(sb);
                        balloon.scale(0.01f);
                        break;
                    case 11:
                        sb.setColor(1, 1, 1, 1);
                       // sb.draw(balloon.getFrames(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);

                        balloon.part_start();
                        break;
                    case 12:

                        if(balloon.isCombo()){
                            balloon.scale(0.01f);
                        }
                        switch ((settings.hi_score()-500)/MEDAL_SCORE) {
                            case 0:
                                break;
                            case 1:
                                sb.setColor(1, 1, 1, 1);
                                //sb.draw(Assets.instance.manager.get(Assets.balloon_ice), balloon.getPosition().x, balloon.getPosition().y);

                                balloon.getSprite().draw(sb);
                                balloon.setNumber_of_n_st(1);
                                break;
                            case 2:
                                sb.setColor(1, 1, 1, 1);
                                //sb.draw(Assets.instance.manager.get(Assets.balloon_wooden), balloon.getPosition().x, balloon.getPosition().y);
                                balloon.getSprite().draw(sb);
                                balloon.setNumber_of_n_st(2);
                                break;
                            case 3:
                                sb.setColor(1, 1, 1, 1);
                                //sb.draw(Assets.instance.manager.get(Assets.balloon_stone), balloon.getPosition().x, balloon.getPosition().y);
                                balloon.getSprite().draw(sb);
                                balloon.setNumber_of_n_st(3);
                                break;
                            case 4:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_kript), balloon.getPosition().x, balloon.getPosition().y);
                                balloon.setNumber_of_n_st(4);
                                if (balloon.effect_gold!=null)
                                balloon.effect_gold.draw(sb);
                                break;
                            case 5:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_blueinit), balloon.getPosition().x, balloon.getPosition().y);
                                balloon.setNumber_of_n_st(5);
                                if (balloon.effect_gold!=null)
                                balloon.effect_gold.draw(sb);
                                break;
                            case 6:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_pheon), balloon.getPosition().x, balloon.getPosition().y);
                                balloon.setNumber_of_n_st(6);
                                if (balloon.effect_gold!=null)
                                balloon.effect_gold.draw(sb);
                                break;
                            case 7:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_bronze), balloon.getPosition().x, balloon.getPosition().y);
                                balloon.setNumber_of_n_st(7);
                                if (balloon.effect_gold!=null)
                                balloon.effect_gold.draw(sb);
                                break;
                            case 8:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_silver), balloon.getPosition().x, balloon.getPosition().y);
                                balloon.setNumber_of_n_st(8);
                                if (balloon.effect_gold!=null)
                                balloon.effect_gold.draw(sb);
                                break;
                            case 9:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_gold), balloon.getPosition().x, balloon.getPosition().y);
                                balloon.setNumber_of_n_st(9);
                                if (balloon.effect_gold!=null)
                                balloon.effect_gold.draw(sb);
                                break;
                            default:
                                sb.setColor(1, 1, 1, 1);
                                sb.draw(Assets.instance.manager.get(Assets.balloon_gold), balloon.getPosition().x, balloon.getPosition().y);
                                balloon.setNumber_of_n_st(9);
                                if (balloon.effect_gold!=null)
                                balloon.effect_gold.draw(sb);
                                break;
                        }break;
                    case 13:
                        sb.setColor(1, 1, 1, 1);
                       // sb.draw(balloon.getFrames(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        break;
                }
            }
        }
        sb.setColor(1,1,1,1);
       // PlayState.perfomancecounter.stop();
       // PlayState.FontRed1.draw(sb,"Current dt : " +PlayState.perfomancecounter.current,10, 750);
       // PlayState.perfomancecounter.reset();
    }
}
