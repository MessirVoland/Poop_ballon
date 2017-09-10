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

import org.omg.CORBA.BooleanSeqHelper;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Sprites.Animation;
import ru.asupd.poop_ballon.Sprites.Balloon;
import ru.asupd.poop_ballon.Sprites.Boss_balloon;
import ru.asupd.poop_ballon.Sprites.Cloud;
import ru.asupd.poop_ballon.Workers.Shaker;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Voland on 04.08.2017.
 */



public class PlayState extends State {

    private Array<Balloon> balloons;//массив шаров
    private Array<Cloud> clouds;//массив шаров

    private Texture background;//задник
    private BitmapFont FontRed1;//для фпс

    private Texture muted,unmuted;//иконка звука

    private Texture texture_b_b,texture_b_g,texture_b_y,texture_b_r,texture_b_p;//Шарики

    private Texture texture_poop_balloon;//Название игры

    private Texture your_high_score,tap_to_play,score;//наибольший счет, таб ту плей, напись счет

    private Texture numbers;//числа
    private Array<TextureRegion> frames_numbers;//числа



    private Texture poof_balloon_atlas;//загрузчик атласов взрывов
    private Animation poof_balloon_g,poof_balloon_y,poof_balloon_b,poof_balloon_r,poof_balloon_p;

    private int cautch_ball = 0;//поймано шаров
    public int miss_ball = 0;//пропущено шаров

    private Sound poop_Sound;//звук лопания
    private Music background_Music,boss_Music;//музыка

    boolean mute;//тишина
    boolean change_background;//хз

    public final static float ANIMATION_TIME=0.266f;//время анимации
    public Preferences prefs;//для храниния данных
    public int load_hiscore;//макс счет
    private static final String APP_STORE_NAME = "Poop_ballons_90471d221cb7702a2b7ab38a5433c26e";
    float volume;//хз)
    Shaker shaker;//шейкео
    int index,x,y;//хз

    boolean started;//для страрта игры
    int[] megred_high_score = new int[5];//для отображения счета

    private Vector3 position;//Координаты заголовка игры
    private Vector3 velosity;//вектор движения заголовка

    Boss_balloon boss_balloon;//босс



