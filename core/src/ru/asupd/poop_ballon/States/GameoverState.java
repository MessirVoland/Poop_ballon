package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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


import static ru.asupd.poop_ballon.MyGdxGame.actionresolver_my;
import static ru.asupd.poop_ballon.MyGdxGame.adsController_my;
import static ru.asupd.poop_ballon.MyGdxGame.playServices_my;
import static ru.asupd.poop_ballon.MyGdxGame.showed_ads;
import static ru.asupd.poop_ballon.States.PlayState.getCurrent_difficult_up;
import static ru.asupd.poop_ballon.States.PlayState.settings;
import static ru.asupd.poop_ballon.States.PlayState.upCurrent_difficult_up;
import static ru.asupd.poop_ballon.States.PlayState.zeroCurrent_difficult_up;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.ANIMATION_TIME_TAP_TO_PLAY;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.ANIMATIO_TIME_TO_PLAY_SIZE;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.APP_STORE_NAME;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.NEEDED_SCORE;

/**
 * Created by Voland on 22.08.2017.
 */

public class GameoverState extends State {
    TextureRegion background;
    Texture your_score,your_best_score,awesome;

    Sprite tap_to_restart;
    private float current_tap_to_restart;

    Texture numbers;
    //что за счет
    Score score= new Score(1.0f);
    //и это
    Score score_b= new Score();

    Score score_medal=new Score();
    int score_medal_load;

    Score score_need=new Score();

    Array<TextureRegion> frames_numbers;

    float  currentdt, waiting;
    final BitmapFont FontRed1;
    static final Preferences prefs = Gdx.app.getPreferences(APP_STORE_NAME);;
    int load_hiscore,last_score;
    int size_of_score=6;
    int[] score_best =new int[size_of_score];
    int[] score_last =new int[size_of_score];
    Array<Star> final_stars;
    Sprite dark_medal;
    boolean ads=true;
    boolean start_count_trigger=true;
    boolean start_gain_medal_trigger=true;

    Vector3 velosity,position;
    Texture big_balloon;
    private boolean add_hicsore=true;
    public static final Achievement achievement= new Achievement();
    public static Array<ParticleEffect> special_effects = new Array<ParticleEffect>();

    Sprite progress_bar_bgnd= new Sprite(new Texture(Gdx.files.internal("bar.png")));
    Sprite stick=new Sprite(new Texture("palka.png"));

    NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("progress.9.png")),10,10,10,10);



    public GameoverState(GameStateManager gsm, float redball_x, Array<Star> stars) {
        super(gsm);
        dark_medal = new Sprite(new Texture(Gdx.files.internal("pse-medal.png")));
        dark_medal.setPosition(420,40);
        //redball_x=-190;
        //System.out.println("redball_x: "+redball_x);
        camera.setToOrtho(false, 480 , 800 );
        this.final_stars=stars;
        current_tap_to_restart=0;
        if (showed_ads){
            progress_bar_bgnd.setPosition(15,85);
        }else {
            progress_bar_bgnd.setPosition(15, 15);
        }
        stick.setPosition(210,progress_bar_bgnd.getY()+18);



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




        load_hiscore = prefs.getInteger("highscore");
        //load_hiscore = 0;
        int local_score;

        local_score = load_hiscore;
        for (int k=0;k<size_of_score;k++) {

            score_best[k] = local_score % 10;
            local_score = local_score / 10;
        }

        last_score = prefs.getInteger("last_match_score");
        local_score = last_score;
        for (int k=0;k<size_of_score;k++) {
            score_last[k] = local_score % 10;
            local_score = local_score / 10;
        }
        //Занулятор
        //zeroCurrent_difficult_up();
        //prefs.putInteger("medal_score",4200);
        //score_medal_load=0;
        //prefs.flush();


        if (prefs.contains("medal_score")) {
            score_medal_load = prefs.getInteger("medal_score");
            prefs.putInteger("medal_score",last_score+score_medal_load);
            prefs.flush();
        }
        else
        {
            prefs.putInteger("medal_score",last_score);
            score_medal_load=0;
            prefs.flush();
        }
        score_medal.setScore(score_medal_load);
        score_medal.update_render_time();

        score_need.setScore(NEEDED_SCORE[getCurrent_difficult_up()]);


        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный
        currentdt=0;
        waiting=1.5f;
        settings.hi_score_refresh();
        achievement.start_anim();
        score.addScore(last_score);
        score.update_render_time();
        score_b.setScore(load_hiscore);

        ads=true;


    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            //System.out.println("score.getScore(true)"+score.getScore(true));
            //System.out.println("score.getScore()"+score.getScore());
            if (score.getScore(true)<score.getScore())
            {
                score.end_count();
                System.out.println("S4et");
            }
            else if (score_medal.getScore(true)<score_medal.getScore())
            {
                score_medal.end_count();
            }
            else
            {
                if (special_effects.size<=0) {
                    gsm.set(new PlayState(gsm));
                }
            }
            //score_b.addScore(score.getScore()-score_b.getScore());
          //  prefs.putInteger("highscore", score.getScore());
         //   prefs.flush();
          // achievement.refr();
        //   PlayState.RESTART_STAGE();
           // gsm.pop();
        }
    }

    @Override
    public void update(float dt) {
        score.update(dt);
        score_b.update(dt);
        achievement.update(dt);
        if (start_count_trigger){
            if (score.getScore(true)>=score.getScore()){
                start_count_trigger=false;
                score_medal.addScore(score.getScore());
            }
        }else
        {
            score_medal.update(dt);
        }
        if (start_gain_medal_trigger){
            if (score_medal.getScore()>=score_need.getScore()){
                upCurrent_difficult_up();
                score_need.setScore(NEEDED_SCORE[getCurrent_difficult_up()]);

                special_effects.add(new ParticleEffect(Assets.beam));
                special_effects.get(special_effects.size-1).setPosition(240, 400);
                special_effects.get(special_effects.size-1).start();

                switch (getCurrent_difficult_up()){
                    case 1:
                        special_effects.add(new ParticleEffect(Assets.medal_x2));
                        special_effects.get(special_effects.size - 1).setPosition(240, 400);
                        special_effects.get(special_effects.size - 1).start();
                        break;
                    case 2:
                        special_effects.add(new ParticleEffect(Assets.medal_x3));
                        special_effects.get(special_effects.size - 1).setPosition(240, 400);
                        special_effects.get(special_effects.size - 1).start();
                        break;
                    case 3:
                        special_effects.add(new ParticleEffect(Assets.medal_x4));
                        special_effects.get(special_effects.size - 1).setPosition(240, 400);
                        special_effects.get(special_effects.size - 1).start();
                        break;
                    case 4:
                        special_effects.add(new ParticleEffect(Assets.medal_x5));
                        special_effects.get(special_effects.size - 1).setPosition(240, 400);
                        special_effects.get(special_effects.size - 1).start();
                        break;
                    case 5:
                        special_effects.add(new ParticleEffect(Assets.medal_x6));
                        special_effects.get(special_effects.size - 1).setPosition(240, 400);
                        special_effects.get(special_effects.size - 1).start();
                        break;
                    case 6:
                        special_effects.add(new ParticleEffect(Assets.medal_x7));
                        special_effects.get(special_effects.size - 1).setPosition(240, 400);
                        special_effects.get(special_effects.size - 1).start();
                        break;
                    case 7:
                        special_effects.add(new ParticleEffect(Assets.medal_x8));
                        special_effects.get(special_effects.size - 1).setPosition(240, 400);
                        special_effects.get(special_effects.size - 1).start();
                        break;
                    case 8:
                        special_effects.add(new ParticleEffect(Assets.medal_x9));
                        special_effects.get(special_effects.size - 1).setPosition(240, 400);
                        special_effects.get(special_effects.size - 1).start();
                        break;
                }
            }
            for(ParticleEffect special_effect :special_effects){
                special_effect.update(dt);
            }
        }

        if (currentdt>=waiting) {
            handleInput();
            if (ads){
                if (actionresolver_my!=null)
                {
                    System.out.println("Submit score"+score_b.getScore());
                    playServices_my.submitScore(score_b.getScore());
                    //actionresolver_my.submitScoreGPGS(score_b.getScore());
                }
                if (showed_ads){

                    ads=false;
                    //оказать рекламу
                    System.out.println("adsController show");
                    adsController_my.showBannerAd_full();
                }
            }
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
        //фон
        sb.draw(background, 0, 0,480,800);

        //звезды
        for (Star star : final_stars ){
            sb.draw(star.getTexture(),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
            // sb.draw(Assets.instance.manager.get(Assets.star1),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
        }

        tap_to_restart.draw(sb);



        progress_bar_bgnd.draw(sb);


        draw_progress_bar(sb,(score_medal.getScore(true)*10000)/score_need.getScore());


        dark_medal.draw(sb);
        score_medal.draw_center(sb,(int) progress_bar_bgnd.getX() + 80,(int) progress_bar_bgnd.getY() + 20);
        stick.draw(sb);
        score_need.draw_center(sb,(int) progress_bar_bgnd.getX() + 260,(int) progress_bar_bgnd.getY() + 20);


        // if(
        achievement.draw_current_medal(sb);
        //{
          //  if (!add_hicsore) {
        //        sb.draw(awesome, 95, 530, 290, 55);
        //    }
       //     sb.draw(your_score,105,400,120,75);
        //    score.draw_center(sb, 115,320);
       // }
       // else{
        if (!add_hicsore) {
            sb.draw(awesome, 95, 530, 290, 55);
        }
        sb.draw(your_score,175,450,120,75);
        score.draw_center(sb, 185,400);
       // }


        if (showed_ads){
            sb.draw(your_best_score, 125, 190, 210, 75);
            score_b.draw_center(sb,185,160);
        }else {
            sb.draw(your_best_score, 125, 190, 210, 75);
            score_b.draw_center(sb,185,160);

        }
        if (position.x>=-865){
            sb.draw(big_balloon, position.x, position.y, 860, 800);
        }

        //achievement.draw_current_medal(sb,270,200);

       // FontRed1.draw(sb, " Hi Score: "+  load_hiscore, 10, 250);
        if (special_effects.size>=1) {
            for (int j = 0; j <= special_effects.size - 1; j++) {
                if (!special_effects.get(j).isComplete()) {
                    special_effects.get(j).draw(sb);
                } else {
                    special_effects.removeIndex(j);
                }
            }
        }
        sb.end();

    }

    private void draw_progress_bar(SpriteBatch sb,float range) {

        patch.draw(sb, progress_bar_bgnd.getX() + 13, progress_bar_bgnd.getY() + 10,((range*405)/10000)+20 , 50);
    }

    @Override
    public void dispose() {

    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }
}
