package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.asupd.poop_ballon.GameStateManager;

/**
 * Created by Voland on 22.08.2017.
 */

public class GameoverState extends State {
    private Texture background;
    float currentdt, waiting;
    private BitmapFont FontRed1;
    public Preferences prefs;
    private static final String APP_STORE_NAME = "Poop_ballons_90471d221cb7702a2b7ab38a5433c26e";
    int load_hiscore;


    public GameoverState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 320 , 440 );
        background = new Texture("Game_over.png");
        prefs = Gdx.app.getPreferences(APP_STORE_NAME);
        load_hiscore = prefs.getInteger("highscore");
        currentdt=0;
        waiting=1.0f;

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        if (currentdt>=waiting) {
            handleInput();
        }
        currentdt+=dt;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0,320,440);


        FontRed1.draw(sb, " Hi Score: "+  load_hiscore, 10, 250);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
