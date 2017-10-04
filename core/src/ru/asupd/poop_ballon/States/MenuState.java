package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Workers.Assets;

/**
 * Меню
 * Created by Voland on 04.08.2017.
 */

public class MenuState extends State {

    private Texture background;
    private Texture balloon;
    private float current_dt=0;
    private BitmapFont FontRed1;
    final String FONT_CHARS = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";



    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false,640,800 );
        background = new Texture("background_start.png");
        balloon  = new Texture("tap.png");
        FontRed1 = new BitmapFont();
        final String FONT_PATH = "coquettec.ttf";
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        parameter.size = 25;
        parameter.color = Color.BLACK;
        FontRed1 = generator.generateFont(parameter);
        generator.dispose();
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
       // if(Gdx.input.justTouched()){
        //    gsm.set(new PlayState(gsm));
       // }

    }

    @Override
    public void update(float dt) {
        handleInput();
        current_dt+=dt;
        if (current_dt>=4.0f){
            Assets.make_linear();
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0,640,800);
        sb.draw(balloon,120,100,405,88);

                //
        //Assets.loadParticleEffects();
        FontRed1.draw(sb, " Время до старта 4.0f : "+ current_dt, 15, 790);
        while (!Assets.instance.manager.update()) {
            FontRed1.draw(sb, " Загрузка >>> " + +Assets.instance.manager.getProgress() * 100 + "%", 15, 730);
        }

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
        }
        // = 30;
        FontRed1.draw(sb," Pop Balloons v.0.9.7-beta-pre-release.rev.A.build.15", 15, 70);
        FontRed1.draw(sb," Android API level :"+Gdx.app.getVersion(), 15, 40);
        sb.end();

    }

    @Override
    public void dispose() {
        //Assets.dispose();
      }
}
