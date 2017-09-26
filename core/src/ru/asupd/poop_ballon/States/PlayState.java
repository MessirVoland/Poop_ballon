package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Sprites.Animation;
import ru.asupd.poop_ballon.Sprites.Balloon;
import ru.asupd.poop_ballon.Sprites.Boss_balloon;
import ru.asupd.poop_ballon.Sprites.Cloud;
import ru.asupd.poop_ballon.Workers.Resizer;
import ru.asupd.poop_ballon.Workers.Score;
import ru.asupd.poop_ballon.Workers.Shaker;

import static com.badlogic.gdx.math.MathUtils.random;

/**Игровой модуль
 * Created by Voland on 04.08.2017.
 */



public class PlayState extends State {

    private Array<Balloon> balloons;//массив шаров
    private Array<Cloud> clouds;//массив шаров

    private Texture background,pause_bgnd;//задник
    private TextureRegion back_ground_atlas;
    private Array<TextureRegion> background_frames;
    private boolean change_background;//хз
    private float currnent_dt_background=0,current_alpha_background=1.0f;

    private BitmapFont FontRed1;//для фпс

    private Texture muted,unmuted;//иконка звука
    private Texture vibrated,unvibrated;//иконка звука

    private Texture texture_b_b,texture_b_g,texture_b_y,texture_b_r,texture_b_p,texture_b_o;//Шарики

    private Texture texture_poop_balloon;//Название игры
    private Resizer resizer_poop_balloon;

    private Texture your_high_score,tap_to_play,score;//наибольший счет, таб ту плей, напись счет

    private Texture multi_x2,multi_x3,multi_x4,multi_x5,multi_x6;
	
	private Texture options;//кнопка опции

    //private Texture numbers;//числа
    //private Array<TextureRegion> frames_numbers;//числа
    private Score score_num;



    private Texture poof_balloon_atlas;//загрузчик атласов взрывов
    private Animation poof_balloon_g,poof_balloon_y,poof_balloon_b,poof_balloon_r,poof_balloon_p,poof_balloon_o;

    private int cautch_ball = 0;//поймано шаров
    private int miss_ball = 0;//пропущено шаров

    private Sound poop_Sound;//звук лопания
    private static final float minX = 0.75f;//Диапазон изменения хлопка
    private static final float maxX = 1.7f;

    //private int max_combo=0;

    private Music background_Music,boss_Music;//музыка

    public final static float ANIMATION_TIME=0.266f;//время анимации
    //public final static float ANIMATION_TIME=3.0f;
    private Preferences prefs;//для храниния данных
    private int load_hiscore;//макс счет
    private boolean mute;//тишина
    private boolean vibro=true;//вибратор
    private static final String APP_STORE_NAME = "Poop_ballons_90471d221cb7702a2b7ab38a5433c26e";
    private float volume;//звук хлопков)

    //гейм овер
    private boolean game_over_start=false;//перероход в гейм овер
    private boolean game_over_well_play=false;
    private boolean game_over_ball_fly=false;
    private float game_over_dt=0;
    private final static float GAME_OVER_ANIM=0.266f;
    private Texture well_played;
    private Texture nice_played;
    private Texture big_balloon;
    private float x_game_over=-480,y_game_over=0;

    private Shaker shaker;//шейкео
    private int index;//хз
    private boolean pause=false;
    private float min_x=0.0f,min_y=0.0f;

    boolean started;//для страрта игры
    private GameoverState gameoverState;
    //int[] megred_high_score = new int[5];//для отображения счета

    private Vector3 position;//Координаты заголовка игры
    private Vector3 velosity;//вектор движения заголовка

    private Boss_balloon boss_balloon;//босс

    private int chance_of_boss=20;//20%
    private int chance_100_150=random(50)+100;

    private int STEP_for_balloon=50;
    public static final int MAX_STEP=401;
    int current_step=1;
    //частицы
    private ParticleEffect effect;
    private ParticleEffect effect_pop;

