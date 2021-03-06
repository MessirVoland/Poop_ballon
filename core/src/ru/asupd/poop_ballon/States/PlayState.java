package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.PerformanceCounter;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Sprites.BackGround;
import ru.asupd.poop_ballon.Sprites.Balloon;
import ru.asupd.poop_ballon.Sprites.Bomb_balloon;
import ru.asupd.poop_ballon.Sprites.Boss_balloon;
import ru.asupd.poop_ballon.Sprites.Cloud;
import ru.asupd.poop_ballon.Sprites.Hearth_balloon;
import ru.asupd.poop_ballon.Sprites.Star;
import ru.asupd.poop_ballon.Workers.Achievement;
import ru.asupd.poop_ballon.Workers.Achivements_GPS;
import ru.asupd.poop_ballon.Workers.Ads_Clicker;
import ru.asupd.poop_ballon.Workers.Assets;
import ru.asupd.poop_ballon.Workers.Balloons_manager;
import ru.asupd.poop_ballon.Workers.Faq;
import ru.asupd.poop_ballon.Workers.MyInputProcessor;
import ru.asupd.poop_ballon.Workers.Resizer;
import ru.asupd.poop_ballon.Workers.Score;
import ru.asupd.poop_ballon.Workers.Settings;
import ru.asupd.poop_ballon.Workers.Shaker;
import ru.asupd.poop_ballon.Workers.Sound_effects;

import static com.badlogic.gdx.math.MathUtils.random;
import static ru.asupd.poop_ballon.MyGdxGame.playServices_my;
import static ru.asupd.poop_ballon.MyGdxGame.showed_ads;
import static ru.asupd.poop_ballon.States.MenuState.sound_effects;
import static ru.asupd.poop_ballon.Workers.Assets.make_PlayState_linear;
import static ru.asupd.poop_ballon.Workers.Balloons_manager.pause_clicked;
import static ru.asupd.poop_ballon.Workers.Balloons_manager.pause_time_passed;
import static ru.asupd.poop_ballon.Workers.Balloons_manager.rescale;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.ANIMATION_TIME_TAP_TO_PLAY;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.ANIMATIO_TIME_TO_PLAY_SIZE;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.APP_STORE_NAME;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.IMMORTAL_TIME;

/**Игровой модуль
 * Created by Voland on 04.08.2017.
 */



public class PlayState extends State {
    public static Array<Balloon> balloons;//массив шаров
    private Array<Cloud> clouds;//массив шаров
    private Array<Star> stars;//звезды

    private Texture pause_bgnd;//задник

    //public static float current_alpha_background=1.0f;

    public static BitmapFont FontRed1;//для фпс

    private float current_immotal;
    private Texture texture_poop_balloon;//Название игры

    private Resizer resizer_poop_balloon;

    private Texture your_high_score,score;//наибольший счет, таб ту плей, напись счет
    private Sprite tap_to_play;
    private float current_tap_to_play;


	public static Sprite options;//кнопка опции

    public static Score score_num;

    public static long cautch_ball = 0;//поймано шаров
    public static int miss_ball = 0;//пропущено шаров

    public static int balloons_count=0;
    public static int balloons_number=0;

    private static Music background_Music;
    //private Music boss_Music;//музыка

    public final static float ANIMATION_TIME=0.266f;//время анимации
    public static Preferences prefs=Gdx.app.getPreferences(APP_STORE_NAME);//для храниния данных
    private int load_hiscore;//макс счет

    public static float volume=1.0f;//звук хлопков)

    //гейм овер
    private boolean game_over_start=false;//перероход в гейм овер
    private boolean game_over_well_play=false;
    private boolean game_over_ball_fly=false;

    private boolean red_balloon_start=true;

    public void setPosition_red(Vector3 position_red) {
        this.position_red = position_red;
    }

    private Vector3 position_red;//Координаты красного шара
    private Vector3 velosity_red=new Vector3(-1200,0,0);//вектор движения красного шара

    private float game_over_dt=0;
    private final static float GAME_OVER_ANIM=0.266f;
    //private Texture well_played;
    //private Texture nice_played;
    private Texture big_balloon;

    private float x_game_over=-480,y_game_over=0;
    private Resizer well_played_resizer;
    private Resizer nice_played_resizer;

    public static Shaker shaker;//шейкео
    public static int index;//хз
    public static boolean pause=false;
    //private float min_x=0.0f,min_y=0.0f;

