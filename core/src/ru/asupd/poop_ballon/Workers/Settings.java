package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.asupd.poop_ballon.States.PlayState;

import static ru.asupd.poop_ballon.MyGdxGame.playServices_my;
import static ru.asupd.poop_ballon.States.PlayState.score_num;
import static ru.asupd.poop_ballon.States.MenuState.sound_effects;

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

    int change_size=0;
    boolean any_button=false;
    float current_dt=0.0f;
    Sprite game_name=new Sprite(new Texture("poop_balloon.png"));

    Sprite restart= new Sprite(Assets.instance.manager.get(Assets.restart_ico));

    Sprite sprt_mute=new Sprite(Assets.instance.manager.get(Assets.ico_mute));
    Sprite sprt_unmute=new Sprite(Assets.instance.manager.get(Assets.ico_unmute));

    Sprite sprt_vibro=new Sprite(new Texture("vibro_on.png"));
    Sprite sprt_unvibro=new Sprite(new Texture("vibro_off.png"));

    Sprite sprt_music=new Sprite(new Texture(Gdx.files.internal("music_on.png")));
    Sprite sprt_unmusic=new Sprite(new Texture(Gdx.files.internal("music_off.png")));

    Sprite sprt_achiv=new Sprite(new Texture("achives_ico.png"));
    Sprite leaderboard=new Sprite(new Texture("high_ico.png"));

    private Sprite button_vk=new Sprite(Assets.instance.manager.get(Assets.button_vk));
    private Sprite button_fb=new Sprite(Assets.instance.manager.get(Assets.button_fb));

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


        //Первый запуск

        if (!pref.contains("first_start")){

            pref.putBoolean("first_start",false);
            mute=false;
            vibro=true;
            //music=true;
            music_b=true;
            pref.putBoolean("mute",mute);
            pref.putBoolean("vibro",vibro);
            pref.putBoolean("music",music_b);
            pref.flush();
            //System.out.println("First start of game. vibro: "+vibro+" ,mute: "+mute);
        }
        else {
            mute=pref.getBoolean("mute");
            music_b=pref.getBoolean("music");
            vibro=pref.getBoolean("vibro");
        }

        game_name.setPosition(10,500);

        restart.setPosition(60,200);

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


        button_fb.setPosition(260,165);
        //button_fb.scale(-0.4f);

        button_vk.setPosition(340,165);
        //button_vk.scale(-0.4f);

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
        float x=shaker.getCamera_sh().position.x;
        float y=shaker.getCamera_sh().position.y;
        x-=240;
        y-=400;

        //bgnd_white.draw(sb);
        sb.draw(bgnd_white,bgnd_white.getX()+x,bgnd_white.getY()+y);
        patch.draw(sb,45+x,180+y,390,420);   // patch.draw(sb,70,185,340,360);
        //game_name.draw(sb);
        sb.draw(game_name,game_name.getX()+x,game_name.getY()+y);
        //plah_bgnd.draw(sb);
        //sprt_music.draw(sb);
        sprt_achiv.draw(sb);
        //sb.draw(sprt_achiv,sprt_achiv.getX(),sprt_achiv.getY());
        button_fb.draw(sb);
        //sb.draw(button_fb,button_fb.getX()+x,button_fb.getY()+y);
        button_vk.draw(sb);
        //sb.draw(button_vk,button_vk.getX()+x,button_vk.getY()+y);
        leaderboard.draw(sb);
        //sb.draw(leaderboard,leaderboard.getX()+x,leaderboard.getY()+y);


        if (music_b){
            sprt_music.draw(sb);
            //sb.draw(sprt_music,sprt_music.getX()+x,sprt_music.getY()+y);
        }
        else
        {
            sprt_unmusic.draw(sb);
            //sb.draw(sprt_unmusic,sprt_unmusic.getX()+x,sprt_unmusic.getY()+y);
        }

        if (mute){
            sprt_mute.draw(sb);
            //sb.draw(sprt_mute,sprt_mute.getX()+x,sprt_mute.getY()+y);
            //sb.draw(mute_tex,((int) shaker.getCamera_sh().position.x)+30,((int) shaker.getCamera_sh().position.y)-78);
        }else{
            sprt_unmute.draw(sb);
            //sb.draw(sprt_unmute,sprt_unmute.getX()+x,sprt_unmute.getY()+y);
            //sb.draw(unmute_tex,((int) shaker.getCamera_sh().position.x)+30,((int) shaker.getCamera_sh().position.y)-78);
        }

        if (vibro){
            sprt_vibro.draw(sb);
            //sb.draw(sprt_vibro,sprt_vibro.getX()+x,sprt_vibro.getY()+y);
            //sb.draw(vibro_tex,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78);
        }else{
            sprt_unvibro.draw(sb);
            //sb.draw(sprt_unvibro,sprt_unvibro.getX()+x,sprt_unvibro.getY()+y);
            //sb.draw(unvibro_tex,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78);
        }

        restart.draw(sb);
        //sb.draw(restart,restart.getX()+x,restart.getY()+y);
        //sb.draw(restart,((int) shaker.getCamera_sh().position.x)+POS_X_RESTART,((int) shaker.getCamera_sh().position.y)+POS_Y_RESTART);
    }

    void clicked(int ScreenX, int ScreenY) {
        if (any_button){
            button_press();
        }
        change_size=0;
        //if (((ScreenY>pos_restart.y)&(ScreenY<pos_restart.y+restart.getHeight()))&
        //        ((ScreenX>pos_restart.x)&(ScreenX<pos_restart.x+restart.getWidth()))){
        if (restart.getBoundingRectangle().contains(ScreenX,ScreenY)){
            //gsm.set(new PlayState(gsm));
            restart.scale(-0.2f);
            change_size=1;
            sound_effects.click_sound();


        }
        else
        //звук
        //if (((ScreenY>pos_mute.y)&(ScreenY<pos_mute.y+mute_tex.getHeight()))&
          //      ((ScreenX>pos_mute.x)&(ScreenX<pos_mute.x+mute_tex.getWidth()))) {
        if (sprt_unmute.getBoundingRectangle().contains(ScreenX,ScreenY)|
                sprt_mute.getBoundingRectangle().contains(ScreenX,ScreenY))
        {
            sprt_mute.scale(-0.2f);
            sprt_unmute.scale(-0.2f);
            change_size=2;
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
            sound_effects.click_sound();
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
                sprt_unvibro.scale(-0.2f);
                sprt_vibro.scale(-0.2f);
                change_size=3;
                sound_effects.click_sound();
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
            sound_effects.click_sound();
            leaderboard.scale(-0.2f);
            change_size=4;
            if (playServices_my!=null) {
                playServices_my.submitScore(pref.getInteger("highscore"));
                playServices_my.showScore();
            }
        }

        else if (button_fb.getBoundingRectangle().contains(ScreenX,ScreenY)){
            sound_effects.click_sound();
            button_fb.scale(-0.2f);
            change_size=5;

        }

        else if (button_vk.getBoundingRectangle().contains(ScreenX,ScreenY)){
            sound_effects.click_sound();
            button_vk.scale(-0.2f);
            change_size=6;

        }

        else if (sprt_achiv.getBoundingRectangle().contains(ScreenX,ScreenY)){
            sprt_achiv.scale(-0.2f);
            change_size=7;
            sound_effects.click_sound();
            if (playServices_my!=null){
                playServices_my.showAchievement();

            }

        }
        else if (sprt_music.getBoundingRectangle().contains(ScreenX,ScreenY)|
                sprt_unmusic.getBoundingRectangle().contains(ScreenX,ScreenY)){
            music_b=!music_b;
            if (music_b) {
                //mute=false;
                //System.out.println("unmute");
                PlayState.set_unmus();
            }
            else
            {
                //mute=true;
                //System.out.println("mute");
                PlayState.set_mus();
            }
            pref.putBoolean("music", music_b);
            pref.flush();
            sprt_music.scale(-0.2f);
            change_size=8;
            sprt_unmusic.scale(-0.2f);

            sound_effects.click_sound();

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
    public void clicked_up(int ScreenX, int ScreenY) {
        any_button=true;
        current_dt=0.0f;

        if (restart.getBoundingRectangle().contains(ScreenX,ScreenY)) {
            //restart.scale(0.2f);
            if (change_size==1) {

                PlayState.RESTART_STAGE();
            }
        }

        else if (sprt_music.getBoundingRectangle().contains(ScreenX,ScreenY)|
                sprt_unmusic.getBoundingRectangle().contains(ScreenX,ScreenY)){
            //sprt_music.scale(0.2f);
            //sprt_unmusic.scale(0.2f);

            //score_num.addScore(1500);
        }else if (sprt_unmute.getBoundingRectangle().contains(ScreenX,ScreenY)|
                sprt_mute.getBoundingRectangle().contains(ScreenX,ScreenY))
        {
            //sprt_mute.scale(0.2f);
            //sprt_unmute.scale(0.2f);
        }
        else if (button_fb.getBoundingRectangle().contains(ScreenX,ScreenY)){
            sound_effects.click_sound();
            //button_fb.scale(0.2f);
            Gdx.net.openURI("https://www.facebook.com/groups/detone.games/");
        }

        else if (button_vk.getBoundingRectangle().contains(ScreenX,ScreenY)){
            sound_effects.click_sound();
            //button_vk.scale(0.2f);
            Gdx.net.openURI("https://vk.com/detone_games");
        }
        else if (sprt_vibro.getBoundingRectangle().contains(ScreenX,ScreenY)|
                sprt_unvibro.getBoundingRectangle().contains(ScreenX,ScreenY)) {
            //sprt_unvibro.scale(0.2f);
            //sprt_vibro.scale(0.2f);
        }
        else if (leaderboard.getBoundingRectangle().contains(ScreenX,ScreenY)){
            //leaderboard.scale(0.2f);
        }
        else if (sprt_achiv.getBoundingRectangle().contains(ScreenX,ScreenY)){
            //sprt_achiv.scale(0.2f);
        }
        else
        {

        }
    }

    public boolean isMute() {
        return mute;
    }
    public void button_press(){
        current_dt=0.0f;
        any_button=false;
        switch (change_size){
            case 1:
                restart.scale(0.2f);
                break;
            case 2:
                sprt_mute.scale(0.2f);
                sprt_unmute.scale(0.2f);
                break;
            case 3:
                sprt_unvibro.scale(0.2f);
                sprt_vibro.scale(0.2f);
                break;
            case 4:
                leaderboard.scale(0.2f);
                break;
            case 5:
                button_fb.scale(0.2f);
                break;
            case 6:
                button_vk.scale(0.2f);
                break;
            case 7:
                sprt_achiv.scale(0.2f);
                break;
            case 8:
                sprt_music.scale(0.2f);
                sprt_unmusic.scale(0.2f);
                break;
        }
    }

    public void update(float dt){
        if (any_button){
            current_dt+=dt;
            if (current_dt>=0.25f){
                button_press();
            }
        }
    }

    public boolean isMus() {
        return music_b;
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
