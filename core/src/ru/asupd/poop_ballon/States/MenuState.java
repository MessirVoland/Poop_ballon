package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Locale;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Sprites.Cloud;
import ru.asupd.poop_ballon.Workers.Assets;
import ru.asupd.poop_ballon.Workers.Network_time;
import ru.asupd.poop_ballon.Workers.Settings;
import ru.asupd.poop_ballon.Workers.Sound_effects;

import static com.badlogic.gdx.math.MathUtils.random;
import static ru.asupd.poop_ballon.MyGdxGame.adsController_my;
import static ru.asupd.poop_ballon.MyGdxGame.playServices_my;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.APP_STORE_NAME;

/**
 * Меню
 * Created by Voland on 04.08.2017.
 */

public class MenuState extends State {

    private Texture background;
    private Texture balloon;
    private Sprite logo;
    Texture big_balloon;
    Vector3 velosity,position;
    boolean red_balloon_trigger=false;
    public static Array<Cloud> clouds;//массив шаров
    private float current_dt=0;
    private BitmapFont FontRed1;
    private boolean apear=true,disapear=false;
    //private Network_time network_time;
    private boolean one_time_play_state_load=true;
    private boolean one_time_play_snd=true;
    PlayState playState;
    public static Sound_effects sound_effects=new Sound_effects();
    public static Settings settings;
    int var;
    final String FONT_CHARS = "本土化ローカリゼーションабвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";