    public static boolean started;//для страрта игры
    private GameoverState gameoverState;
    //int[] megred_high_score = new int[5];//для отображения счета

    private Vector3 position;//Координаты заголовка игры
    private Vector3 velosity;//вектор движения заголовка

    //с v.0.9.7.rev.B.build.11 босс отключен
    private final static int CHANSE_OF_BOSS = -1;//20  Шанс появления босса
    public static Boss_balloon boss_balloon;//босс

    public static Hearth_balloon hearth_balloon;
    public static byte counter_of_h_ballons=0;

    private int chance_100_150=random(50)+100;

    public static int STEP_for_balloon=50;
    //public static final int MAX_STEP=401;
    public static int current_step=1;
    float cureunt_dt_for_speed=0;

    public static Balloons_manager balloons_manager;

    //Комбо
    public static short current_combo=0;
    private static final float TIME_FOR_COMBO = 1.25f;//максимальное время отображения значка комбо
    float current_time_for_combo = 0.0f;//текущее время комбо
    public static Array<ParticleEffect> combo_effects = new Array<ParticleEffect>();
    public static Array<ParticleEffect> combo_effects_multi = new Array<ParticleEffect>();
    public static Array<ParticleEffect> special_effects = new Array<ParticleEffect>();

    private boolean one_cast_music;
    private float current_dt_one_cast;


    private MyInputProcessor inputProcessor;//обработчик событий
    //private MyInputProcessor inputProcessor2;
    //InputMultiplexer inputMultiplexer = new InputMultiplexer();
    public static PerformanceCounter perfomancecounter;



    public static Bomb_balloon bomb_balloon;
    BackGround background;
    public static Faq faq;
    public static Ads_Clicker ads_clicker;

    public static Settings settings;
    //public static Sprite button_ads_free;

    //private Sprite buuton_vk=new Sprite(Assets.instance.manager.get(Assets.button_vk));

    private int effect_500=0;
    private boolean beat_highscore;

    private Achievement medals=new Achievement(true);


    public static Achivements_GPS achivements_gps=new Achivements_GPS();

