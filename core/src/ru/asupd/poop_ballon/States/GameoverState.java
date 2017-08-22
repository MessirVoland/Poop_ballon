package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.asupd.poop_ballon.GameStateManager;

/**
 * Created by Voland on 22.08.2017.
 */

public class GameoverState extends State {
    private Texture background;
    public GameoverState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 320 , 440 );
        background = new Texture("Game_over.png");

    }

    @Override
    protected void handleInput() {
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
        sb.draw(background, 0, 0,320,440);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
