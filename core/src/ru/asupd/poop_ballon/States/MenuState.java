package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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



    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false,640,800 );
        background = new Texture("background_start.png");
        balloon  = new Texture("tap.png");
        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Assets.instance.load(new AssetManager());

            }
        });
    }
    @Override
    public void handleInput() {
        //if(Gdx.input.justTouched()){
          //  gsm.set(new PlayState(gsm));
       // }

    }

    @Override
    public void update(float dt) {
        handleInput();
        current_dt+=dt;
        if (current_dt>=3.0f){
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
        FontRed1.draw(sb, " Time to START 3.0f : "+ current_dt, 10, 790);
        while (!Assets.instance.manager.update()) {
            FontRed1.draw(sb, "LOADING >>> " + +Assets.instance.manager.getProgress() * 100 + "%", 10, 775);
        }

        switch (Gdx.app.getType()){
            case Desktop:
                FontRed1.draw(sb, "Desktop asigned", 10, 760);
                break;
            case Android:
                FontRed1.draw(sb, "Android asigned", 10, 760);
                break;
            case WebGL:
                FontRed1.draw(sb, "HTML5 asigned", 10, 760);
                break;
        }
        sb.end();

    }

    @Override
    public void dispose() {
        //Assets.dispose();
      }
}
