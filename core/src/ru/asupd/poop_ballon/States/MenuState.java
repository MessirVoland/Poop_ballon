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
    private Texture playBtn;
    private Texture balloon;
    private Vector3 position;
    private Vector3 velosity;
    private static final int size=50;



    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 640 , 800 );
        background = new Texture("sky.jpg");
       // playBtn = new Texture("playbtn.png");
        balloon  = new Texture("Click_to_start.png");
       // position = new Vector3(100, 100, 0);
      //  velosity = new Vector3(0, 0, 0);
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
      //  sb.draw(playBtn, (FlappyDemo.WIDTH / 2) - (playBtn.getWidth() / 2), FlappyDemo.HEIGHT / 2);
       sb.draw(balloon,120,200,400,400);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        balloon.dispose();

    }
}
