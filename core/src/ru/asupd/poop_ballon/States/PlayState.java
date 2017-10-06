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
import ru.asupd.poop_ballon.Sprites.Balloon;
import ru.asupd.poop_ballon.Sprites.Boss_balloon;
import ru.asupd.poop_ballon.Sprites.Cloud;
import ru.asupd.poop_ballon.Sprites.Hearth_balloon;
import ru.asupd.poop_ballon.Sprites.Star;
import ru.asupd.poop_ballon.Workers.Assets;
import ru.asupd.poop_ballon.Workers.MyInputProcessor;
import ru.asupd.poop_ballon.Workers.Resizer;
import ru.asupd.poop_ballon.Workers.Score;
import ru.asupd.poop_ballon.Workers.Settings;
import ru.asupd.poop_ballon.Workers.Shaker;

import static com.badlogic.gdx.math.MathUtils.random;

/**Игровой модуль
 * Created by Voland on 04.08.2017.
 */



public class PlayState extends State {

    public static Array<Balloon> balloons;//массив шаров
    private Array<Cloud> clouds;//массив шаров
    private Array<Star> stars;//звезды

    private Texture background,pause_bgnd;//задник
    private TextureRegion back_ground_atlas;
    private Array<TextureRegion> background_frames;
    private boolean change_background;//хз
    public float currnent_dt_background=0;
    public static float current_alpha_background=1.0f;

    private BitmapFont FontRed1;//для фпс

    private Texture muted,unmuted;//иконка звука
    private Texture vibrated,unvibrated;//иконка звука

    //private Texture texture_b_b,texture_b_g,texture_b_y,texture_b_r,texture_b_p,texture_b_o;//Шарики

    private Texture texture_poop_balloon;//Название игры
    private Resizer resizer_poop_balloon;
    int get_avr_speed=0;

    private Texture your_high_score,tap_to_play,score;//наибольший счет, таб ту плей, напись счет

    //private Texture multi_x2,multi_x3,multi_x4,multi_x5,multi_x6;
	
	private Texture options;//кнопка опции

    //private Texture numbers;//числа
    //private Array<TextureRegion> frames_numbers;//числа
    public static Score score_num;



    private Texture poof_balloon_atlas;//загрузчик атласов взрывов

    //private Animation poof_balloon_g,poof_balloon_y,poof_balloon_b,poof_balloon_r,poof_balloon_p,poof_balloon_o;

    public static int cautch_ball = 0;//поймано шаров
    public static int miss_ball = 0;//пропущено шаров

    public static Sound poop_Sound;//звук лопания
    public static final float minX = 0.75f;//Диапазон изменения хлопка
    public static final float maxX = 1.7f;
    public static int balloons_count=0;
    public static int balloons_number=0;

    //private int max_combo=0;

    private static Music background_Music,boss_Music;//музыка

    public final static float ANIMATION_TIME=0.266f;//время анимации
    //public final static float ANIMATION_TIME=3.0f;
    private Preferences prefs;//для храниния данных
    private int load_hiscore;//макс счет
    private static boolean mute;//тишина
    private boolean vibro=true;//вибратор
    private static final String APP_STORE_NAME = "Poop_ballons_90471d221cb7702a2b7ab38a5433c26e";
    public static float volume;//звук хлопков)

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
    private Resizer well_played_resizer;
    private Resizer nice_played_resizer;

    public static Shaker shaker;//шейкео
    public static int index;//хз
    private static boolean pause=false;
    private float min_x=0.0f,min_y=0.0f;

    boolean started;//для страрта игры
    private GameoverState gameoverState;
    //int[] megred_high_score = new int[5];//для отображения счета

    private Vector3 position;//Координаты заголовка игры
    private Vector3 velosity;//вектор движения заголовка

    public static Boss_balloon boss_balloon;//босс

    public static Hearth_balloon hearth_balloon;
    public static byte counter_of_h_ballons=0;

    private int chance_of_boss=20;//20%
    private int chance_100_150=random(50)+100;

    public static int STEP_for_balloon=50;
    public static final int MAX_STEP=401;
    public static int current_step=1;
    float cureunt_dt_for_speed=0;

    //Комбо
    public static int current_combo=0;
    private static final float TIME_FOR_COMBO = 1.25f;//максимальное время отображения значка комбо
    float current_time_for_combo = 0.0f;//текущее время комбо
    public static Array<ParticleEffect> combo_effects = new Array<ParticleEffect>();

