package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Sprites.Balloon;
import ru.asupd.poop_ballon.Sprites.Cloud;
import ru.asupd.poop_ballon.Workers.Shaker;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Voland on 04.08.2017.
 */



public class PlayState extends State {

    private Array<Balloon> balloons;
    private Array<Cloud> clouds;

    private Texture background;
    private BitmapFont FontRed1;

    private Texture muted;
    private Texture texture_b_b,texture_b_g,texture_b_y,texture_b_r,texture_b_p,texture_pooped,texture_bloody;
    private Texture texture_poop_balloon;
    private Texture your_high_score,tap_to_play,score;
    private Texture numbers;
    private Array<TextureRegion> frames_numbers;

    private Texture unmuted;

    private int cautch_ball = 0;
    private int miss_ball = 0;
    private Sound poop_Sound;
    private Music background_Music;
    boolean mute,change_background;
    public Preferences prefs;
    public int load_hiscore;
    private static final String APP_STORE_NAME = "Poop_ballons_90471d221cb7702a2b7ab38a5433c26e";
    float volume;
    Shaker shaker;
    int index,x,y;

    boolean started;//для страрта игры
    int[] megred_high_score = new int[5];
    private Vector3 position;
    private Vector3 velosity;



    public PlayState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 480 , 800 );
        background = new Texture("background_clean.png");
        x=10;
        y=450;
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
        this.velosity.y = 300;
        texture_poop_balloon=new Texture("poop_balloon.png");

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный




        shaker = new Shaker(camera);
        started=false;


        //инициализация музыки
        poop_Sound = Gdx.audio.newSound(Gdx.files.internal("poop.mp3"));

        background_Music = Gdx.audio.newMusic(Gdx.files.internal("sound.mp3"));
        background_Music.setVolume(0.1f);
        background_Music.setLooping(true);
        background_Music.play();


        //хорошо бы сделать отдельный текстур менеджер с полосой загрузки
        texture_b_b =  new Texture("Balloon_blue.png");
        texture_b_r =  new Texture("Balloon_red.png");
        texture_b_g =  new Texture("Balloon_green.png");
        texture_b_y =  new Texture("Balloon_yellow.png");
        texture_b_p =  new Texture("Balloon_purple.png");
        texture_pooped=new Texture("blow.png");
        texture_bloody=new Texture("Blood_Splatter.png");
        your_high_score = new Texture("best_score_g.png");
        tap_to_play = new Texture("tap_to_play.png");
        score =  new Texture("score.png");

        numbers = new Texture("numbers.png");
        frames_numbers = new Array<TextureRegion>();
        for (int j=0;j<=9;j++){
            frames_numbers.add(new TextureRegion(numbers,j*25,0,25,31));
        }


