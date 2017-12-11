package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import ru.asupd.poop_ballon.States.PlayState;

import static ru.asupd.poop_ballon.MyGdxGame.playServices_my;
import static ru.asupd.poop_ballon.States.PlayState.score_num;
import static ru.asupd.poop_ballon.States.PlayState.set_mute;

/**
 * Класс для храния и обработки настроек
 * Created by Asup.D on 05.10.2017.
 */

public class Settings {
    private Preferences pref;//для храниния данных
    private static int hi_score;
    private boolean mute,vibro,music_b;//звук вибро

    //private Texture restart;
    //private Texture mute_tex,unmute_tex;

    //private Texture vibro_tex,unvibro_tex;

    //private Vector3 pos_restart;
    //private Vector3 pos_mute;
    //private Vector3 pos_vibro;

    Sprite restart= new Sprite(Assets.instance.manager.get(Assets.restart_ico));

    Sprite sprt_mute=new Sprite(Assets.instance.manager.get(Assets.ico_mute));
    Sprite sprt_unmute=new Sprite(Assets.instance.manager.get(Assets.ico_unmute));

    Sprite sprt_vibro=new Sprite(new Texture("vibro_on.png"));
    Sprite sprt_unvibro=new Sprite(new Texture("vibro_off.png"));

    Sprite sprt_music=new Sprite(new Texture(Gdx.files.internal("music_on.png")));
    Sprite sprt_unmusic=new Sprite(new Texture(Gdx.files.internal("music_off.png")));

    Sprite sprt_achiv=new Sprite(new Texture("achives_ico.png"));
    Sprite leaderboard=new Sprite(new Texture("high_ico.png"));