    private Vector3 touchPos;//вектор прикосновния

    public static Settings settings;

    private MyInputProcessor inputProcessor= new MyInputProcessor();//обработчик событий

    public PlayState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 480 , 800 );
        Gdx.input.setInputProcessor(inputProcessor);
        background = Assets.instance.manager.get(Assets.back_ground_atlas);
        background_frames = new Array<TextureRegion>();
        back_ground_atlas = new TextureRegion(background);
        int frameWidth=back_ground_atlas.getRegionWidth()/4;
        for (int i=0;i<4;i++){
            background_frames.add(new TextureRegion(back_ground_atlas,i*frameWidth,0,frameWidth,back_ground_atlas.getRegionHeight()));
        }

        pause_bgnd = new Texture("pause.png");
        STEP_for_balloon=50;

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный

        shaker = new Shaker(camera);
        started=false;//начало игры

        position = new Vector3(10,500,0);
        velosity=new Vector3(0,200,0);
        cautch_ball=0;
        balloons_count=0;
        balloons_number=0;

        //инициализация музыки
        poop_Sound = Gdx.audio.newSound(Gdx.files.internal("poop.mp3"));
        boss_Music = Gdx.audio.newMusic(Gdx.files.internal("Sound_19272 [Wav_Library_Net].mp3"));

        background_Music = Assets.instance.manager.get(Assets.background_Music);
        background_Music.setVolume(0.3f);
        background_Music.setLooping(true);

        boss_Music.setVolume(0.3f);
        boss_Music.setLooping(true);

        your_high_score = new Texture("best_score_g.png");
        texture_poop_balloon = new Texture("poop_balloon.png");
        resizer_poop_balloon = new Resizer(texture_poop_balloon.getWidth(),texture_poop_balloon.getHeight());
        tap_to_play = new Texture("tap_to_play.png");
        score =  new Texture("score.png");

        well_played = new Texture("wp.png");
        nice_played = new Texture("nice_try.png");
        big_balloon= new Texture("big_balloon.png");

        score_num = new Score();

		options = new Texture("options.png");
        vibrated = new Texture("vibro_on.png");
        unvibrated = new Texture("vibro_off.png");

        prefs = Gdx.app.getPreferences(APP_STORE_NAME);
        settings= new Settings(prefs);
        load_hiscore = prefs.getInteger("highscore");

        mute=settings.isMute();
        if (!mute)
        {
            volume=0.1f;
            background_Music.play();
        }

        miss_ball=0;

        System.out.println("settings.isMute():"+settings.isMute());
        prefs.putInteger("last_match_score", 0);

        //Вывод макс очков
        score_num.setScore(load_hiscore);

        change_background = false;

        //инициализация массива шаров
        balloons = new Array<Balloon>();
        for (int i = 0; i <= 3; i++){
            //int local_speed = get_speed_for_balloon();
            balloons.add(new Balloon(i * 96,-195-random(50),get_speed_for_balloon(),true));
            //balloons.get(i).setAnimation_idle(poof_balloon_g);
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
        hearth_balloon = new Hearth_balloon();

        resizer_poop_balloon.start();
        nice_played_resizer=new Resizer(384,88);
        well_played_resizer=new Resizer(323,164);


    }