    PlayState(GameStateManager gsm) {
        super(gsm);

        make_PlayState_linear();

        settings=MenuState.settings;

        perfomancecounter = new PerformanceCounter("Counter");
        camera.setToOrtho(false, 480 , 800 );

        inputProcessor = new MyInputProcessor();
        //inputProcessor2 = new MyInputProcessor();
        //inputMultiplexer.addProcessor(inputProcessor);
        //inputMultiplexer.addProcessor(inputProcessor2);
        Gdx.input.setInputProcessor(inputProcessor);
        //Gdx.input.setInputProcessor(inputProcessor);
        background=new BackGround();

        //Texture background = Assets.instance.manager.get(Assets.back_ground_atlas);
       // background_frames = new Array<TextureRegion>();
       // back_ground_atlas = new TextureRegion(background);
       // int frameWidth=back_ground_atlas.getRegionWidth()/4;
      //  for (int i=0;i<4;i++){
      //      background_frames.add(new TextureRegion(back_ground_atlas,i*frameWidth,0,frameWidth,back_ground_atlas.getRegionHeight()));
      //  }

        pause_bgnd = Assets.instance.manager.get(Assets.pause_bgnd);

        STEP_for_balloon=50;
        faq = new Faq();
        ads_clicker = new Ads_Clicker();

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный



        shaker = new Shaker(camera);
        started=false;//начало игры

        position = new Vector3(10,500,0);
        velosity=new Vector3(0,200,0);
        cautch_ball=0;
        balloons_count=0;
        balloons_number=0;
        counter_of_h_ballons=0;
        current_immotal=1.0f;
        settings = settings;

        //инициализация музыки
        //poop_Sound = Gdx.audio.newSound(Gdx.files.internal("poop.mp3"));
        //boss_Music = Gdx.audio.newMusic(Gdx.files.internal("Sound_19272 [Wav_Library_Net].mp3"));

        background_Music = Assets.instance.manager.get(Assets.background_Music);
        background_Music.setVolume(1.0f);
        background_Music.setLooping(true);



        //boss_Music.setVolume(0.3f);
        //boss_Music.setLooping(true);

        your_high_score = Assets.instance.manager.get(Assets.your_high_score);
        texture_poop_balloon = Assets.instance.manager.get(Assets.texture_poop_balloon);
        resizer_poop_balloon = new Resizer(texture_poop_balloon.getWidth(),texture_poop_balloon.getHeight());

        //Texture text = new Texture("tap_to_play.png");
        //text.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tap_to_play = new Sprite(Assets.instance.manager.get(Assets.tap_to_play));
        tap_to_play.setPosition(80,360);
        current_tap_to_play=0;

        score =  Assets.instance.manager.get(Assets.score);

        //well_played = new Texture("wp.png");
        //nice_played = new Texture("nice_try.png");
        big_balloon= Assets.instance.manager.get(Assets.big_balloon);

        score_num = new Score();

		options = new Sprite(new Texture("options.png"));
		options.setPosition((shaker.getCamera_sh().position.x + 171),(shaker.getCamera_sh().position.y + 331));
		//options.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        settings.hi_score_refresh();
        load_hiscore = prefs.getInteger("highscore");



        miss_ball=0;

        //System.out.println("settings.isMute():"+settings.isMute());
        prefs.putInteger("last_match_score", 0);

        //Вывод макс очков
        score_num.setScore(load_hiscore);
        //score_num.addScore(950);

        //change_background = false;


        //инициализация массива шаров
        balloons = new Array<Balloon>();
        for (int i = 0; i <= 3; i++){
            //int local_speed = get_speed_for_balloon();
            balloons.add(new Balloon(i * 96,-200-random(50),get_speed_for_balloon(),true));
            //balloons.get(i).setAnimation_idle(poof_balloon_g);
        }

        balloons_manager=new Balloons_manager(balloons);


        //инициализация массива облаков
        if (MenuState.clouds.size>=1){
            clouds=MenuState.clouds;
        }else {
            clouds = new Array<Cloud>();
            for (int i = 0; i <= 4; i++) {
                //clouds.add(new Cloud(random(1000)-400,125*i+100+10,-random(25)-25));
                clouds.add(new Cloud(random(680) - 222, 125 * i + 100 + 10, -random(25) - 25));
            }
        }
        //инициализация босса
        boss_balloon = new Boss_balloon(random(4)*96,-195-random(50),80);

        if (random(100)>= CHANSE_OF_BOSS){
            boss_balloon.setLive(false);
        }
        hearth_balloon = new Hearth_balloon();

        resizer_poop_balloon.start();
        nice_played_resizer=new Resizer(384,88);
        well_played_resizer=new Resizer(323,164);

        one_cast_music=true;
        current_dt_one_cast=0.0f;
        bomb_balloon = new Bomb_balloon();
        //button_ads_free=new Sprite(Assets.instance.manager.get(Assets.button_ads_free));
        //button_ads_free.setPosition(35,680);
        //button_ads_free.scale(-0.5f);

        beat_highscore=true;

        if (settings.isMus()){
            background_Music.play();
        }else
        {
            background_Music.stop();
        }
        if (playServices_my!=null) {
            if (!playServices_my.isSignedIn()) {
                playServices_my.signIn();
            }
        }

    }
// Input

    public static boolean isPause() {
        return pause;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {

            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            index = 0;

            if (!pause) {
                //босс
                if (boss_balloon.isStarted()) {
                    if ((boss_balloon.getPosition().x < touchPos.x) & (boss_balloon.getPosition().x + 100 > touchPos.x)) {
                        if ((boss_balloon.getPosition().y < touchPos.y) & (boss_balloon.getPosition().y + 200 > touchPos.y)) {
                            //poop_Sound.play(volume);
                            shaker.shake(0.40f);
                            boss_balloon.clicked_boss();
                        }
                    }
                }
                //--------------------------------------//
            }

        }
    }
    public static void setPAUSE(){
        pause = true;
    }
    public static void setUNPAUSE(){
        pause = false;
    }