    //Комбо
    private int current_combo=0;

    private Vector3 touchPos;//вектор прикосновния



    protected PlayState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 480 , 800 );

        //background = new Texture("background_clean.png");
        background = new Texture("bacgound_atlas.png");
        background_frames = new Array<TextureRegion>();
        back_ground_atlas = new TextureRegion(background);
        int frameWidth=back_ground_atlas.getRegionWidth()/4;
        for (int i=0;i<4;i++){
            background_frames.add(new TextureRegion(back_ground_atlas,i*frameWidth,0,frameWidth,back_ground_atlas.getRegionHeight()));
        }

        pause_bgnd = new Texture("pause.png");

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный


        shaker = new Shaker(camera);
        started=false;//начало игры

        position = new Vector3(10,500,0);
        velosity=new Vector3(0,200,0);


        //инициализация музыки
        poop_Sound = Gdx.audio.newSound(Gdx.files.internal("poop.mp3"));
        boss_Music = Gdx.audio.newMusic(Gdx.files.internal("Sound_19272 [Wav_Library_Net].mp3"));
        background_Music = Gdx.audio.newMusic(Gdx.files.internal("sound.mp3"));
        background_Music.setVolume(0.3f);
        background_Music.setLooping(true);

        boss_Music.setVolume(0.3f);
        boss_Music.setLooping(true);


        //хорошо бы сделать отдельный текстур менеджер с полосой загрузки
        texture_b_b =  new Texture("Balloon_blue.png");
        texture_b_r =  new Texture("Balloon_red.png");
        texture_b_g =  new Texture("Balloon_green.png");
        texture_b_y =  new Texture("Balloon_yellow.png");
        texture_b_p =  new Texture("Balloon_purple.png");
        texture_b_o =  new Texture("round_b_o.png");
        your_high_score = new Texture("best_score_g.png");
        texture_poop_balloon = new Texture("poop_balloon.png");
        resizer_poop_balloon = new Resizer(texture_poop_balloon.getWidth(),texture_poop_balloon.getHeight());
        resizer_poop_balloon.start();
        tap_to_play = new Texture("tap_to_play.png");
        score =  new Texture("score.png");

        multi_x2 = new Texture("x2.png");
        multi_x3 = new Texture("x3.png");
        multi_x4 = new Texture("x4.png");
        multi_x5 = new Texture("x5.png");
        multi_x6 = new Texture("x6.png");

        well_played = new Texture("wp.png");
        nice_played = new Texture("nice_try.png");
        big_balloon= new Texture("big_balloon.png");

        score_num = new Score();

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
        poof_balloon_atlas = new Texture("pop_color.png");
        poof_balloon_o = new Animation(new TextureRegion(poof_balloon_atlas),3,ANIMATION_TIME);

		options = new Texture("options.png");
        muted = new Texture("sound_off.png");
        unmuted = new Texture("sound_on.png");
        vibrated = new Texture("vibro_on.png");
        unvibrated = new Texture("vibro_off.png");

        prefs = Gdx.app.getPreferences(APP_STORE_NAME);
        load_hiscore = prefs.getInteger("highscore");
        boolean first_start = true;
        if (first_start !=prefs.getBoolean("first_start")){
            prefs.putBoolean("first_start",true);
            mute=false;
            prefs.putBoolean("mute",mute);
            prefs.flush();
        }else
        {   volume=0.0f;
            mute = prefs.getBoolean("mute");
        }
        if (!mute)
        {
            volume=0.1f;
            background_Music.play();
        }

        System.out.println("mute:"+mute);
        prefs.putInteger("last_match_score", 0);

        //Вывод макс очков
        //int local_highscore;
        //local_highscore = load_hiscore;
        score_num.setScore(load_hiscore);

        //выключил звук на время тестов
        change_background = false;
        //mute=false;
        //volume=0.1f;

        //инициализация массива шаров
        balloons = new Array<Balloon>();
        for (int i = 0; i <= 3; i++){
            balloons.add(new Balloon(i * 96,-195-random(50),get_speed_for_balloon(),true));
        }

        //инициализация массива облаков
        clouds = new Array<Cloud>();
        for (int i = 0; i <= 4; i++){
            //clouds.add(new Cloud(random(1000)-400,125*i+100+10,-random(25)-25));
            clouds.add(new Cloud(random(680)-222,125*i+100+10,-random(25)-25));
        }
        //инициализация босса
        boss_balloon = new Boss_balloon(random(4)*96,-195-random(50),80);
        if (random(100)>=chance_of_boss){
            boss_balloon.setLive(false);
        }
        //частицы
        effect = new ParticleEffect();
        effect.loadEmitters(Gdx.files.internal("particles/red_balls.p"));
        effect.loadEmitterImages(Gdx.files.internal("particles"));
        effect.start();

        effect_pop = new ParticleEffect();
        effect_pop.loadEmitters(Gdx.files.internal("particles/pop_b"));
        effect_pop.loadEmitterImages(Gdx.files.internal("particles"));
        //effect_pop.start();

    }
