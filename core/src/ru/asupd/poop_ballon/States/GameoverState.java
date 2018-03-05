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


import static com.badlogic.gdx.math.MathUtils.random;
import static ru.asupd.poop_ballon.MyGdxGame.actionresolver_my;
import static ru.asupd.poop_ballon.MyGdxGame.adsController_my;
import static ru.asupd.poop_ballon.MyGdxGame.playServices_my;
import static ru.asupd.poop_ballon.MyGdxGame.showed_ads;
import static ru.asupd.poop_ballon.States.MenuState.sound_effects;
import static ru.asupd.poop_ballon.States.PlayState.getCurrent_difficult_up;
import static ru.asupd.poop_ballon.States.MenuState.settings;
import static ru.asupd.poop_ballon.States.PlayState.upCurrent_difficult_up;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.ANIMATION_TIME_TAP_TO_PLAY;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.ANIMATIO_TIME_TO_PLAY_SIZE;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.APP_STORE_NAME;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.NEEDED_SCORE;

/**
 * Created by Voland on 22.08.2017.
 */

public class GameoverState extends State {
    //TextureRegion background;
    Texture background;
    Texture your_score,your_best_score,awesome;

    Sprite tap_to_restart;
    private float current_tap_to_restart;

    Texture numbers;
    //что за счет
    Score score= new Score(1.0f);
    //и это
    Score score_b= new Score(1.0f);

    Score score_medal=new Score(1.0f);
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
    private boolean ads=true;
    boolean start_count_trigger=true;
    boolean start_gain_medal_trigger=true;

    Vector3 velosity,position;
    Texture big_balloon;
    private boolean can_restart;
    private boolean submit=true;
    boolean next_game=false;
    boolean start=true;
    private boolean add_hicsore=true;
    public static final Achievement achievement= new Achievement();
    PlayState playState;
    public static Array<ParticleEffect> special_effects = new Array<ParticleEffect>();

    Sprite progress_bar_bgnd= new Sprite(new Texture(Gdx.files.internal("progr_bar.png")));
    Sprite progress_bar_bgnd_one= new Sprite(new Texture(Gdx.files.internal("progr_bar_part.png")));
    Sprite stick=new Sprite(new Texture("palka.png"));

    NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("scores.9.png")),15,14,14,15);

    public GameoverState(GameStateManager gsm, float redball_x, Array<Star> stars) {
        super(gsm);
        can_restart=false;
        dark_medal = new Sprite(new Texture(Gdx.files.internal("pse-medal.png")));
        dark_medal.setPosition(420,95);
        //redball_x=-190;
        //System.out.println("redball_x: "+redball_x);
        camera.setToOrtho(false, 480 , 800 );
        //this.final_stars=stars;
        current_tap_to_restart=0;
        if (showed_ads){
            progress_bar_bgnd.setPosition(5,80);
            progress_bar_bgnd_one.setPosition(5,80);
        }else {
            progress_bar_bgnd_one.setPosition(5,10);
            progress_bar_bgnd.setPosition(5, 10);
        }
        stick.setPosition(210,progress_bar_bgnd.getY()+43);


        background = new Texture("background_gameover.png");
        //background = new TextureRegion((Texture)(Assets.instance.manager.get(Assets.back_ground_atlas)));
        //background = new TextureRegion(background,4*background.getRegionWidth()/4,0,background.getRegionWidth()/4,background.getRegionHeight());

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
        //score_medal.update_render_time();

        if (getCurrent_difficult_up()<=8) {
            score_need.setScore(NEEDED_SCORE[getCurrent_difficult_up()]);
        }


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



        submit=true;


    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            //System.out.println("score.getScore(true)"+score.getScore(true));
            //System.out.println("score.getScore()"+score.getScore());
            if (score.getScore(true)<score.getScore())
            {
                //score.end_count();
                //System.out.println("S4et");
            }
            else if (score_medal.getScore(true)<score_medal.getScore())
            {

               // score_medal.end_count();
            }
            else
            {
                if (special_effects.size<=0) {
                    if (!next_game) {

                        sound_effects.snd_big_baloon();
                        next_game=true;
                        position.x=480;
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                PlayState.set_mus();
                                playState=new PlayState(gsm);
                                PlayState.set_mus();
                            }
                        });
                    }
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
        if(!can_restart){
            if (score.getScore(true)<score.getScore())
            {

            }
            else if (score_medal.getScore(true)<score_medal.getScore())
            {

            }
            else
            {
                can_restart=true;
            }
        }

        if (start&currentdt>=0.25f){
            //

            //if (score.getScore()>score.getScore(true)) {
                start = false;
                sound_effects.snd_scores(score.getScore());
            //}
        }

        if (next_game){
            if (position.x>=-190) {
                velosity.scl(dt);
                position.add(velosity.x, 0, 0);
                velosity.scl(1 / dt);
            }else{
                next_game=false;
                playState.setPosition_red(position);
                if (settings.isMus()){
                    PlayState.set_unmus();
                }
                gsm.set(playState);
            }
        }
        if (PlayState.isPause())
        {
            PlayState.setUNPAUSE();
        }
            score.update(dt);
            score_b.update(dt);
            achievement.update(dt);


            if (start_count_trigger) {
                    if (score.getScore(true) >= score.getScore()) {
                        start_count_trigger = false;

                        sound_effects.snd_scores_stop();
                        start=true;
                        currentdt=0.0f;
                        //sound_effects.snd_scores(score.getScore());

                        score_medal.addScore(score.getScore());
                        score_medal.update_render_time();
                }
            } else {
                score_medal.update(dt);
                if (score_medal.getBuffer()<=3){
                    sound_effects.snd_scores_stop();
                }
            }
        if (getCurrent_difficult_up()<=8) {
        if (start_gain_medal_trigger) {
            if (score_medal.getScore(true) >= score_need.getScore()) {
                sound_effects.snd_progressl();
                upCurrent_difficult_up();
                if (getCurrent_difficult_up() <= 8) {
                    score_need.setScore(NEEDED_SCORE[getCurrent_difficult_up()]);
                }

                special_effects.add(new ParticleEffect(Assets.beam));
                special_effects.get(special_effects.size - 1).setPosition(240, 400);
                special_effects.get(special_effects.size - 1).start();

                switch (getCurrent_difficult_up()) {
                    default:
                        sound_effects.snd_take_medal();
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

        }
            for(ParticleEffect special_effect :special_effects){
                special_effect.update(dt);
            }
        }

        if (currentdt>=waiting) {
            handleInput();
            if (actionresolver_my!=null)
            {
                if (submit) {
                    submit=false;
                    System.out.println("Submit score" + score_b.getScore());
                    playServices_my.submitScore(score_b.getScore());
                    playServices_my.submitScore_ALLScore(score_medal.getScore()+last_score);
                }
                //actionresolver_my.submitScoreGPGS(score_b.getScore());
            }

            int chance=random(10);

            if (ads&chance<3){
                System.out.println("started adsController: "+chance);
                if (showed_ads){

                    ads=false;
                    //оказать рекламу
                    adsController_my.showBannerAd_full();
                }
            }else if (ads){
                System.out.println("started adsController: "+chance);
                ads=false;
            }
        }
        currentdt+=dt;
        if (!next_game) {
            if (position.x >= -865) {
                velosity.scl(dt);
                position.add(velosity.x, 0, 0);
                velosity.scl(1 / dt);
            }
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
                prefs.putInteger("highscore", (int) score.getScore());
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
        //for (Star star : final_stars ){
        //    sb.draw(star.getTexture(),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
            // sb.draw(Assets.instance.manager.get(Assets.star1),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
        //}

        if(can_restart) {
            tap_to_restart.draw(sb);
        }

        //


        long stam_p=score_need.getScore();
        long stap_2=score_medal.getScore(true);
        stap_2*=10000;
        //System.out.println(stam_p+" "+stap_2);


        if (getCurrent_difficult_up()==0){
            progress_bar_bgnd_one.draw(sb);

        }else
        {
            progress_bar_bgnd.draw(sb);
        }


        if (getCurrent_difficult_up()<=8) {

            draw_progress_bar(sb,stap_2/stam_p);
            //dark_medal.draw(sb);
            score_medal.draw_center(sb, (int) progress_bar_bgnd.getX() + 80, (int) progress_bar_bgnd.getY() + 45);
            stick.draw(sb);
            score_need.draw_center(sb, (int) progress_bar_bgnd.getX() + 260, (int) progress_bar_bgnd.getY() + 45);
        }else
        {

            draw_progress_bar(sb,10000);
            score_medal.draw_center(sb, (int) progress_bar_bgnd.getX() + 170, (int) progress_bar_bgnd.getY() + 45);
        }
        //System.out.println("asd: "+score_need.getScore());
        // if(
        if (getCurrent_difficult_up()<=8) {
            achievement.draw_current_medal(sb);
        }
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
            sb.draw(your_best_score, 125, 220, 210, 75);
            score_b.draw_center(sb,185,190);
        }else {
            sb.draw(your_best_score, 125, 190, 210, 75);
            score_b.draw_center(sb,185,160);

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
        if (position.x>=-800){
            sb.draw(big_balloon, position.x, position.y, 860, 800);
        }
        sb.end();

    }

    private void draw_progress_bar(SpriteBatch sb,float range) {

        //range=10000;
        patch.draw(sb, progress_bar_bgnd.getX() + 43, progress_bar_bgnd.getY() + 46,((range*360)/10000)+30 , 30);
    }

    @Override
    public void dispose() {

    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }
}
