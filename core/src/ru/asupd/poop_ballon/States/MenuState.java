package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ru.asupd.poop_ballon.GameStateManager;

/**
 * Created by Voland on 04.08.2017.
 */

public class MenuState extends State {

    private Texture background;
    private Texture balloon;



    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false,640,800 );
        background = new Texture("background_start.png");
        balloon  = new Texture("tap.png");
    }
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0,640,800);
        sb.draw(balloon,120,100,405,88);
        sb.end();

    }

    @Override
    public void dispose() {
      }
}
