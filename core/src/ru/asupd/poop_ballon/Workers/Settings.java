package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import ru.asupd.poop_ballon.States.PlayState;

/**
 * Класс для храния и обработки настроек
 * Created by Asup.D on 05.10.2017.
 */

public class Settings {
    private Preferences pref;//для храниния данных
    private boolean mute,vibro;//звук вибро
    private Texture restart;
    private Texture mute_tex,unmute_tex;
    private Texture vibro_tex,unvibro_tex;
    private Vector3 pos_restart;
    private Vector3 pos_mute;
    private Vector3 pos_vibro;
    private static final float POS_X_RESTART=-75,POS_Y_RESTART=-278;

    private boolean one_click=false;

    public Settings(Preferences prefs) {
        pref=prefs;
        //Первый запуск
        if (pref.getBoolean("first_start")){
            pref.putBoolean("first_start",false);
            pref.putBoolean("mute",false);
            pref.putBoolean("vibro",false);
            pref.flush();
            mute=false;
            vibro=true;
        }
        else {
            mute=pref.getBoolean("mute");
            vibro=pref.getBoolean("vibro");
        }

        restart = Assets.instance.manager.get(Assets.restart_ico);
        pos_restart = new Vector3(240+POS_X_RESTART,400+POS_Y_RESTART,0);

        mute_tex= new Texture("sound_off.png");
        unmute_tex = new Texture("sound_on.png");
        pos_mute = new Vector3(270,400-78,0);

        vibro_tex = new Texture("vibro_on.png");
        unvibro_tex = new Texture("vibro_off.png");
        pos_vibro = new Vector3(60,400 - 78,0);


    }
    public void draw(SpriteBatch sb,Shaker shaker){
        if (mute){
            sb.draw(mute_tex,((int) shaker.getCamera_sh().position.x)+30,((int) shaker.getCamera_sh().position.y)-78,150,156);
        }else{
            sb.draw(unmute_tex,((int) shaker.getCamera_sh().position.x)+30,((int) shaker.getCamera_sh().position.y)-78,150,156);
        }
        if (vibro){
            sb.draw(vibro_tex,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78,150,156);
        }else{
            sb.draw(unvibro_tex,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78,150,156);
        }
        sb.draw(restart,((int) shaker.getCamera_sh().position.x)+POS_X_RESTART,((int) shaker.getCamera_sh().position.y)+POS_Y_RESTART,150,131);
    }

    void clicked(int ScreenX, int ScreenY) {
        if (((ScreenY>pos_restart.y)&(ScreenY<pos_restart.y+restart.getHeight()))&
                ((ScreenX>pos_restart.x)&(ScreenX<pos_restart.x+restart.getWidth()))){
            //gsm.set(new PlayState(gsm));
            PlayState.RESTART_STAGE();
        }
        else
        //звук
        if (((ScreenY>pos_mute.y)&(ScreenY<pos_mute.y+mute_tex.getHeight()))&
                ((ScreenX>pos_mute.x)&(ScreenX<pos_mute.x+mute_tex.getWidth()))) {
            if (mute) {
                mute=false;
                System.out.println("unmute");
                PlayState.set_unmute();
            }
            else
            {
                mute=true;
                System.out.println("mute");
                PlayState.set_mute();
            }
            pref.putBoolean("mute", mute);
            pref.flush();
        }
        else
        //вибро
        if (((ScreenY>pos_vibro.y)&(ScreenY<pos_vibro.y+vibro_tex.getHeight()))&
             ((ScreenX>pos_vibro.x)&(ScreenX<pos_vibro.x+vibro_tex.getWidth()))) {
                {
                    if (vibro){
                        vibro=false;
                        pref.putBoolean("vibro",vibro);
                        pref.flush();
                    }
                    else
                    {
                        vibro=true;
                        Gdx.input.vibrate(125);
                        pref.putBoolean("vibro",vibro);
                        pref.flush();
                    }
                }
        }
        /*else

        if ((ScreenY>600)&(ScreenX>240)){
            PlayState.score_num.addScore(200);
        }
        else
        if ((ScreenY>600)&(ScreenX<240)){
            pref.putInteger("highscore",0) ;
            pref.flush();
        }*/
        else
        //вывод из паузы
        {
           // if (one_click) {
                PlayState.setUNPAUSE();
           //     System.out.println("Unpause()");
            //    one_click=false;
          //  }
           //  else {
           //     one_click=true;
          //  }
        }

    }

    public boolean isMute() {
        return mute;
    }

    public boolean isVibro() {
        return vibro;
    }

    public int hi_score(){
        return pref.getInteger("highscore");
    }
}