    Sprite plah_bgnd=new Sprite(Assets.instance.manager.get(Assets.plah));
    Sprite bgnd_white= new Sprite(new Texture("white_bck.png"));
    NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("bck.9.png")),10,10,10,10);


    //private static final float POS_X_RESTART=-75,POS_Y_RESTART=-278;

    //public ShaderProgram shader;
    boolean one_time=false;

    private boolean one_click=false;

    public Settings(Preferences prefs) {
        pref=prefs;
       // ShaderProgram.pedantic = false;
       // shader = new ShaderProgram(Gdx.files.internal("shaders/default.vert"),
       //         (Gdx.files.internal("shaders/invertColors.frag")));
       // if (!shader.isCompiled()) {
       //     System.err.println(shader.getLog());
      //      System.exit(0);
       // }

        //Перепроверка ачивок
        if (!pref.contains("achive_0"))
        {
            if (playServices_my!=null) {
                playServices_my.unlockAchievement(0);
                pref.putBoolean("achive_0", true);

            }else
            {
                pref.putBoolean("achive_0", false);
            }
        }
        pref.flush();
        //Первый запуск

        if (!pref.contains("first_start")){

            pref.putBoolean("first_start",false);
            mute=false;
            vibro=true;
            music_b=true;
            pref.putBoolean("mute",mute);
            pref.putBoolean("vibro",vibro);
            pref.flush();
            //System.out.println("First start of game. vibro: "+vibro+" ,mute: "+mute);
        }
        else {
            mute=pref.getBoolean("mute");
            music_b=true;
            vibro=pref.getBoolean("vibro");
        }

        restart.setPosition(60,270);

        //leaderboard.scale(0.8f);
        leaderboard.setPosition(210,310);

        //sprt_achiv.scale(0.4f);
        sprt_achiv.setPosition(320,310);


        //pos_restart = new Vector3(240+POS_X_RESTART,400+POS_Y_RESTART,0);

        //mute_tex= new Texture("sound_off.png");
        //unmute_tex = new Texture("sound_on.png");
        sprt_mute.setPosition(170,395);
        sprt_unmute.setPosition(170,395);

        sprt_music.setPosition(280,395);
        sprt_unmusic.setPosition(280,395);


        //pos_mute = new Vector3(270,400-78,0);

        sprt_vibro.setPosition(60,395);
        sprt_unvibro.setPosition(60,395);



        //vibro_tex = new Texture("vibro_on.png");
        //unvibro_tex = new Texture("vibro_off.png");
        //pos_vibro = new Vector3(70,480,0);
        hi_score_refresh();

        bgnd_white.setPosition(0,0);

        //plah_bgnd.setPosition(70,265);


    }
    public void draw(SpriteBatch sb,Shaker shaker){
       // if (!one_time){
       //     one_time=true;
       //     sb.setShader(shader);
       // }

        bgnd_white.draw(sb);
        patch.draw(sb,70,265,340,270);
        //plah_bgnd.draw(sb);
        sprt_music.draw(sb);

        sprt_achiv.draw(sb);


        leaderboard.draw(sb);

        if (music_b){
            sprt_music.draw(sb);
        }
        else
        {
            sprt_unmusic.draw(sb);
        }

        if (mute){
            sprt_mute.draw(sb);
            //sb.draw(mute_tex,((int) shaker.getCamera_sh().position.x)+30,((int) shaker.getCamera_sh().position.y)-78);
        }else{
            sprt_unmute.draw(sb);
            //sb.draw(unmute_tex,((int) shaker.getCamera_sh().position.x)+30,((int) shaker.getCamera_sh().position.y)-78);
        }
        if (vibro){
            sprt_vibro.draw(sb);
            //sb.draw(vibro_tex,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78);
        }else{
            sprt_unvibro.draw(sb);
            //sb.draw(unvibro_tex,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78);
        }
        restart.draw(sb);
        //sb.draw(restart,((int) shaker.getCamera_sh().position.x)+POS_X_RESTART,((int) shaker.getCamera_sh().position.y)+POS_Y_RESTART);
    }

    void clicked(int ScreenX, int ScreenY) {
        //if (((ScreenY>pos_restart.y)&(ScreenY<pos_restart.y+restart.getHeight()))&
        //        ((ScreenX>pos_restart.x)&(ScreenX<pos_restart.x+restart.getWidth()))){
        if (restart.getBoundingRectangle().contains(ScreenX,ScreenY)){
            //gsm.set(new PlayState(gsm));
            PlayState.RESTART_STAGE();
        }
        else
        //звук
        //if (((ScreenY>pos_mute.y)&(ScreenY<pos_mute.y+mute_tex.getHeight()))&
          //      ((ScreenX>pos_mute.x)&(ScreenX<pos_mute.x+mute_tex.getWidth()))) {
        if (sprt_unmute.getBoundingRectangle().contains(ScreenX,ScreenY)|
                sprt_mute.getBoundingRectangle().contains(ScreenX,ScreenY))
        {
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
        //if (((ScreenY>pos_vibro.y)&(ScreenY<pos_vibro.y+vibro_tex.getHeight()))&
           //  ((ScreenX>pos_vibro.x)&(ScreenX<pos_vibro.x+vibro_tex.getWidth()))) {
        if (sprt_vibro.getBoundingRectangle().contains(ScreenX,ScreenY)|
                sprt_unvibro.getBoundingRectangle().contains(ScreenX,ScreenY))
            {
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
        //leaderboard
        else if (leaderboard.getBoundingRectangle().contains(ScreenX,ScreenY)){
            if (playServices_my!=null) {
                playServices_my.submitScore(pref.getInteger("highscore"));
                playServices_my.showScore();
            }
        }
        else if (sprt_achiv.getBoundingRectangle().contains(ScreenX,ScreenY)){
            if (playServices_my!=null){
                playServices_my.showAchievement();
            }

        }
        else if (sprt_music.getBoundingRectangle().contains(ScreenX,ScreenY)|
                sprt_unmusic.getBoundingRectangle().contains(ScreenX,ScreenY)){
            music_b=!music_b;
            //score_num.addScore(1500);
        }
        else
        //вывод из паузы
        {
           // if (one_click) {
            if (ScreenY<780) {
                PlayState.setUNPAUSE();
            }
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

    public void hi_score_refresh(){
        hi_score= pref.getInteger("highscore");
    }
    public int hi_score(){
        return hi_score;
    }
}
