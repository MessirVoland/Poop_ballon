package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Sprites.Balloon;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Voland on 04.08.2017.
 */



public class PlayState extends State {

    private Array<Balloon> balloons;
    private Texture background;
    private BitmapFont FontRed1;
    private Texture red_cross;
    private Texture muted;
    private Texture unmuted;
    private int cautch_ball = 0;
    private int miss_ball = 0;
    private Sound poop_Sound;
    private Music background_Music;
    boolean mute;
    float volume;
    int index;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 480 , 800 );
        background = new Texture("background.png");

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный

        red_cross = new Texture("Redcross.png");

        muted = new Texture("mute.png");
        unmuted = new Texture("unmute.png");
        mute=false;
        volume=1.0f;


        balloons = new Array<Balloon>();

        poop_Sound = Gdx.audio.newSound(Gdx.files.internal("poop.mp3"));
        background_Music = Gdx.audio.newMusic(Gdx.files.internal("sound.mp3"));

        background_Music.setLooping(true);
        background_Music.play();

        for (int i = 0; i <= 4; i++){
            balloons.add(new Balloon(i * 96,-195-random(50),100));
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
                            System.out.println("touched the ball :");
                            poop_Sound.play(volume);
                            //balloon.setPosition(balloon.getPosition().x, -220-random(50));

                            cautch_ball++;

                            balloon.setPooped(cautch_ball);
                            balloons.add(new Balloon(random(4) * 96, -195 - random(50), (200 + random(cautch_ball * 3) - random(100))));
                        }
                    }
                }
                index++;
            }
            if ((480-69-69-69-69<touchPos.x)&(480-69-69-69-69+64>touchPos.x)){
                if((800-69<touchPos.y)&(800-69+64>touchPos.y)){
                    if (mute) {
                        background_Music.play();
                        volume=1.0f;
                        mute=false;
                    }
                    else {
                        background_Music.pause();
                        volume=0.0f;
                        mute=true;
                    }
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        index=0;
        for (Balloon balloon : balloons) {
            balloon.update(dt);

            if (balloon.getPosition().y>720){
                balloon.setPosition(balloon.getPosition().x, -220-random(50));
                balloon.setVelosity(200+random(cautch_ball*3)-random(100));
                miss_ball++;}
            if (balloon.isLive_out()){
                balloon.dispose();
                balloons.removeIndex(index);
            }
        index++;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0,480,800);
        for (Balloon balloon : balloons) {
            sb.draw(balloon.getTexture(),balloon.getPosition().x,balloon.getPosition().y,95,190);

        }
        FontRed1.draw(sb, " cautch_ball() ballons: "+  cautch_ball, 10, 780);
        if (mute){
            sb.draw(muted,480-69-69-69-69,800-69,64,64);
        }else{
            sb.draw(unmuted,480-69-69-69-69,800-69,64,64);
        }

        switch (miss_ball){
            case 4:
                gsm.set(new GameoverState(gsm));
                break;
            case 3:
                sb.draw(red_cross,480-69-69-69,800-69,64,64);
            case 2:
                sb.draw(red_cross,480-69-69,800-69,64,64);
            case 1:
                sb.draw(red_cross,480-69,800-69,64,64);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        poop_Sound.dispose();
        background_Music.dispose();
        red_cross.dispose();
        background.dispose();
    }
}
