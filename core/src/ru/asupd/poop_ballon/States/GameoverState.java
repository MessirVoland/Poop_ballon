package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.GameStateManager;

/**
 * Created by Voland on 22.08.2017.
 */

public class GameoverState extends State {
    private Texture background;
    private Texture tap_to_restart,your_score,your_best_score;
    private Texture numbers;
    private Array<TextureRegion> frames_numbers;
    float currentdt, waiting;
    private BitmapFont FontRed1;
    public Preferences prefs;
    private static final String APP_STORE_NAME = "Poop_ballons_90471d221cb7702a2b7ab38a5433c26e";
    int load_hiscore,last_score;
    int[] score_best =new int[5];
    int[] score_last =new int[5];


    public GameoverState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 480 , 800 );
        background = new Texture("background_night.png");
        tap_to_restart = new Texture("restart.png");

        your_score = new Texture("your_score.png");
        your_best_score = new Texture("best_score_r.png");

        numbers = new Texture("numbers.png");
        frames_numbers = new Array<TextureRegion>();

        for (int j=0;j<=9;j++){
            frames_numbers.add(new TextureRegion(numbers,j*25,0,25,31));
        }


        prefs = Gdx.app.getPreferences(APP_STORE_NAME);

        load_hiscore = prefs.getInteger("highscore");
        int local_score;

        local_score = load_hiscore;
        for (int k=0;k<=4;k++) {

            score_best[k] = local_score % 10;
            local_score = local_score / 10;
        }

        last_score = prefs.getInteger("last_match_score");
        local_score = last_score;
        for (int k=0;k<=4;k++) {

            score_last[k] = local_score % 10;
            local_score = local_score / 10;
        }

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный
        currentdt=0;
        waiting=1.5f;

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
           // gsm.pop();
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
        sb.draw(background, 0, 0,480,800);
        sb.draw(your_score,175,600,120,75);

        sb.draw(frames_numbers.get(score_last[0]),245,520,25,31);
        sb.draw(frames_numbers.get(score_last[1]),225,520,25,31);
        sb.draw(frames_numbers.get(score_last[2]),205,520,25,31);
        sb.draw(frames_numbers.get(score_last[3]),185,520,25,31);

        sb.draw(your_best_score,125,300,210,75);

        sb.draw(frames_numbers.get(score_best[0]),245,250,25,31);
        sb.draw(frames_numbers.get(score_best[1]),225,250,25,31);
        sb.draw(frames_numbers.get(score_best[2]),205,250,25,31);
        sb.draw(frames_numbers.get(score_best[3]),185,250,25,31);


       // FontRed1.draw(sb, " Hi Score: "+  load_hiscore, 10, 250);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