    public PlayState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 480 , 800 );


        background = new Texture("background_clean.png");

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный


        shaker = new Shaker(camera);
        started=false;//начало игры

        position = new Vector3(10,500,0);
        velosity=new Vector3(0,200,0);


        //инициализация музыки
        poop_Sound = Gdx.audio.newSound(Gdx.files.internal("poop.mp3"));

        background_Music = Gdx.audio.newMusic(Gdx.files.internal("sound.mp3"));
        boss_Music = Gdx.audio.newMusic(Gdx.files.internal("Sound_19272 [Wav_Library_Net].mp3"));
        background_Music.setVolume(0.3f);
        background_Music.setLooping(true);
        boss_Music.setLooping(true);
        background_Music.play();
        boss_Music.setVolume(0.3f);


        //хорошо бы сделать отдельный текстур менеджер с полосой загрузки
        texture_b_b =  new Texture("Balloon_blue.png");
        texture_b_r =  new Texture("Balloon_red.png");
        texture_b_g =  new Texture("Balloon_green.png");
        texture_b_y =  new Texture("Balloon_yellow.png");
        texture_b_p =  new Texture("Balloon_purple.png");
        your_high_score = new Texture("best_score_g.png");
        texture_poop_balloon = new Texture("poop_balloon.png");
        tap_to_play = new Texture("tap_to_play.png");
        score =  new Texture("score.png");

        numbers = new Texture("numbers.png");
        frames_numbers = new Array<TextureRegion>();
        for (int j=0;j<=9;j++){
            frames_numbers.add(new TextureRegion(numbers,j*25,0,25,31));
        }

        poof_balloon_atlas = new Texture("pop_g.png");
        poof_balloon_g = new Animation(new TextureRegion(poof_balloon_atlas),3,ANIMATION_TIME);
        poof_balloon_atlas = new Texture("pop_y.png");
        poof_balloon_y = new Animation(new TextureRegion(poof_balloon_atlas),3,ANIMATION_TIME);
        poof_balloon_atlas = new Texture("pop_b.png");
        poof_balloon_b = new Animation(new TextureRegion(poof_balloon_atlas),3,ANIMATION_TIME);
        poof_balloon_atlas = new Texture("pop_r.png");
        poof_balloon_r = new Animation(new TextureRegion(poof_balloon_atlas),3,ANIMATION_TIME);
        poof_balloon_atlas = new Texture("pop_p.png");
        poof_balloon_p = new Animation(new TextureRegion(poof_balloon_atlas),3,ANIMATION_TIME);


        muted = new Texture("sound_off.png");
        unmuted = new Texture("sound_on.png");

        prefs = Gdx.app.getPreferences(APP_STORE_NAME);
        load_hiscore = prefs.getInteger("highscore");
        prefs.putInteger("last_match_score", 0);

        //Вывод макс очков
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
            balloons.add(new Balloon(i * 96,-195-random(50),100,true));
        }

        //инициализация массива облаков
        clouds = new Array<Cloud>();
        for (int i = 0; i <= 4; i++){
            clouds.add(new Cloud(random(1000)-400,125*i+100+10,-random(25)-25));
        }
        //инициализация босса
        boss_balloon = new Boss_balloon(random(4)*96,-195-random(50),80);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            //System.out.println("touchPos :"+touchPos);
            camera.unproject(touchPos);
            index=0;
            //клик по боссу
            if (boss_balloon.isStarted()){
                if ((boss_balloon.getPosition().x<touchPos.x)&(boss_balloon.getPosition().x+100>touchPos.x)){
                    if ((boss_balloon.getPosition().y<touchPos.y)&(boss_balloon.getPosition().y+200>touchPos.y)){
                        poop_Sound.play(volume);
                        shaker.shake(0.40f);
                        boss_balloon.clicked_boss();
                    }
                }
            }

            //клик по шарам
            for (Balloon balloon : balloons) {
                if ((balloon.getPosition().x<touchPos.x)&(balloon.getPosition().x+100>touchPos.x)){
                    if ((balloon.getPosition().y<touchPos.y)&(balloon.getPosition().y+200>touchPos.y)){
                        if (!balloon.isPooped()) {
                            if (touchPos.y<700) {
                                //System.out.println("touched the ball :");
                                 poop_Sound.play(volume);
                                shaker.shake(0.40f);

                                cautch_ball++;

                                poof_balloon_g.setCurrentFrameTime(0.0f);
                                poof_balloon_g.setFrame(0);
                                poof_balloon_y.setCurrentFrameTime(0.0f);
                                poof_balloon_y.setFrame(0);
                                poof_balloon_b.setCurrentFrameTime(0.0f);
                                poof_balloon_b.setFrame(0);
                                poof_balloon_r.setCurrentFrameTime(0.0f);
                                poof_balloon_r.setFrame(0);
                                poof_balloon_p.setCurrentFrameTime(0.0f);
                                poof_balloon_p.setFrame(0);

                                int local_highscore;
                                local_highscore = cautch_ball;
                                for (int k=0;k<=4;k++) {

                                    megred_high_score[k] = local_highscore % 10;
                                    local_highscore = local_highscore / 10;
                                }

                                balloon.setPooped(cautch_ball);
                                balloons.add(new Balloon(random(4) * 96, -195 - random(50), (200 + random(cautch_ball * 3) - random(100)),!boss_balloon.isStarted()));
                            }
                        }
                    }
                }
                index++;
            }

            //клик по иконке звука
            if ((480-69<touchPos.x)&(480-69+64>touchPos.x)){
                if((700+20<touchPos.y)&(700+20+64>touchPos.y)){
                    if (mute) {
                        if (boss_balloon.isStarted()){
                            boss_Music.play();
                        }else {
                            background_Music.play();
                        }
                        volume=0.1f;
                        mute=false;
                    }
                    else {
                        if (boss_balloon.isStarted()){
                            boss_Music.pause();
                        }else {
                            background_Music.pause();
                        }
                        volume=0.0f;
                        mute=true;
                    }
                }
            }

            if (!started){
                if (!boss_balloon.isStarted()) {
                    started = true;

                int local_highscore;
                local_highscore = 0;
                for (int k=0;k<=4;k++) {

                    megred_high_score[k] = local_highscore % 10;
                    local_highscore = local_highscore / 10;
                }
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        poof_balloon_g.update(dt);
        poof_balloon_y.update(dt);
        poof_balloon_b.update(dt);
        poof_balloon_r.update(dt);
        poof_balloon_p.update(dt);

        if (boss_balloon.isStarted()){
            boss_balloon.update(dt);
        }

        //Старт босса
        if ((cautch_ball>25)&(!boss_balloon.isStarted())){
            if (boss_balloon.isLive()) {
                boss_balloon.Start();
                //started = false;
                if (!mute) {
                    background_Music.stop();
                    boss_Music.play();
                }
                for (Balloon balloon : balloons) {
                    balloon.stop_spawn();
                }
            }

        }
        //Активные действия и проверки босса
        if ((boss_balloon.isMissed())&(boss_balloon.isStarted())){
            miss_ball++;
            boss_balloon.setMissed(false);
            change_background = true;
            Gdx.input.vibrate(125);
        }
        //смерть босса
        if ((!boss_balloon.isLive())&(!boss_balloon.isDead())){
            boss_balloon.make_dead();
            for (Balloon balloon : balloons) {
                balloon.start_spawn();
            }
        }


        index=0;

        if ((started)&(position.y<1000)) {
            velosity.scl(dt);
            position.add(0, velosity.y, 0);
            velosity.scl(1 / dt);
        }
        if (!boss_balloon.isLive()){
            if (boss_Music.isPlaying()){
                if (!mute) {
                    boss_Music.stop();
                    background_Music.play();
                }
            }
        }


        if (started) {
            for (Balloon balloon : balloons) {
                balloon.update(dt);

                if (balloon.getPosition().y > 720) {
                    balloon.setPosition(balloon.getPosition().x, -220 - random(50));
                    balloon.setVelosity(get_speed_for_balloon());
                    miss_ball++;
                    change_background = true;
                    Gdx.input.vibrate(125);
                }

                if (balloon.isLive_out()) {
                    balloon.dispose();
                    System.out.println("balloon disposed :"+cautch_ball);
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
        sb.disableBlending();
        sb.draw(background,-25, -25,550,900);
        sb.enableBlending();

        if ((started)|(boss_balloon.isStarted())){
            sb.draw(score,100,760,115,31);
            sb.draw(frames_numbers.get(megred_high_score[0]),285,760,25,31);
            sb.draw(frames_numbers.get(megred_high_score[1]),265,760,25,31);
            sb.draw(frames_numbers.get(megred_high_score[2]),245,760,25,31);
            sb.draw(frames_numbers.get(megred_high_score[3]),225,760,25,31);
        }

        if ((!started)&(!boss_balloon.isStarted())){
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

        sb.draw(texture_poop_balloon,position.x,position.y,463,218);//заголовок
        if (boss_balloon.isStarted()){
            sb.draw(boss_balloon.getTexture_boss(),boss_balloon.getPosition().x,boss_balloon.getPosition().y,95,190);
        }

        //if (!boss_balloon.isStarted()){
        for (Balloon balloon : balloons) {
            switch (balloon.getColor_of_balloon()) {
                case 0:
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
                    sb.draw(poof_balloon_g.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                    break;
                case 6:
                    sb.draw(poof_balloon_y.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                    break;
                case 7:
                    sb.draw(poof_balloon_b.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                    break;
                case 8:
                    sb.draw(poof_balloon_r.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                    break;
                case 9:
                    sb.draw(poof_balloon_p.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                    break;
            }

       // }
        }

        FontRed1.draw(sb, " FPS : "+  Gdx.graphics.getFramesPerSecond(), 10, 790);


        if (mute){
            sb.draw(muted,480-69,700+20,64,64);
        }else{
            sb.draw(unmuted,480-69,700+20,64,64);
        }
        System.out.println("Missed balls: "+miss_ball);
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

                break;
            case 2:
                if (change_background) {
                    change_background=false;
                    background = new Texture("background_evening.png");
                }
                break;

            case 1:
                if (change_background) {
                    change_background = false;
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
        poof_balloon_atlas.dispose();
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
    public void missed_ball(){
        miss_ball--;
    }

}