    @Override
    public void update(final float dt) {
        settings.update(dt);

        handleInput();
        if (pause_clicked)
        {
            pause_time_passed+=dt;
            if (pause_time_passed>=0.2f){
                if(rescale){
                    options.scale(0.2f);
                    rescale=false;
                    pause_time_passed=0.0f;
                }else
                {
                    PlayState.setPAUSE(true);
                    pause_clicked=false;
                }
            }
        }
        //супер костыль)
        if (red_balloon_start){
            if (position_red==null){
                position_red= new Vector3(480,0,0);
            }
            if (position_red.x>=-865) {
                velosity_red.scl(dt);
                position_red.add(velosity_red.x, 0, 0);
                velosity_red.scl(1 / dt);
            }else
            {
                red_balloon_start=false;
            }
        }


        if (one_cast_music){
            current_dt_one_cast+=dt;
            if(current_dt_one_cast>=0.25f){
                if (!settings.isMute())
                {
                    volume=1.0f;
                    //System.out.println("Воспроизвести звук");
                    //set_mute();
                    set_unmute();
                }
                one_cast_music=false;
            }
        }

        /*
        cureunt_dt_for_speed+=dt;
        if (cureunt_dt_for_speed>=0.6f){
            cureunt_dt_for_speed=0;
            get_avr_speed=get_speed_for_balloon();
        }*/

        if (game_over_start) {
            well_played_resizer.update(dt);
            nice_played_resizer.update(dt);
            for (Star star : stars ){
                star.update(dt);
            }
        }

        //currnent_dt_background= dt*8;//8; //2;
        
		if (game_over_start){
            game_over_dt+=dt;
            if (game_over_ball_fly){
                if (position.x<=-190) {
                    gameoverState.setPosition(position);
                    gsm.set(gameoverState);
                }
            }

            if (game_over_dt>=1.2f){

                if ((!game_over_ball_fly)&(game_over_well_play)){

                    if (combo_effects.size==0) {
                        if (score_num.getBuffer() >= 0) {
                            game_over_ball_fly = true;
                            sound_effects.snd_big_baloon();
                            game_over_dt = 0;
                            System.out.println("cautch_ball : " + cautch_ball);
                        }
                    }
                }

                if (!game_over_well_play){
                    if (score_num.getScore()>load_hiscore){
                        sound_effects.snd_titles();
                        combo_effects.add(new ParticleEffect(Assets.well_play_particle));
                        combo_effects.get(combo_effects.size - 1).setPosition(240, 500);
                        combo_effects.get(combo_effects.size - 1).start();
                    }else
                    {
                        sound_effects.snd_titles();
                        combo_effects.add(new ParticleEffect(Assets.nice_try_particle));
                        combo_effects.get(combo_effects.size - 1).setPosition(240, 500);
                        combo_effects.get(combo_effects.size - 1).start();
                    }
                    game_over_well_play=true;
                    well_played_resizer.setPosition(0);
                    nice_played_resizer.setPosition(0);
                    well_played_resizer.start();
                    nice_played_resizer.start();
                    for (Star star : stars ){
                        star.resizer.setPosition(0);
                        star.resizer.start();
                    }
                    game_over_dt=0;
                    velosity.x=-1200;
                    velosity.y=0;
                    position.x=480;
                    position.y=0;
                    gameoverState = new GameoverState(gsm,-190,stars);
                }
            }
        }
        //0.0


        if (!pause) {


            if (boss_balloon.isStarted()) {
                boss_balloon.update(dt);
            }

            if (started) {
                if (beat_highscore){
                    if (score_num.getScore()>load_hiscore){
                        beat_highscore=false;
                        sound_effects.snd_titles();
                        combo_effects.add(new ParticleEffect(Assets.beat_high_score));
                        combo_effects.get(combo_effects.size - 1).setPosition(240, 500);
                        combo_effects.get(combo_effects.size - 1).start();
                    }
                }
                if (score_num.getScore() / 500 > effect_500) {
                    effect_500++;


                    // Эффект каждые 500 очков
                    /*
                    switch (random(2)) {
                        case 0:
                            sound_effects.snd_titles();
                            //System.out.println("Effect start cool");
                            combo_effects.add(new ParticleEffect(Assets.score_500_effect_cool));
                            combo_effects.get(combo_effects.size - 1).setPosition(240, 500);
                            combo_effects.get(combo_effects.size - 1).start();
                            break;
                        case 1:
                            sound_effects.snd_titles();
                            //System.out.println("Effect start wow");
                            combo_effects.add(new ParticleEffect(Assets.score_500_effect_wow));
                            combo_effects.get(combo_effects.size - 1).setPosition(240, 500);
                            combo_effects.get(combo_effects.size - 1).start();
                            break;
                        case 2:
                            sound_effects.snd_titles();
                            //System.out.println("Effect start amazing");
                            combo_effects.add(new ParticleEffect(Assets.score_500_effect_amazing));
                            combo_effects.get(combo_effects.size - 1).setPosition(240, 500);
                            combo_effects.get(combo_effects.size - 1).start();
                            break;
                        default:
                            System.out.println("Effect start ERROR!");
                            break;

                    }
                    */
                }
            }

            //Старт босса
            if ((cautch_ball > chance_100_150) & (!boss_balloon.isStarted())) {
                if (boss_balloon.isLive()) {
                    boss_balloon.Start();
                    //started = false;
                    if (!settings.isMute()) {
                        background_Music.stop();
                        //boss_Music.play();
                    }
                    for (Balloon balloon : balloons) {
                        balloon.stop_spawn();
                    }
                }

            }
            //Активные действия и проверки босса
            if ((boss_balloon.isMissed()) & (boss_balloon.isStarted())) {
                miss_ball++;
                //current_alpha_background=0.0f;
                boss_balloon.setMissed(false);
                //change_background = true;
                if (settings.isVibro()) {
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
                //score_num.setCombo(9);
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
            /*
            if (!boss_balloon.isLive()) {
                if (boss_Music.isPlaying()) {
                    if (!settings.isMute()) {
                        boss_Music.stop();
                        background_Music.play();
                    }
                }
            }*/


            if (started) {
                {
                    if (current_immotal<=IMMORTAL_TIME) {
                        current_immotal += dt;
                    }
                    int ballons_disposed = 0;
                    boolean missed=false;
                        for (Balloon balloon : balloons) {
                            balloon.update(dt, shaker);

                            if (balloon.getPosition().y > 720) {
                                balloon.setPosition(balloon.getPosition().x, -220 - random(50));

                                balloon.setVelosity(get_speed_for_balloon());
                                if (current_immotal>=IMMORTAL_TIME) {
                                    miss_ball++;
                                    missed=true;
                                    combo_effects.add(new ParticleEffect(Assets.miss_ball));
                                    combo_effects.get(combo_effects.size - 1).setPosition(240, 800);
                                    combo_effects.get(combo_effects.size - 1).start();
                                    System.out.println("Missed ball");
                                    sound_effects.snd_life();
                                    //current_alpha_background = 0.0f;
                                    current_immotal = 0;
                                    //change_background = true;
                                    if (settings.isVibro()) {
                                        Gdx.input.vibrate(125);
                                    }
                                }
                            }
                        }
                        if (missed){
                            for (Balloon balloon : balloons) {
                                if (!balloon.isPooped()) {
                                    ballons_disposed++;
                                }
                                balloon.setPooped();
                                //make_poop_Sound();
                            }
                            for (int i=0;i<ballons_disposed;i++){
                                balloons.add(new Balloon(random(4) * 96, -260 - random(80), PlayState.get_speed_for_balloon(), !PlayState.boss_balloon.isStarted()));
                            }
                        }

                    }
                {
                        index=0;
                        for (Balloon balloon : balloons){
                            if (balloon.isLive_out()) {
                                balloon.dispose();
                                //System.out.println("balloon disposed :"+cautch_ball);
                                try {
                                    balloons.removeIndex(index);
                                }catch (Throwable e){
                                   // System.out.println(e);
                                }
                            }
                            index++;
                        }
                    }
            }else{
                current_tap_to_play+=dt;
                if (current_tap_to_play<=ANIMATION_TIME_TAP_TO_PLAY){
                    tap_to_play.scale(ANIMATIO_TIME_TO_PLAY_SIZE);
                }
                else
                {
                    current_tap_to_play=0;
                    ANIMATIO_TIME_TO_PLAY_SIZE=-ANIMATIO_TIME_TO_PLAY_SIZE;
                }
            }

            for (Cloud cloud : clouds) {
                cloud.update(dt);
                if (cloud.getPosition().x < -222) {
                    cloud.setPosition(+480 + 222 + random(0), cloud.getPosition().y);
                    cloud.change_texture();
                }
            }

            if ((hearth_balloon.isFly())|(hearth_balloon.isPooped())){
                hearth_balloon.update(dt);
                if (hearth_balloon.getPosition().x>=480+95){
                    counter_of_h_ballons++;
                    hearth_balloon.dispose();
                    hearth_balloon.setFly(false);
                    hearth_balloon.restart();
                }
                if (hearth_balloon.isDispose()){
                    hearth_balloon.dispose();
                    hearth_balloon=new Hearth_balloon();
                }
            }

            shaker.update(dt);
            score_num.update(dt);
            options.setPosition((shaker.getCamera_sh().position.x + 171),(shaker.getCamera_sh().position.y + 331));

            for(ParticleEffect combo_effect :combo_effects){
                combo_effect.update(dt);
            }

            for(ParticleEffect combo_effect :combo_effects_multi){
                combo_effect.update(dt);
            }

            for(ParticleEffect combo_effect :special_effects){
                combo_effect.update(dt);
            }

            if (current_combo>=2){
                current_time_for_combo+=dt;

                if (current_time_for_combo>=TIME_FOR_COMBO)
                {
                    current_time_for_combo=0;
                    current_combo=0;
                }
            }
            bomb_balloon.update(dt);

        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        //sb.disableBlending();
        //потеря жизни
        /*
        if (current_alpha_background<1.0f)
        {
            if (miss_ball==5) {
                try {
                    sb.setColor(1, 1, 1, current_alpha_background);
                    sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);
                    sb.setColor(1, 1.0f - 0.07f * current_alpha_background, 1.0f - 0.43f * current_alpha_background, 1.0f);
                    sb.draw(background_frames.get(miss_ball - 1), -25, -25, 550, 900);
                    //current_alpha_background += currnent_dt_background;
                    current_alpha_background += currnent_dt_background;
                }catch (Throwable ignored){

                }
            }else
            {
                try {
                    sb.setColor(1, 1, 1, 1.0f);
                    sb.draw(background_frames.get(miss_ball - 1), -25, -25, 550, 900);
                    sb.setColor(1, 1, 1, current_alpha_background);
                    //System.out.println(current_alpha_background);
                    sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);

                    current_alpha_background += currnent_dt_background;
                }catch (Throwable ignored){
                }
            }
            if (current_alpha_background>=1.0f){
                current_alpha_background=1.0f;
            }
        }
        else
            //восстановление
        if (current_alpha_background>1.0f)
        {
            if (miss_ball!=0) {
                sb.setColor(1, 1, 1, current_alpha_background - 1.0f);
                sb.draw(background_frames.get(miss_ball + 1), -25, -25, 550, 900);
                sb.setColor(1,  1.0f-0.07f*current_alpha_background, 1.0f-0.43f*current_alpha_background, 1.0f - current_alpha_background - 1.0f);
                sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);
                current_alpha_background -= currnent_dt_background;
            }
            else
            {
                sb.setColor(1, 1, 1, current_alpha_background - 1.0f);
                sb.draw(background_frames.get(miss_ball + 1), -25, -25, 550, 900);
                sb.setColor(1, 1, 1, 1.0f - current_alpha_background - 1.0f);
                sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);
                current_alpha_background -= currnent_dt_background;
            }
            if (current_alpha_background<=1.0f){
                current_alpha_background=1.0f;
            }
        }
        else
        { //обычный режим
            sb.setColor(1,1,1,1.0f);
            sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);
        }*/
        background.draw(sb);

        bomb_balloon.draw(sb);

        if ((started)|(boss_balloon.isStarted())){
            sb.draw(score,((int) shaker.getCamera_sh().position.x)-140,((int) shaker.getCamera_sh().position.y)+360,115,31);
           //score_num.draw(sb, (int) (shaker.getBaseX()+225), (int) (shaker.getBaseY()+760));
            score_num.draw(sb,((int) shaker.getCamera_sh().position.x)-15, ((int) shaker.getCamera_sh().position.y)+360);
        }

        if ((!started)&(!boss_balloon.isStarted())){


            tap_to_play.draw(sb);

            if (showed_ads) {
                medals.draw_current_medal(sb,170,70,130,160);
                sb.draw(your_high_score,130,200,211,74);
                score_num.draw_center(sb,190,150,32,40);
            }else
            {
                medals.draw_current_medal(sb,175,0,120,160);
                sb.draw(your_high_score,130,100,211,74);
                score_num.draw_center(sb,190,50,32,40);
            }
            //button_ads_free.draw(sb);

            //leaderboard.setPosition(10,350);
            //leaderboard.draw(sb);

            //Подсказка
            /*if (!faq.isShow()) {
                if (showed_ads) {
                    sb.draw(Assets.instance.manager.get(Assets.faq), 380, 70);
                }else
                    sb.draw(Assets.instance.manager.get(Assets.faq), 380, 0);
            }*/


        }
        if (hearth_balloon.isFly()|hearth_balloon.isPooped()) {
            sb.draw(hearth_balloon.getTexture(), hearth_balloon.getPosition().x, hearth_balloon.getPosition().y, 95, 169);
        }



        for (Cloud cloud : clouds) {
            sb.setColor(1,1,1,0.9f);
            sb.draw(cloud.getTexture(),cloud.getPosition().x,cloud.getPosition().y);
            sb.setColor(1,1,1,1);
        }


        if (!game_over_ball_fly) {
            sb.draw(texture_poop_balloon, position.x, position.y, 463, 218);//заголовок
            //sb.draw(texture_poop_balloon, position.x+(texture_poop_balloon.getWidth()/2)-(resizer_poop_balloon.getSize_x()/2), position.y+(texture_poop_balloon.getHeight()/2)-(resizer_poop_balloon.getSize_y()/2), resizer_poop_balloon.getSize_x(), resizer_poop_balloon.getSize_y());//заголовок
        }

        faq.draw(sb);
        if (boss_balloon.isStarted()){
            sb.draw(boss_balloon.getTexture_boss(),boss_balloon.getPosition().x,boss_balloon.getPosition().y,95,190);
        }
        if (game_over_well_play){
          /*  if (load_hiscore>cautch_ball){
                sb.draw(nice_played,48+(384/2)-(nice_played_resizer.getSize_x()/2),300+(88/2)-(nice_played_resizer.getSize_y()/2),nice_played_resizer.getSize_x(),nice_played_resizer.getSize_y());
            }else
            {
                sb.draw(well_played,78+(323/2)-(well_played_resizer.getSize_x()/2),300+(164/2)-(well_played_resizer.getSize_y()/2),well_played_resizer.getSize_x(),well_played_resizer.getSize_y());
            }*/
            for (Star star : stars ){
                sb.draw(star.getTexture(),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
               // sb.draw(Assets.instance.manager.get(Assets.star1),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
            }
        }


        options.draw(sb);

        if (special_effects.size>=1) {
            for (int j = 0; j <= special_effects.size - 1; j++) {
                if (!special_effects.get(j).isComplete()) {
                    special_effects.get(j).draw(sb);
                } else {
                    special_effects.removeIndex(j);
                }
            }
        }

        balloons_manager.draw(sb);



        if (combo_effects.size>=1) {
            for (int j = 0; j <= combo_effects.size - 1; j++) {
                if (!combo_effects.get(j).isComplete()) {
                    combo_effects.get(j).draw(sb);
                } else {
                    combo_effects.removeIndex(j);
                }
            }
        }

        if (combo_effects_multi.size>=1) {
            for (int j = 0; j <= combo_effects_multi.size - 1; j++) {
                if (!combo_effects_multi.get(j).isComplete()) {
                    combo_effects_multi.get(j).draw(sb);
                } else {
                    combo_effects_multi.removeIndex(j);
                }
            }
        }



        if (current_immotal<=IMMORTAL_TIME) {
            //FontRed1.setColor(1, 0, 0, 1);
            //FontRed1.draw(sb, "IMMORTAL", 10, 770);
        }

        //Справочная информация
        /*
        int fps = Gdx.graphics.getFramesPerSecond();
        if (fps >= 45) {
            // 45 or more FPS show up in green
            FontRed1.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 or more FPS show up in yellow
            FontRed1.setColor(1, 1, 0, 1);
        } else {
            // less than 30 FPS show up in red
            FontRed1.setColor(1, 0, 0, 1);
        }

        FontRed1.draw(sb, " FPS : "+  fps, 10, 790);
        */

        FontRed1.setColor(1, 1, 1, 1);
        /*
        FontRed1.draw(sb,"balloons_count: "+(4+balloons_count)+" Real: "+balloons.size,10,30);
        FontRed1.draw(sb,"balloons_number: "+(balloons_number),10,45);
        FontRed1.draw(sb,"Speed: "+get_avr_speed,10,60);
*/
        //long memUsageJavaHeap = Gdx.app.getJavaHeap();
        //long memUsageNativeHeap = Gdx.app.getNativeHeap();
        //FontRed1.draw(sb, "JavaHeap : "+memUsageJavaHeap, 10, 775);
        //FontRed1.draw(sb, "NativeHeap : "+memUsageNativeHeap,10,760);
        //-------------------------------------------------------------/


       // if (!started) {
        //    sb.draw(options, ((int) (shaker.getCamera_sh().position.x) + 240 - 69), ((int) (shaker.getCamera_sh().position.y + 400 - 69)));
       // }else
        //{
       //     sb.draw(Assets.instance.manager.get(Assets.pause_button), ((int) (shaker.getCamera_sh().position.x) + 240 - 69), ((int) (shaker.getCamera_sh().position.y + 400 - 69)));
        //}
        if (miss_ball>=3) {
            if (!game_over_start) {
                for (Balloon balloon : balloons) {
                    balloon.setPooped();
                    //make_poop_Sound();
                    balloon.stop_spawn();
                }
                //убрать шар здоровья
                if (hearth_balloon.isFly()) {
                    hearth_balloon.setFly(false);
                    hearth_balloon.remove();
                    hearth_balloon.dispose();
                }
                game_over_start = true;
                //инициализация массива звезд
                stars = new Array<Star>();

                for (int i = 0; i <= score_num.getScore() / 100; i++) {
                    //int local_speed = get_speed_for_balloon();
                    //stars.add(new Star(random(440) , random(780) ));
                    //balloons.get(i).setAnimation_idle(poof_balloon_g);
                }
                for (Star star : stars) {
                    star.resizer.start();
                }
                nice_played_resizer.start();
                well_played_resizer.start();
                //  balloons.add(new Balloon(random(4) * 96, -195 - random(50), get_speed_for_balloon(), !boss_balloon.isStarted()));
                load_hiscore = prefs.getInteger("highscore");
                cautch_ball = score_num.getScore();
                prefs.putInteger("last_match_score", (int) cautch_ball);

                prefs.flush();
                //if (load_hiscore < cautch_ball) {
                   // prefs.putInteger("highscore", cautch_ball);
                  //  prefs.flush();
                //}
            }
        }else
        {
            //бессмертие)
            //missball--;
        }

        if (pause){
            //sb.draw(pause_bgnd,((int) shaker.getCamera_sh().position.x)-240,((int) shaker.getCamera_sh().position.y)-400,480,800);
            settings.draw(sb,shaker);
        }
        if (game_over_ball_fly) {
            sb.draw(big_balloon, position.x, position.y,860, 800);
        }

        if (position_red!=null) {
            if (position_red.x >= -865) {
                sb.draw(big_balloon, position_red.x, position_red.y, 860, 800);
            }
        }

        sb.end();
    }


    public static void set_mute(){
        volume=0.0f;
    }

    public static void set_mus(){
        if (boss_balloon.isStarted()){
           // boss_Music.pause();
        }else {
            background_Music.pause();
        }
    }
    public static void set_unmus(){
        if (boss_balloon.isStarted()){
            //boss_Music.play();
        }else {
            background_Music.play();
        }
    }

    public static void set_unmute(){
        volume=1.0f;
    }
    public static void make_poop_Sound(){
        sound_effects.poop_sound();
    }

    @Override
    public void dispose() {
        //boss_Music.dispose();
        //background_frames.clear();
        background_Music.stop();
        background_Music.setPosition(0.0f);
        //background_Music=null;
    }


    public static int get_speed_for_balloon(){
        int speed;
        speed = 100+(int)(Math.sqrt(cautch_ball)*8)+random( ((int)Math.log(cautch_ball+2))*40);
        /*if (speed>=500){
            speed=500;//Ограничитель скорости шаров
        }*/
        return speed;
    }
    public void missed_ball(){
        miss_ball--;
    }

    public static void setPAUSE(boolean pause) {
        PlayState.pause = pause;
    }
    public static void RESTART_STAGE() {


        //game_over_ball_fly = true;
        gsm.set(new PlayState(gsm));
        PlayState.setUNPAUSE();
        if (settings.isMus()){
            set_unmus();
        }
        System.out.println("Playsnd");

        sound_effects.snd_big_baloon();
    }

    public static int getCurrent_difficult_up() {

        if (GameoverState.prefs.contains("difficult_up"))
        {
            return GameoverState.prefs.getInteger("difficult_up");
        }
        else
        {
            GameoverState.prefs.putInteger("difficult_up",0);
            GameoverState.prefs.flush();
            return 0;
        }
        //int current_difficult=((settings.hi_score()-MEDAL_START)/MEDAL_SCORE+1);
        //if (current_difficult>=10){
        //    current_difficult=10;//ограничение для золота
        //}
        //if (current_difficult<1){
        //    current_difficult=1;
        //}
    }
    public static void upCurrent_difficult_up() {
        int local_difficult=GameoverState.prefs.getInteger("difficult_up");
        local_difficult++;
        if (local_difficult>=10)
        {
            local_difficult=10;
        }
        GameoverState.prefs.putInteger("difficult_up",local_difficult);
        GameoverState.prefs.flush();
    }
    public static void zeroCurrent_difficult_up() {
        GameoverState.prefs.putInteger("difficult_up",0);
        GameoverState.prefs.flush();
    }

}