/*
        texture_cloud1=new Texture("cloud1.png");
        texture_cloud2=new Texture("cloud2.png");
        texture_cloud3=new Texture("cloud3.png");
        texture_cloud4=new Texture("cloud4.png");
*/
        muted = new Texture("sound_off.png");
        unmuted = new Texture("sound_on.png");

        prefs = Gdx.app.getPreferences(APP_STORE_NAME);
        load_hiscore = prefs.getInteger("highscore");
        prefs.putInteger("last_match_score", 0);

        int local_highscore;

        local_highscore = load_hiscore;
        for (int k=0;k<=4;k++) {

            megred_high_score[k] = local_highscore % 10;
            local_highscore = local_highscore / 10;
        }



        //выключил звук на время тестов
        change_background = false;
        mute=false;
        volume=0.1f;

        //инициализация массива шаров
        balloons = new Array<Balloon>();
        for (int i = 0; i <= 4; i++){
            balloons.add(new Balloon(i * 96,-195-random(50),100));
        }

        //инициализация массива облаков
        clouds = new Array<Cloud>();
        for (int i = 0; i <= 4; i++){
            clouds.add(new Cloud(random(1000)-400,125*i+100+10,-random(25)-25));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            System.out.println("touchPos :"+touchPos);
            camera.unproject(touchPos);
            index=0;
            for (Balloon balloon : balloons) {
                if ((balloon.getPosition().x<touchPos.x)&(balloon.getPosition().x+100>touchPos.x)){
                    if ((balloon.getPosition().y<touchPos.y)&(balloon.getPosition().y+200>touchPos.y)){
                        if (!balloon.isPooped()) {
                            if (touchPos.y<700) {
                                System.out.println("touched the ball :");
                                 poop_Sound.play(volume);
                                //balloon.setPosition(balloon.getPosition().x, -220-random(50));
                                shaker.shake(0.40f);

                                cautch_ball++;
                                // camera.rotate(0.2f);
                                // camera.update();
                                int local_highscore;
                                local_highscore = cautch_ball;
                                for (int k=0;k<=4;k++) {

                                    megred_high_score[k] = local_highscore % 10;
                                    local_highscore = local_highscore / 10;
                                }

                                balloon.setPooped(cautch_ball);
                                balloons.add(new Balloon(random(4) * 96, -195 - random(50), (200 + random(cautch_ball * 3) - random(100))));
                            }
                        }
                    }
                }
                index++;
            }
            if ((480-69<touchPos.x)&(480-69+64>touchPos.x)){
                if((700+20<touchPos.y)&(700+20+64>touchPos.y)){
                    if (mute) {
                        background_Music.play();
                        volume=0.1f;
                        mute=false;
                    }
                    else {
                        background_Music.pause();
                        volume=0.0f;
                        mute=true;
                    }
                }
            }

            if (!started){
                started=true;
                int local_highscore;
                local_highscore = 0;
                for (int k=0;k<=4;k++) {

                    megred_high_score[k] = local_highscore % 10;
                    local_highscore = local_highscore / 10;
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        index=0;
        if ((started)&(position.y<1000)) {
            velosity.scl(dt);
            position.add(0, velosity.y, 0);
            velosity.scl(1 / dt);
        }

        if (started) {
            for (Balloon balloon : balloons) {
                balloon.update(dt);

                if (balloon.getPosition().y > 720) {
                    balloon.setPosition(balloon.getPosition().x, -220 - random(50));
                    balloon.setVelosity(get_speed_for_balloon());
                    miss_ball++;
                    change_background = true;
                    Gdx.input.vibrate(250);
                }
                if (balloon.isLive_out()) {
                    balloon.dispose();
                    balloons.removeIndex(index);
                }
                index++;
            }
        }
        for (Cloud cloud : clouds) {
            cloud.update(dt);
            if (cloud.getPosition().x < -320) {
                cloud.setPosition(+480+200 + random(250), cloud.getPosition().y);
                cloud.change_texture();
            }
        }

        shaker.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        //sb.draw(background, 0, 100,480,900);
        sb.disableBlending();
        sb.draw(background,-25, -25,550,900);
        sb.enableBlending();
        if (started){
            sb.draw(score,100,760,115,31);
            sb.draw(frames_numbers.get(megred_high_score[0]),285,760,25,31);
            sb.draw(frames_numbers.get(megred_high_score[1]),265,760,25,31);
            sb.draw(frames_numbers.get(megred_high_score[2]),245,760,25,31);
            sb.draw(frames_numbers.get(megred_high_score[3]),225,760,25,31);
        }

        if (!started){
            sb.draw(your_high_score,130,100,211,74);
            sb.draw(tap_to_play,80,360,335,51);

            sb.draw(frames_numbers.get(megred_high_score[0]),250,50,25,31);
            sb.draw(frames_numbers.get(megred_high_score[1]),230,50,25,31);
            sb.draw(frames_numbers.get(megred_high_score[2]),210,50,25,31);
            sb.draw(frames_numbers.get(megred_high_score[3]),190,50,25,31);
        }

        for (Cloud cloud : clouds) {

            sb.draw(cloud.getTexture(),cloud.getPosition().x,cloud.getPosition().y,221,100);
        }

        sb.draw(texture_poop_balloon,position.x,position.y,463,218);

        for (Balloon balloon : balloons) {
            switch (balloon.getColor_of_balloon()) {
                case 0:
                   // sb.draw(balloon.getTexture(), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                    sb.draw(texture_b_g, balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                    break;
                case 1:
                    sb.draw(texture_b_y, balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                    break;
                case 2:
                    sb.draw(texture_b_b, balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                    break;
                case 3:
                    sb.draw(texture_b_r, balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                    break;
                case 4:
                    sb.draw(texture_b_p, balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                    break;
                case 5:
                    sb.draw(texture_pooped, balloon.getPosition().x-10, balloon.getPosition().y+100, 115, 115);
                    break;
            }

        }


       // FontRed1.draw(sb, " Hi Score: "+  load_hiscore, 10, 790);
       // FontRed1.draw(sb, " cautch_ball() ballons: "+  cautch_ball, 10, 755);
        FontRed1.draw(sb, " FPS : "+  Gdx.graphics.getFramesPerSecond(), 10, 790);


        if (mute){
            //sb.draw(muted,480-69-69-69-69,800-69,64,64);
            sb.draw(muted,480-69,700+20,64,64);
        }else{
            sb.draw(unmuted,480-69,700+20,64,64);
        }

        switch (miss_ball){
            default:
                gsm.set(new GameoverState(gsm));
                break;
            case 3:
                load_hiscore = prefs.getInteger("highscore");

                prefs.putInteger("last_match_score", cautch_ball);
                prefs.flush();
                if (load_hiscore<cautch_ball) {
                    prefs.putInteger("highscore", cautch_ball);
                    prefs.flush();
                }

                gsm.set(new GameoverState(gsm));

                //prefs.putBoolean("soundOn", true);


                /*
                SharedPreferences settings = context.getSharedPreferences(PERSISTANT_STORAGE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString( "name", "John" );
                editor.commit();*/
                break;
          /*  case 3:
              //  sb.draw(red_cross,480-69-69-69,20,64,64);
                if (change_background) {
                    change_background=false;
                    background.dispose();
                    background = new Texture("background_night.png");
                }
                break;
*/
            case 2:
              //  sb.draw(red_cross,480-69-69,20,64,64);
                if (change_background) {
                    change_background=false;
                    background.dispose();
                    background = new Texture("background_evening.png");
                }
                break;

            case 1:
              //  sb.draw(red_cross,480-69,20,64,64);
                if (change_background) {
                    change_background=false;
                    background.dispose();
                    background = new Texture("background_sunset.png");
                }
                break;

            case 0:
                break;

        }
        sb.end();
    }

    @Override
    public void dispose() {
        poop_Sound.dispose();
        background_Music.dispose();
        background.dispose();
        texture_b_b.dispose();
        texture_b_r.dispose();
        texture_b_g.dispose();
        texture_b_y.dispose();
        texture_b_p.dispose();
        texture_pooped.dispose();
        texture_bloody.dispose();
        muted.dispose();
        unmuted.dispose();


    }

    private int get_speed_for_balloon(){
        int speed = 0;
        speed = 200+random(cautch_ball*2)-random(100);
        if (speed>=550){
            speed=550;//Ограничитель скорости шаров
        }
        return speed;
    }

}