// Input

    public static boolean isPause() {
        return pause;
    }

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
                //босс
                if (boss_balloon.isStarted()) {
                    if ((boss_balloon.getPosition().x < touchPos.x) & (boss_balloon.getPosition().x + 100 > touchPos.x)) {
                        if ((boss_balloon.getPosition().y < touchPos.y) & (boss_balloon.getPosition().y + 200 > touchPos.y)) {
                            poop_Sound.play(volume);
                            shaker.shake(0.40f);
                            boss_balloon.clicked_boss();
                        }
                    }
                }
                //--------------------------------------//
            }
            //клик по опциям второй

            //бывшее место паузы
            //cтарт игры
            if ((!started)&(!pause)) {
                if (!boss_balloon.isStarted()) {
                    started = true;
                    score_num.setScore(0);
                }
            }

            if (pause) {

                //вибро
                if ((60 < touchPos.x) & (60 +150 > touchPos.x)) {
                    if ((400 - 78 < touchPos.y) & (400 - 78 + 156 > touchPos.y)) {
                        if (vibro){
                            vibro=false;

                            prefs.putBoolean("vibro",vibro);
                            prefs.flush();
                        }
                        else
                        {
                            vibro=true;
                            Gdx.input.vibrate(125);
                            prefs.putBoolean("vibro",vibro);
                            prefs.flush();
                        }

                    }
                }
                //рестарт
                //settings.clicked(touchPos,gsm);
            }
        }

    }

    public static void null_Current_combo() {
        current_combo = 0;
    }

    public static final void setPAUSE(){
        pause = true;
    }
    public static final void setUNPAUSE(){
        pause = false;
    }

    @Override
    public void update(final float dt) {
        handleInput();

        cureunt_dt_for_speed+=dt;
        if (cureunt_dt_for_speed>=0.6f){
            cureunt_dt_for_speed=0;
            get_avr_speed=get_speed_for_balloon();
        }

        //resizer_poop_balloon.update(dt); ресайзер заглавия?
        if (game_over_start) {
            well_played_resizer.update(dt);
            nice_played_resizer.update(dt);
            for (Star star : stars ){
                star.update(dt);
            }
        }

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

        if (!pause) {

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
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        for (Balloon balloon : balloons) {
                            balloon.update(dt, shaker);

                            if (balloon.getPosition().y > 720) {
                                balloon.setPosition(balloon.getPosition().x, -220 - random(50));

                                balloon.setVelosity(get_speed_for_balloon());
                                miss_ball++;
                                current_alpha_background=0.0f;
                                change_background = true;
                                if (settings.isVibro()) {
                                    Gdx.input.vibrate(125);
                                }
                            }
                        }
                    }
                });
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
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
                });

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

            //if (balloons.size>=1) {
            //    effect.setPosition(balloons.get(0).getPosition().x, balloons.get(0).getPosition().y);
            //}
            for(ParticleEffect combo_effect :combo_effects){
                combo_effect.update(dt);
               // System.out.print("Update combo");
            }
            if (current_combo>=2){
                current_time_for_combo+=dt;

                if (current_time_for_combo>=TIME_FOR_COMBO)
                {
                    current_time_for_combo=0;
                    current_combo=0;
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        //sb.disableBlending();
        //потеря жизни
        if (current_alpha_background<1.0f)
        {
            if (miss_ball!=1) {
                try {
                    sb.setColor(1, 1, 1, current_alpha_background);
                    sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);
                    sb.setColor(1, 1.0f - 0.07f * current_alpha_background, 1.0f - 0.43f * current_alpha_background, 1.0f - current_alpha_background);
                    sb.draw(background_frames.get(miss_ball - 1), -25, -25, 550, 900);
                    current_alpha_background += currnent_dt_background;
                }catch (Throwable e){

                }
            }else
            {
                try {
                    sb.setColor(1, 1, 1, current_alpha_background);
                    sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);
                    sb.setColor(1, 1, 1, 1.0f - current_alpha_background);
                    sb.draw(background_frames.get(miss_ball - 1), -25, -25, 550, 900);
                    current_alpha_background += currnent_dt_background;
                }catch (Throwable e){

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
        }


        //sb.enableBlending();


        //effect.draw(sb);



        if ((started)|(boss_balloon.isStarted())){
            sb.draw(score,((int) shaker.getCamera_sh().position.x)-140,((int) shaker.getCamera_sh().position.y)+360,115,31);
           //score_num.draw(sb, (int) (shaker.getBaseX()+225), (int) (shaker.getBaseY()+760));
            score_num.draw(sb,((int) shaker.getCamera_sh().position.x)-15, ((int) shaker.getCamera_sh().position.y)+360);
        }

        if ((!started)&(!boss_balloon.isStarted())){
            sb.draw(your_high_score,130,100,211,74);
            sb.draw(tap_to_play,80,360,335,51);
            score_num.draw(sb,190,50);
        }
        if (hearth_balloon.isFly()|hearth_balloon.isPooped()) {
            sb.draw(hearth_balloon.getTexture(), hearth_balloon.getPosition().x, hearth_balloon.getPosition().y, 95, 169);
        }

        for (Cloud cloud : clouds) {
            sb.setColor(1,1,1,0.9f);
            sb.draw(cloud.getTexture(),cloud.getPosition().x,cloud.getPosition().y,221,100);
            sb.setColor(1,1,1,1);

        }
        if (!game_over_ball_fly) {
            sb.draw(texture_poop_balloon, position.x, position.y, 463, 218);//заголовок
            //sb.draw(texture_poop_balloon, position.x+(texture_poop_balloon.getWidth()/2)-(resizer_poop_balloon.getSize_x()/2), position.y+(texture_poop_balloon.getHeight()/2)-(resizer_poop_balloon.getSize_y()/2), resizer_poop_balloon.getSize_x(), resizer_poop_balloon.getSize_y());//заголовок
        }
        if (boss_balloon.isStarted()){
            sb.draw(boss_balloon.getTexture_boss(),boss_balloon.getPosition().x,boss_balloon.getPosition().y,95,190);
        }
        if (game_over_well_play){
            if (load_hiscore>cautch_ball){
                sb.draw(nice_played,48+(384/2)-(nice_played_resizer.getSize_x()/2),300+(88/2)-(nice_played_resizer.getSize_y()/2),nice_played_resizer.getSize_x(),nice_played_resizer.getSize_y());
            }else
            {
                sb.draw(well_played,78+(323/2)-(well_played_resizer.getSize_x()/2),300+(164/2)-(well_played_resizer.getSize_y()/2),well_played_resizer.getSize_x(),well_played_resizer.getSize_y());
            }
            for (Star star : stars ){
                sb.draw(star.getTexture(),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
               // sb.draw(Assets.instance.manager.get(Assets.star1),star.getPosition().x+(40/2)-(star.resizer.getSize_x()/2),star.getPosition().y+(40/2)-(star.resizer.getSize_y()/2),star.resizer.getSize_x(),star.resizer.getSize_y());
            }
        }


        //if (!boss_balloon.isStarted()){
        for (Balloon balloon : balloons) {
            sb.setColor(1,1,1,0.9f);
            if (!balloon.isAnim_end()) {
                switch (balloon.getColor_of_balloon()) {

                    case 0:
                        //Assets.manager.get(Assets.balloon_green,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                        sb.draw(Assets.instance.manager.get(Assets.balloon_green, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 1:
                        // Assets.manager.get(Assets.balloon_yellow,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                        sb.draw(Assets.instance.manager.get(Assets.balloon_yellow, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 2:
                        //  Assets.manager.get(Assets.balloon_blue,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                        sb.draw(Assets.instance.manager.get(Assets.balloon_blue, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 3:
                        //  Assets.manager.get(Assets.balloon_red,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                        sb.draw(Assets.instance.manager.get(Assets.balloon_red, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 4:
                        // Assets.manager.get(Assets.balloon_purple,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                        sb.draw(Assets.instance.manager.get(Assets.balloon_purple, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 5:
                        //effect_pop.start();
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        balloon.effect.draw(sb);
                        //effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                        break;
                    case 6:
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        balloon.effect.draw(sb);
                        //balloon.effect.draw(sb);
                        // effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                        break;
                    case 7:
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        balloon.effect.draw(sb);
                        // balloon.effect.draw(sb);
                        //effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                        break;
                    case 8:
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        balloon.effect.draw(sb);
                        //balloon.effect.draw(sb);
                        // effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                        break;
                    case 9:
                        sb.draw(balloon.getFrames_idle(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        balloon.effect.draw(sb);
                        // balloon.effect.draw(sb);
                        // effect_pop.setPosition(balloon.getPosition().x+45,balloon.getPosition().y+145);
                        break;
                    case 10:
                        sb.setColor(1, 1, 1, 1);
                        // Assets.manager.get(Assets.balloon_orange,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                        sb.draw(Assets.instance.manager.get(Assets.balloon_orange, Texture.class), balloon.getPosition().x, balloon.getPosition().y, 95, 190);
                        break;
                    case 11:
                        sb.setColor(1, 1, 1, 1);
                        sb.draw(balloon.getFrames(), balloon.getPosition().x - 50, balloon.getPosition().y + 40, 190, 190);
                        balloon.part_start();
                        balloon.effect.draw(sb);
                        break;
                }
            }
            else
                balloon.effect.draw(sb);

       // }
        }
        //effect.draw(sb);
        sb.setColor(1,1,1,1);

        if (combo_effects.size>=1) {
            for (int j = 0; j <= combo_effects.size - 1; j++) {
                if (!combo_effects.get(j).isComplete()) {
                    combo_effects.get(j).draw(sb);
                } else {
                    combo_effects.removeIndex(j);
                }
            }
        }

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
        FontRed1.setColor(1, 1, 1, 1);
        index=0;
        for (Balloon balloon:balloons){
            index++;
        }
        FontRed1.draw(sb,"balloons_count: "+(4+balloons_count)+" Real: "+(index),10,30);
        FontRed1.draw(sb,"balloons_number: "+(balloons_number),10,45);
        FontRed1.draw(sb,"Speed: "+get_avr_speed,10,60);

        long memUsageJavaHeap = Gdx.app.getJavaHeap();
        long memUsageNativeHeap = Gdx.app.getNativeHeap();
        FontRed1.draw(sb, "JavaHeap : "+memUsageJavaHeap, 10, 775);
        FontRed1.draw(sb, "NativeHeap : "+memUsageNativeHeap,10,760);

		sb.draw(options,((int) (shaker.getCamera_sh().position.x)+240-69),((int) (shaker.getCamera_sh().position.y+400-69)),64,64);

        //System.out.println("Missed balls: "+miss_ball);
        switch (miss_ball){
            default:
                //gsm.set(new GameoverState(gsm,position.x));
                //miss_ball=3;
                break;
            case 3:
                if (!game_over_start) {
                    for (Balloon balloon : balloons) {
                        balloon.setPooped();
                        make_poop_Sound();
                        balloon.stop_spawn();
                    }
                    //убрать шар здоровья
                    if (hearth_balloon.isFly()){
                        hearth_balloon.setFly(false);
                        hearth_balloon.restart();
                    }
                    game_over_start=true;
                    //инициализация массива звезд
                    stars = new Array<Star>();

                    for (int i = 0; i <=score_num.getScore()/100; i++){
                        //int local_speed = get_speed_for_balloon();
                        stars.add(new Star(random(400)+40, random(720)+40));
                        //balloons.get(i).setAnimation_idle(poof_balloon_g);
                    }
                    for (Star star : stars ){
                        star.resizer.start();
                    }

                    nice_played_resizer.start();
                    well_played_resizer.start();
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
               // miss_ball--;
                break;
        }
        if (pause){
            sb.draw(pause_bgnd,((int) shaker.getCamera_sh().position.x)-240,((int) shaker.getCamera_sh().position.y)-400,480,800);

            if (vibro){
                sb.draw(vibrated,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78,150,156);
            }else{
                sb.draw(unvibrated,((int) shaker.getCamera_sh().position.x)-180,((int) shaker.getCamera_sh().position.y)-78,150,156);
            }
            settings.draw(sb,shaker);


        }
        if (game_over_ball_fly) {
            sb.draw(big_balloon, position.x, position.y,860, 800);
        }

        sb.end();
    }

    public static void set_mute(){
        if (boss_balloon.isStarted()){
            boss_Music.pause();
        }else {
            background_Music.pause();
        }
        volume=0.0f;
        mute=true;
    }

    public static void set_unmute(){
        mute=false;
        if (boss_balloon.isStarted()){
            boss_Music.play();
        }else {
            background_Music.play();
        }
        volume=0.1f;
    }
    public static void make_poop_Sound(){
        if (!mute) {
            long id = PlayState.poop_Sound.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (PlayState.maxX - PlayState.minX) + PlayState.minX;
            PlayState.poop_Sound.setPitch(id, finalX);
            PlayState.poop_Sound.setVolume(id, PlayState.volume);
        }
    }

    @Override
    public void dispose() {
        boss_Music.dispose();
        background_frames.clear();
    }


    public static int get_speed_for_balloon(){
        int speed = 550;
        speed = 100+(int)(Math.sqrt(cautch_ball)*8)+random( ((int)Math.log(cautch_ball+2))*40);
        if (speed>=500){
            speed=500;//Ограничитель скорости шаров
        }
        return speed;
    }
    public void missed_ball(){
        miss_ball--;
    }

    public static void setPAUSE(boolean pause) {
        PlayState.pause = pause;
    }
    public static void RESTART_STAGE() {
        gsm.set(new PlayState(gsm));
        PlayState.setUNPAUSE();
    }

}