    public MenuState(GameStateManager gsm) {
        super(gsm);

        //String extRoot = Gdx.files.getExternalStoragePath();
        //String locRoot = Gdx.files.getLocalStoragePath();
        //System.out.println("extRoot "+extRoot);
        //System.out.println("locRoot "+locRoot);

        //синхронизация счета с файлом
        //load_from_file();

        var = random(5);

        //network_time = new Network_time();
       // network_time = new Network_time();
        //network_time.get();

        camera.setToOrtho(false,480,800 );

        background = new Texture("background_start.png");
        balloon  = new Texture("c_logo.png");
        balloon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new Sprite(balloon);
        logo.setPosition(22,320);
        //balloon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        FontRed1 = new BitmapFont();
        final String FONT_PATH = "coquettec.ttf";

        //Попытка запустить ГУГЛ плей сервисы атоматически
        //Кстати костыль, пробовать это делать только когда реклама смогла получить доступ в интеренет.
        if (playServices_my!=null) {
            if (playServices_my.isOnline()) {
                 playServices_my.signIn();
            }
        }
        //инициализация массива облаков
        clouds = new Array<Cloud>();
        for (int i = 0; i <= 4; i++){
            //clouds.add(new Cloud(random(1000)-400,125*i+100+10,-random(25)-25));
            clouds.add(new Cloud(random(680)-222,125*i+100+10,-random(25)-25));
        }
        big_balloon= new Texture("big_balloon.png");
        position = new Vector3(480,0,0);
        velosity=new Vector3(-1200,0,0);
    }
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            if (current_dt>=0.25f){
                //current_dt=4.5f;
              //  gsm.set(new PlayState(gsm));
            }

        }
    }

    @Override
    public void update(float dt) {
//пробую раскинуть комкенты
        handleInput();
        current_dt+=dt;
        if (current_dt<=1.0f){
            //сука
            //apear=true;
            //появление
            //не работает корректно
        }else
         //ьтпл гачало времени
        {
            //если ты это читаешь то знай что я умер тут нахуй
            if (one_time_play_state_load){
                one_time_play_state_load=false;
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        playState=new PlayState(gsm);
                        PlayState.set_mus();
                    }
                });
            }
            apear=false;
        }

        if (current_dt>=5.0f){
            disapear=true;

        }else{
            disapear=false;
        }

        if (current_dt>=5.0f){
            //Assets.make_linear();
            if (one_time_play_snd) {
                one_time_play_snd=false;
                sound_effects.snd_big_baloon();
                Assets.make_resized_balloons_linear();
                Assets.make_ads_linear();
                red_balloon_trigger = true;
            }

        }

        if (red_balloon_trigger){
            if (position.x>=-190) {
                velosity.scl(dt);
                position.add(velosity.x, 0, 0);
                velosity.scl(1 / dt);
            }else
            {
                red_balloon_trigger=false;
                playState.setPosition_red(position);
                if (settings.isMus()){
                    PlayState.set_unmus();
                }

                gsm.set(playState);
            }
        }


        for (Cloud cloud : clouds) {
            cloud.update(dt);
            if (cloud.getPosition().x < -222) {
                cloud.setPosition(+480 + 222 + random(0), cloud.getPosition().y);
                cloud.change_texture();
            }
        }


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0,480,800);

        for (Cloud cloud : clouds) {
            sb.setColor(1,1,1,0.9f);
            sb.draw(cloud.getTexture(),cloud.getPosition().x,cloud.getPosition().y);
            sb.setColor(1,1,1,1);
        }

        //sb.draw(balloon,22,320,436,370);
        if (!red_balloon_trigger) {
            if (apear) {
                logo.draw(sb, current_dt);
            } else if (disapear) {
                logo.draw(sb, 3.0f - current_dt);
            } else {
                logo.draw(sb);
            }
        }

                //
        //Assets.loadParticleEffects();
        //FontRed1.draw(sb, " Время до старта 4.0f : "+ current_dt, 15, 790);
        //while (!Assets.instance.manager.update()) {
        //    FontRed1.draw(sb, " Loanding >>> " + +Assets.instance.manager.getProgress() * 100 + "%", 15, 730);
       // }


        /*
        switch (Gdx.app.getType()){
            case Desktop:
                FontRed1.draw(sb, " PC Версия", 15, 760);
                break;
            case Android:
                FontRed1.draw(sb, " Android Версия", 15, 760);
                break;
            case WebGL:
                FontRed1.draw(sb, " HTML5 asigned", 15, 760);
                break;
        }*/
        // = 30;
        FontRed1.setColor(Color.CHARTREUSE);
        FontRed1.draw(sb,"Pop Balloon v.0.9.8.9-beta-release", 10, 20);
        //FontRed1.draw(sb," Android API level :"+Gdx.app.getVersion(), 15, 70);
        /*String st_locale=new String("");
        switch (var){
            case 0:
                st_locale=" Локализация :";
                //Locale.setDefault(Locale.ROOT);
                break;
            case 1:
                st_locale=" 本土化 :";
                Locale.setDefault(Locale.TRADITIONAL_CHINESE);
                break;
            case 2:
                st_locale=" Localisation :";
                Locale.setDefault(Locale.FRANCE);
                break;
            case 3:
                st_locale=" ローカリゼーション :";
                Locale.setDefault(Locale.JAPAN);
                break;
            case 4:
                st_locale=" Localization :";
                Locale.setDefault(Locale.ENGLISH);
                break;
        }
        FontRed1.draw(sb,st_locale + Locale.getDefault(), 15, 40);*/
        //FontRed1.draw(sb," Локализация :"+ Locale.getDefault().getCountry(), 15, 40);
        //FontRed1.draw(sb," Локализация :"+ Locale.getDefault().getCountry(), 15, 40);
        //FontRed1.draw(sb," Локализация :"+ Locale.getDefault().getCountry(), 15, 40);
        if (position.x>=-865){
            sb.draw(big_balloon, position.x, position.y, 860, 800);
        }
        sb.end();

    }

    @Override
    public void dispose() {
        //Assets.dispose();
      }

      //Судя по всему попытка загрузить сейвы из файла
    public void load_from_file(){
        //переменные
        FileHandle file = Gdx.files.external("saved_score.txt");
        Preferences preferences= Gdx.app.getPreferences(APP_STORE_NAME);;
        int hi_score=0;
        //Если существует файл
        if (file.exists()){
            hi_score= Integer.parseInt(file.readString());
        }
        //если не первый старт
        if (!preferences.getBoolean("first_start")){

            if (hi_score>preferences.getInteger("highscore")){
                preferences.putInteger("highscore",hi_score);
                preferences.flush();
            }
            else
            {
                file.writeString(String.valueOf(preferences.getInteger("highscore")),false);
            }
        }
        else
        {
            preferences.putInteger("highscore",hi_score);
            preferences.flush();
        }
    }
}