// Input

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            //System.out.println("touchPos :"+touchPos);
            camera.unproject(touchPos);
            index = 0;

            //клик по боссу

            if (!pause){
                if (touchPos!=null) {
                    min_x = touchPos.x;
                    min_y = touchPos.y;
                }

            }
            if (!pause) {
                if (boss_balloon.isStarted()) {
                    if ((boss_balloon.getPosition().x < touchPos.x) & (boss_balloon.getPosition().x + 100 > touchPos.x)) {
                        if ((boss_balloon.getPosition().y < touchPos.y) & (boss_balloon.getPosition().y + 200 > touchPos.y)) {
                            poop_Sound.play(volume);
                            shaker.shake(0.40f);
                            boss_balloon.clicked_boss();
                        }
                    }
                }

                //max_combo=0;
                current_combo = 0;
                //Клик по шарам для проверки комбо, да долго проверять клик по шарам дважды но ничего лучше не придумал
                for (Balloon balloon : balloons) {
                    if ((balloon.getPosition().x < touchPos.x) & (balloon.getPosition().x + 100 > touchPos.x)) {
                        if ((balloon.getPosition().y < touchPos.y) & (balloon.getPosition().y + 200 > touchPos.y)) {
                            if (!balloon.isPooped()) {
                                current_combo++;
                                current_step++;
                                balloon.setCombo(current_combo);
                                // System.out.println("Current combo on click: "+current_combo);
                            }
                        }
                    }
                }
                //System.out.println("Current combo: "+current_combo);
                //current_combo=0;
                //клик по шарам

                for (Balloon balloon : balloons) {
                    if ((balloon.getPosition().x < touchPos.x) & (balloon.getPosition().x + 100 > touchPos.x)) {
                        if ((balloon.getPosition().y < touchPos.y) & (balloon.getPosition().y + 200 > touchPos.y)) {
                            if (!balloon.isPooped()) {
                                if (touchPos.y < 750) {
                                    //System.out.println("touched the ball :");

                                    long id = poop_Sound.play(volume);
                                    Random rand = new Random();
                                    float finalX = rand.nextFloat() * (maxX - minX) + minX;
                                    poop_Sound.setPitch(id, finalX);
                                    poop_Sound.setVolume(id, volume);

                                    shaker.shake(0.276f); // 0.2f
                                    cautch_ball++;
                                    score_num.addScore(1);
                                    //current_combo++;
                                    if (current_combo >= 2) {
                                        //balloon.setCombo(current_combo);
                                        score_num.addScore(current_combo * current_combo - current_combo);
                                        score_num.setCombo(current_combo);
                                        //balloon.setAnimation(poof_balloon_o);
                                        balloon.setMax_combo(current_combo);
                                        //shaker.shake(0.20f*current_combo);
                                    } else {
                                        balloon.setCombo(0);
                                    }

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
                                    poof_balloon_o.setCurrentFrameTime(0.0f);
                                    poof_balloon_o.setFrame(0);

                                    //int local_highscore;
                                    //local_highscore = cautch_ball;
                                    //score_num.setScore(cautch_ball);
                                    //for (int k=0;k<=4;k++) {
                                    //     megred_high_score[k] = local_highscore % 10;
                                    //    local_highscore = local_highscore / 10;
                                    //}

                                    balloon.setPooped();

                                    if ((current_step >= STEP_for_balloon) & (cautch_ball <= 500)) {
                                        current_step = 0;
                                        // System.out.println("New balloon generated");
                                        STEP_for_balloon += 20;
                                        balloons.add(new Balloon(random(4) * 96, -195 - random(50), get_speed_for_balloon(), !boss_balloon.isStarted()));

                                    }
                                    balloons.add(new Balloon(random(4) * 96, -195 - random(50), get_speed_for_balloon(), !boss_balloon.isStarted()));
                                }
                            }
                        }
                    }
                    index++;
                }


                //клик по иконке звука
            /*if ((480-69<touchPos.x)&(480-69+64>touchPos.x)){
                if((700+20<touchPos.y)&(700+20+64>touchPos.y)){
                    if (mute) {
                        if (boss_balloon.isStarted()){
                            boss_Music.play();
                        }else {
                            background_Music.play();
                        }
                        volume=0.1f;
                        mute=false;
                        prefs.putBoolean("mute",mute);
                        prefs.flush();
                    }
                    else {
                        if (boss_balloon.isStarted()){
                            boss_Music.pause();
                        }else {
                            background_Music.pause();
                        }
                        volume=0.0f;
                        mute=true;
                        prefs.putBoolean("mute",mute);
                        prefs.flush();
                    }
                }
            }*/

            }
            //клик по опциям второй
            if ((480 - 69 < touchPos.x) & (480 - 69 + 64 > touchPos.x)) {
                if ((700 + 20 < touchPos.y) & (700 + 20 + 64 > touchPos.y)) {
                   pause=!pause;
                   /* if (pause) {
                        pause = false;
                    } else {
                        pause = true;
                    }*/
                }
            }
                else
            {
                if ((!started)&(!pause)) {
                    if (!boss_balloon.isStarted()) {
                        started = true;
                        score_num.setScore(0);

                        //int local_highscore;
                        //local_highscore = 0;
                        //for (int k=0;k<=4;k++) {

                        //  megred_high_score[k] = local_highscore % 10;
                        // local_highscore = local_highscore / 10;
                        //}
                    }
                }
            }
            if (pause) {
                //звук
                if ((270 < touchPos.x) & (270+150 > touchPos.x)) {
                    if ((400 - 78 < touchPos.y) & (400 - 78 + 156 > touchPos.y)) {
                        if (mute){
                            mute=false;
                            if (boss_balloon.isStarted()){
                                boss_Music.play();
                            }else {
                                background_Music.play();
                            }
                            volume=0.1f;
                            prefs.putBoolean("mute",mute);
                            prefs.flush();
                        }
                        else
                        {
                            if (boss_balloon.isStarted()){
                                boss_Music.pause();
                            }else {
                                background_Music.pause();
                            }
                            volume=0.0f;
                            mute=true;
                            prefs.putBoolean("mute",mute);
                            prefs.flush();
                        }

                    }
                }
                //вибро
                if ((60 < touchPos.x) & (60 +150 > touchPos.x)) {
                    if ((400 - 78 < touchPos.y) & (400 - 78 + 156 > touchPos.y)) {
                        if (vibro){
                            vibro=false;

                            //prefs.putBoolean("mute",mute);
                            //prefs.flush();
                        }
                        else
                        {
                            vibro=true;
                            //prefs.putBoolean("mute",mute);
                            //prefs.flush();
                        }

                    }
                }
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        resizer_poop_balloon.update(dt);
        currnent_dt_background=dt*8; //2;
        if (game_over_start){
            game_over_dt+=dt;
            if (game_over_dt>=0.7f){
                if (game_over_ball_fly){
                    if (position.x<=-190) {
                        gameoverState.setPosition(position);
                         gsm.set(gameoverState);
                    }
                }
                if ((!game_over_ball_fly)&(game_over_well_play)){
                    game_over_ball_fly=true;
                    game_over_dt=0;


                }

                if (!game_over_well_play){
                    game_over_well_play=true;
                    game_over_dt=0;
                    velosity.x=-1200;
                    velosity.y=0;
                    position.x=480;
                    position.y=0;
                    gameoverState = new GameoverState(gsm,-190);
                }
            }
        }

        if (!pause) {
            //effect.update(dt);
            effect_pop.update(dt);
            poof_balloon_g.update(dt);
            poof_balloon_y.update(dt);
            poof_balloon_b.update(dt);
            poof_balloon_r.update(dt);
            poof_balloon_p.update(dt);
            //poof_balloon_o.update(dt);


            if (boss_balloon.isStarted()) {
                boss_balloon.update(dt);
            }

            //Старт босса
            if ((cautch_ball > chance_100_150) & (!boss_balloon.isStarted())) {
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
            if ((boss_balloon.isMissed()) & (boss_balloon.isStarted())) {
                miss_ball++;
                current_alpha_background=0.0f;
                boss_balloon.setMissed(false);
                change_background = true;
                if (vibro) {
                    Gdx.input.vibrate(125);
                }
            }
            //смерть босса
            if ((!boss_balloon.isLive()) & (!boss_balloon.isDead())) {
                boss_balloon.make_dead();
                for (Balloon balloon : balloons) {
                    balloon.start_spawn();

                    //score_num.addScore(50);
                    //score_num.setCombo(9);
                }

            }
            if (boss_balloon.isGive_score()) {
                boss_balloon.setGive_score(false);
                //System.out.println("add_score");
                score_num.addScore(50);
                score_num.setCombo(9);
            }


            index = 0;

            if ((started) & (position.y < 1000)) {
                velosity.scl(dt);
                position.add(0, velosity.y, 0);
                velosity.scl(1 / dt);
            }
            if (game_over_ball_fly){
                velosity.scl(dt);
                position.add(velosity.x, 0, 0);
                velosity.scl(1 / dt);
            }
            if (!boss_balloon.isLive()) {
                if (boss_Music.isPlaying()) {
                    if (!mute) {
                        boss_Music.stop();
                        background_Music.play();
                    }
                }
            }


            if (started) {
                for (Balloon balloon : balloons) {
                    balloon.update(dt, shaker);

                    if (balloon.getPosition().y > 720) {
                        balloon.setPosition(balloon.getPosition().x, -220 - random(50));

                        balloon.setVelosity(get_speed_for_balloon());
                        miss_ball++;
                        current_alpha_background=0.0f;
                        change_background = true;
                        if (vibro) {
                            Gdx.input.vibrate(125);
                        }
                    }

                    if (balloon.isLive_out()) {
                        balloon.dispose();
                        //System.out.println("balloon disposed :"+cautch_ball);
                        balloons.removeIndex(index);
                    }
                    index++;
                }
            }

            for (Cloud cloud : clouds) {
                cloud.update(dt);
                if (cloud.getPosition().x < -222) {
                    cloud.setPosition(+480 + 222 + random(0), cloud.getPosition().y);
                    cloud.change_texture();
                }
            }

            shaker.update(dt);
            score_num.update(dt);
            if (balloons.size>=1) {
                effect.setPosition(balloons.get(0).getPosition().x, balloons.get(0).getPosition().y);
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        //sb.disableBlending();
        if (current_alpha_background<1.0f)
        {
            sb.setColor(1,1,1,current_alpha_background);
            sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);
            sb.setColor(1,1,1,1.0f-current_alpha_background);
            sb.draw(background_frames.get(miss_ball-1), -25, -25, 550, 900);
            current_alpha_background+=currnent_dt_background;
        }
        else
        {
            sb.setColor(1,1,1,1.0f);
            sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);
        }


        //sb.enableBlending();


        effect.draw(sb);



        if ((started)|(boss_balloon.isStarted())){
            sb.draw(score,((int) shaker.getCamera_sh().position.x)-140,((int) shaker.getCamera_sh().position.y)+360,115,31);
           //score_num.draw(sb, (int) (shaker.getBaseX()+225), (int) (shaker.getBaseY()+760));
            score_num.draw(sb,((int) shaker.getCamera_sh().position.x)-15, ((int) shaker.getCamera_sh().position.y)+360);
            //sb.draw(frames_numbers.get(megred_high_score[0]),285,760,25,31);
            //sb.draw(frames_numbers.get(megred_high_score[1]),265,760,25,31);
            //sb.draw(frames_numbers.get(megred_high_score[2]),245,760,25,31);
            //sb.draw(frames_numbers.get(megred_high_score[3]),225,760,25,31);
        }

        if ((!started)&(!boss_balloon.isStarted())){
            sb.draw(your_high_score,130,100,211,74);
            sb.draw(tap_to_play,80,360,335,51);
            score_num.draw(sb,190,50);
            //sb.draw(frames_numbers.get(megred_high_score[0]),250,50,25,31);
            //sb.draw(frames_numbers.get(megred_high_score[1]),230,50,25,31);
            //sb.draw(frames_numbers.get(megred_high_score[2]),210,50,25,31);
            //sb.draw(frames_numbers.get(megred_high_score[3]),190,50,25,31);
        }

        for (Cloud cloud : clouds) {
            sb.setColor(1,1,1,0.9f);
            sb.draw(cloud.getTexture(),cloud.getPosition().x,cloud.getPosition().y,221,100);
            sb.setColor(1,1,1,1);

        }
        if (!game_over_ball_fly) {
            //sb.draw(texture_poop_balloon, position.x, position.y, 463, 218);//заголовок
            sb.draw(texture_poop_balloon, position.x+(texture_poop_balloon.getWidth()/2)-(resizer_poop_balloon.getSize_x()/2), position.y+(texture_poop_balloon.getHeight()/2)-(resizer_poop_balloon.getSize_y()/2), resizer_poop_balloon.getSize_x(), resizer_poop_balloon.getSize_y());//заголовок
        }
        if (boss_balloon.isStarted()){
            sb.draw(boss_balloon.getTexture_boss(),boss_balloon.getPosition().x,boss_balloon.getPosition().y,95,190);
        }
        if (game_over_well_play){
            if (load_hiscore<cautch_ball){
                sb.draw(well_played,100,200,200,100);
            }else
            {
                sb.draw(nice_played,100,200,200,100);
            }
        }

        //if (!boss_balloon.isStarted()){
        for (Balloon balloon : balloons) {
            sb.setColor(1,1,1,0.9f);
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
                    effect_pop.start();
                    sb.draw(poof_balloon_g.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                    effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                    break;
                case 6:
                    sb.draw(poof_balloon_y.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                   // effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                    break;
                case 7:
                    sb.draw(poof_balloon_b.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                    //effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                    break;
                case 8:
                    sb.draw(poof_balloon_r.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                   // effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                    break;
                case 9:
                    sb.draw(poof_balloon_p.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                   // effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                    break;
                case 10:
                    sb.setColor(1,1,1,1);
                    sb.draw(texture_b_o, balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                    break;
                case 11:
                    sb.setColor(1,1,1,1);
                    sb.draw(balloon.getFrames(), balloon.getPosition().x-50, balloon.getPosition().y+40, 190, 190);
                    break;
            }

       // }
        }
        effect_pop.draw(sb);
        sb.setColor(1,1,1,1);

        switch (current_combo){
            case 2:
                sb.draw(multi_x2,min_x,min_y);
                //score_num.addScore(2);
                break;
            case 3:
                sb.draw(multi_x3,min_x,min_y);
               // score_num.addScore(6);
                break;
            case 4:
                sb.draw(multi_x4,min_x,min_y);
               // score_num.addScore(12);
                break;
            case 5:
                sb.draw(multi_x5,min_x,min_y);
               // score_num.addScore(20);
                break;
            case 6:
                sb.draw(multi_x6,min_x,min_y);
                break;
        }

        FontRed1.draw(sb, " FPS : "+  Gdx.graphics.getFramesPerSecond(), 10, 790);

		/*
        if (mute){
            sb.draw(muted,((int) shaker.getCamera_sh().position.x)+240-69,((int) shaker.getCamera_sh().position.x)+300+20,64,64);
        }else{
            sb.draw(unmuted,((int) shaker.getCamera_sh().position.x)240-69,((int) shaker.getCamera_sh().position.x)+300+20,64,64);
        }*/

		sb.draw(options,((int) (shaker.getCamera_sh().position.x)+240-69),((int) (shaker.getCamera_sh().position.y+400-69)),64,64);

        //System.out.println("Missed balls: "+miss_ball);
        switch (miss_ball){
            default:
                //gsm.set(new GameoverState(gsm,position.x));
                break;
            case 3:
                if (!game_over_start) {
                    for (Balloon balloon : balloons) {
                        balloon.setPooped();
                        balloon.stop_spawn();
                    }
                    game_over_start=true;
                    //  balloons.add(new Balloon(random(4) * 96, -195 - random(50), get_speed_for_balloon(), !boss_balloon.isStarted()));
                    load_hiscore = prefs.getInteger("highscore");
                    cautch_ball=score_num.getScore();
                    prefs.putInteger("last_match_score", cautch_ball);
                    prefs.flush();
                    if (load_hiscore<cautch_ball) {
                        prefs.putInteger("highscore", cautch_ball);
                        prefs.flush();
                    }
                }


               // gsm.set(new GameoverState(gsm));

                break;
            case 2:
               // if (change_background) {
               //     change_background=false;
                    //miss_ball++;
                   // background = new Texture("background_evening.png");
               // }

                break;

            case 1:
               // if (change_background) {
                  //  change_background = false;
                    //miss_ball++;
                  // background = new Texture("background_sunset.png");
               // }
                break;

            case 0:
                break;

        }
        if (pause){
            sb.draw(pause_bgnd,((int) shaker.getCamera_sh().position.x)-240,((int) shaker.getCamera_sh().position.y)-400,480,800);

            if (mute){
                sb.draw(muted,((int) shaker.getCamera_sh().position.x)+30,((int) shaker.getCamera_sh().position.y)-78,150,156);
            }else{
                sb.draw(unmuted,((int) shaker.getCamera_sh().position.x)+30,((int) shaker.getCamera_sh().position.y)-78,150,156);
            }
            if (vibro){
                sb.draw(vibrated,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78,150,156);
            }else{
                sb.draw(unvibrated,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78,150,156);
            }

        }
        if (game_over_ball_fly) {
            sb.draw(big_balloon, position.x, position.y,860, 800);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        poop_Sound.dispose();
        background_Music.dispose();
        boss_Music.dispose();
        background.dispose();
        texture_b_b.dispose();
        texture_b_r.dispose();
        texture_b_g.dispose();
        texture_b_y.dispose();
        texture_b_p.dispose();
        poof_balloon_atlas.dispose();
        background_frames.clear();
        muted.dispose();
        unmuted.dispose();
    }

    public int get_speed_for_balloon(){
        int speed = 550;
        speed = 100+(int)(Math.sqrt(cautch_ball)*8)+random( ((int)Math.log(cautch_ball+2))*40);
        if (speed>=500){
            speed=500;//Ограничитель скорости шаров
        }
       // System.out.println("Math.log: "+((int) Math.log(cautch_ball+2)*35));
        //System.out.println("Speed: "+speed);
        return speed;
    }
    public void missed_ball(){
        miss_ball--;
    }

}
