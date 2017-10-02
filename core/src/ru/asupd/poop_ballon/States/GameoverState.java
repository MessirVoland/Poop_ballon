package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Sprites.Star;
import ru.asupd.poop_ballon.Workers.Assets;

/**
 * Created by Voland on 22.08.2017.
 */

public class GameoverState extends State {
    TextureRegion background;
    Texture tap_to_restart,your_score,your_best_score,awesome;
    Texture numbers;
    Array<TextureRegion> frames_numbers;
    float  currentdt, waiting;
    final BitmapFont FontRed1;
    final Preferences prefs;
    private static final String APP_STORE_NAME = "Poop_ballons_90471d221cb7702a2b7ab38a5433c26e";
    int load_hiscore,last_score;
    int[] score_best =new int[5];
    int[] score_last =new int[5];
    Array<Star> final_stars;

    Vector3 velosity,position;
    Texture big_balloon;


    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public GameoverState(GameStateManager gsm, float redball_x, Array<Star> stars) {
        super(gsm);
        //redball_x=-190;
        //System.out.println("redball_x: "+redball_x);
        camera.setToOrtho(false, 480 , 800 );
        this.final_stars=stars;

        //background = new Texture("background_night.png");
        background = new TextureRegion((Texture)(Assets.instance.manager.get(Assets.back_ground_atlas)));
        background = new TextureRegion(background,4*background.getRegionWidth()/4,0,background.getRegionWidth()/4,background.getRegionHeight());

        tap_to_restart = new Texture("restart.png");

        your_score = new Texture("your_score.png");
        your_best_score = new Texture("best_score_r.png");
        awesome = new Texture("awesome.png");

        numbers = new Texture("numbers.png");
        big_balloon= new Texture("big_balloon.png");
        position = new Vector3(redball_x,0,0);
        velosity=new Vector3(-1200,0,0);
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
        if (position.x>=-865) {
            velosity.scl(dt);
            position.add(velosity.x, 0, 0);
            velosity.scl(1 / dt);
        }
        //System.out.println("position.x: "+position.x);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0,480,800);
        for (Star star : final_stars ){
            sb.draw(star.getTexture(),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
            // sb.draw(Assets.instance.manager.get(Assets.star1),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
        }
        sb.draw(tap_to_restart,60,600,360,50);
        if (last_score>=load_hiscore) {
            sb.draw(awesome, 95, 480, 290, 55);
        }
        sb.draw(your_score,175,400,120,75);

        sb.draw(frames_numbers.get(score_last[0]),245,320,25,31);
        sb.draw(frames_numbers.get(score_last[1]),225,320,25,31);
        sb.draw(frames_numbers.get(score_last[2]),205,320,25,31);
        sb.draw(frames_numbers.get(score_last[3]),185,320,25,31);

        sb.draw(your_best_score,125,100,210,75);

        sb.draw(frames_numbers.get(score_best[0]),245,50,25,31);
        sb.draw(frames_numbers.get(score_best[1]),225,50,25,31);
        sb.draw(frames_numbers.get(score_best[2]),205,50,25,31);
        sb.draw(frames_numbers.get(score_best[3]),185,50,25,31);
        sb.draw(big_balloon,position.x,position.y,860,800);




       // FontRed1.draw(sb, " Hi Score: "+  load_hiscore, 10, 250);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
