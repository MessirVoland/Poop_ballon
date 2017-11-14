package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Locale;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Workers.Assets;
import ru.asupd.poop_ballon.Workers.Network_time;

import static com.badlogic.gdx.math.MathUtils.random;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.APP_STORE_NAME;

/**
 * Меню
 * Created by Voland on 04.08.2017.
 */

public class MenuState extends State {

    private Texture background;
    private Texture balloon;
    private float current_dt=0;
    private BitmapFont FontRed1;
    //private Network_time network_time;
    int var;
    final String FONT_CHARS = "本土化ローカリゼーションабвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";



    public MenuState(GameStateManager gsm) {
        super(gsm);

        //String extRoot = Gdx.files.getExternalStoragePath();
        //String locRoot = Gdx.files.getLocalStoragePath();
        //System.out.println("extRoot "+extRoot);
        //System.out.println("locRoot "+locRoot);
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

        var = random(5);

        //network_time = new Network_time();
       // network_time = new Network_time();
        //network_time.get();

        camera.setToOrtho(false,480,800 );

        background = new Texture("background_start.png");
        balloon  = new Texture("c_logo.png");
        balloon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //balloon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        FontRed1 = new BitmapFont();
        final String FONT_PATH = "coquettec.ttf";
   //     //final String FONT_PATH = "Arialuni.ttf";
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
 //       FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
  //      parameter.characters = FONT_CHARS;
   //     parameter.size = 25;
  //      parameter.color = Color.BLACK;
   //     FontRed1 = generator.generateFont(parameter);
   //     generator.dispose();
        //FontRed1.setColor(Color.RED);

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Assets.instance.load(new AssetManager());

            }
        });
    }
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            if (current_dt>=0.25f){
                current_dt=4.5f;
            }
            gsm.set(new PlayState(gsm));
        }



    }

    @Override
    public void update(float dt) {
        handleInput();
        current_dt+=dt;

        if (current_dt>=4.0f){
            //Assets.make_linear();
            Assets.make_resized_balloons_linear();
            Assets.make_ads_linear();
           // gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0,480,800);
        sb.draw(balloon,22,320,436,370);

                //
        //Assets.loadParticleEffects();
        //FontRed1.draw(sb, " Время до старта 4.0f : "+ current_dt, 15, 790);
        while (!Assets.instance.manager.update()) {
            FontRed1.draw(sb, " Loanding >>> " + +Assets.instance.manager.getProgress() * 100 + "%", 15, 730);
        }


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
        FontRed1.draw(sb,"Pop Balloon v.0.9.9-beta-pre-release.rev.A.build.52", 10, 20);
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

        sb.end();

    }

    @Override
    public void dispose() {
        //Assets.dispose();
      }
}
