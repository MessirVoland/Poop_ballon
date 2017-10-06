package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import javax.swing.text.Position;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.States.PlayState;

/**
 * Created by Asup.D on 05.10.2017.
 */

public class Settings {
    private Preferences pref;//для храниния данных
    private boolean mute,vibro;//звук вибро
    private Texture restart;
    private Vector3 pos_restart;
    private static final float POS_X_RESTART=-75,POS_Y_RESTART=-278;

    public Settings(Preferences prefs) {
        pref=prefs;
        //Первый запуск
        if (pref.getBoolean("first_start")){
            pref.putBoolean("first_start",false);
            pref.putBoolean("mute",true);
            pref.putBoolean("vibro",true);
            pref.flush();
        }
        else {
            mute=pref.getBoolean("mute");
            vibro=pref.getBoolean("vibro");
        }

        restart = Assets.instance.manager.get(Assets.restart_ico);
        pos_restart = new Vector3(240+POS_X_RESTART,400+POS_Y_RESTART,0);

    }
    public void draw(SpriteBatch sb,Shaker shaker){
        sb.draw(restart,((int) shaker.getCamera_sh().position.x)+POS_X_RESTART,((int) shaker.getCamera_sh().position.y)+POS_Y_RESTART,150,156);
    }

    public void clicked(Vector3 touchPos,GameStateManager gsm) {
        if (((touchPos.y>pos_restart.y)&(touchPos.y<pos_restart.y+restart.getHeight()))&&
        ((touchPos.x>pos_restart.x)&(touchPos.x<pos_restart.x+restart.getWidth()))){
            gsm.set(new PlayState(gsm));
            PlayState.setUNPAUSE();
        }
    }
    public void clicked(int ScreenX, int ScreenY) {
        if (((800-ScreenY>pos_restart.y)&(800-ScreenY<pos_restart.y+restart.getHeight()))&&
                ((ScreenX>pos_restart.x)&(ScreenX<pos_restart.x+restart.getWidth()))){
            //gsm.set(new PlayState(gsm));
            PlayState.RESTART_STAGE();
        }
    }

    public boolean isMute() {
        return mute;
    }

    public boolean isVibro() {
        return vibro;
    }
}
