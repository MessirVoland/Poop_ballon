package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Sprites.Star;
import ru.asupd.poop_ballon.Workers.Achievement;
import ru.asupd.poop_ballon.Workers.Assets;
import ru.asupd.poop_ballon.Workers.Score;

import static ru.asupd.poop_ballon.MyGdxGame.showed_ads;
import static ru.asupd.poop_ballon.States.PlayState.settings;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.ANIMATION_TIME_TAP_TO_PLAY;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.ANIMATIO_TIME_TO_PLAY_SIZE;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.APP_STORE_NAME;

/**
 * Created by Voland on 22.08.2017.
 */

public class GameoverState extends State {
    TextureRegion background;
    Texture your_score,your_best_score,awesome;

    Sprite tap_to_restart;
    private float current_tap_to_restart;

    Texture numbers;
    Score score= new Score();
    Score score_b= new Score();

    Array<TextureRegion> frames_numbers;

    float  currentdt, waiting;
    final BitmapFont FontRed1;
    final Preferences prefs;
    int load_hiscore,last_score;
    int[] score_best =new int[5];
    int[] score_last =new int[5];
    Array<Star> final_stars;

    Vector3 velosity,position;
    Texture big_balloon;
    private boolean add_hicsore=true;
    public static final Achievement achievement= new Achievement();

    Sprite progress_bar_bgnd= new Sprite(new Texture(Gdx.files.internal("bar.png")));

    NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("progress.9.png")),10,10,10,10);



    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public GameoverState(GameStateManager gsm, float redball_x, Array<Star> stars) {
        super(gsm);
        //redball_x=-190;
        //System.out.println("redball_x: "+redball_x);
        camera.setToOrtho(false, 480 , 800 );
        this.final_stars=stars;
        current_tap_to_restart=0;
        progress_bar_bgnd.setPosition(15,15);



        //background = new Texture("background_night.png");
        background = new TextureRegion((Texture)(Assets.instance.manager.get(Assets.back_ground_atlas)));
        background = new TextureRegion(background,4*background.getRegionWidth()/4,0,background.getRegionWidth()/4,background.getRegionHeight());

        Texture texture_t=new Texture("restart.png");
        texture_t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tap_to_restart =new Sprite(texture_t);
        tap_to_restart.setPosition(60,660);

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
        settings.hi_score_refresh();
        achievement.start_anim();
        score.addScore(last_score);
        score_b.setScore(load_hiscore);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
           gsm.set(new PlayState(gsm));
            achievement.refr();
           PlayState.RESTART_STAGE();
           // gsm.pop();
        }
    }

    @Override
    public void update(float dt) {
        score.update(dt);
        score_b.update(dt);
        achievement.update(dt);
        if (currentdt>=waiting) {
            handleInput();
        }
        currentdt+=dt;
        if (position.x>=-865) {
            velosity.scl(dt);
            position.add(velosity.x, 0, 0);
            velosity.scl(1 / dt);
        }
        current_tap_to_restart+=dt;
        if (current_tap_to_restart<=ANIMATION_TIME_TAP_TO_PLAY){
            tap_to_restart.scale(ANIMATIO_TIME_TO_PLAY_SIZE);
        }
        else
        {
            current_tap_to_restart=0;
            ANIMATIO_TIME_TO_PLAY_SIZE=-ANIMATIO_TIME_TO_PLAY_SIZE;
        }

        if (add_hicsore){
            if (score.getScore(true)>score_b.getScore()){
                add_hicsore=false;
                score_b.addScore(score.getScore()-score_b.getScore());
                prefs.putInteger("highscore", score.getScore());
                prefs.flush();
            }
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
        tap_to_restart.draw(sb);



        if(achievement.draw_current_medal(sb)){
            if (!add_hicsore) {
                sb.draw(awesome, 95, 530, 290, 55);
            }
            sb.draw(your_score,105,400,120,75);

            score.draw_center(sb, 115,320);
            //sb.draw(frames_numbers.get(score_last[0]),175,320,25,31);
            //sb.draw(frames_numbers.get(score_last[1]),155,320,25,31);
            //sb.draw(frames_numbers.get(score_last[2]),135,320,25,31);
            //sb.draw(frames_numbers.get(score_last[3]),115,320,25,31);
        }
        else{
            if (!add_hicsore) {
                sb.draw(awesome, 95, 530, 290, 55);
            }
            sb.draw(your_score,175,450,120,75);

            score.draw_center(sb, 185,400);

            //sb.draw(frames_numbers.get(score_last[0]),245,320,25,31);
            //sb.draw(frames_numbers.get(score_last[1]),225,320,25,31);
            //sb.draw(frames_numbers.get(score_last[2]),205,320,25,31);
            //sb.draw(frames_numbers.get(score_last[3]),185,320,25,31);
        }


        if (showed_ads){

            sb.draw(your_best_score, 125, 170, 210, 75);
            score_b.draw_center(sb,185,130);
           // sb.draw(frames_numbers.get(score_best[0]), 245, 130, 25, 31);
           // sb.draw(frames_numbers.get(score_best[1]), 225, 130, 25, 31);
           // sb.draw(frames_numbers.get(score_best[2]), 205, 130, 25, 31);
           // sb.draw(frames_numbers.get(score_best[3]), 185, 130, 25, 31);
            //sb.draw(big_balloon, position.x, position.y, 860, 800);
        }else {
            sb.draw(your_best_score, 125, 170, 210, 75);

            score_b.draw_center(sb,185,120);
            //sb.draw(frames_numbers.get(score_best[0]), 245, 50, 25, 31);
            //sb.draw(frames_numbers.get(score_best[1]), 225, 50, 25, 31);
            //sb.draw(frames_numbers.get(score_best[2]), 205, 50, 25, 31);
            //sb.draw(frames_numbers.get(score_best[3]), 185, 50, 25, 31);

        }
        if (position.x>=-865){
            sb.draw(big_balloon, position.x, position.y, 860, 800);
        }

        //achievement.draw_current_medal(sb,270,200);


        progress_bar_bgnd.draw(sb);
        if (score_b.getScore()<1500) {
            patch.draw(sb, progress_bar_bgnd.getX() + 13, progress_bar_bgnd.getY() + 10, (score_b.getScore(true)*404)/1500+20, 50);
        }


       // FontRed1.draw(sb, " Hi Score: "+  load_hiscore, 10, 250);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
