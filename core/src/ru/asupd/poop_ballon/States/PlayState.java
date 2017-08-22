package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
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
    private int miss_ball = 0;



    public PlayState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 480 , 800 );
        background = new Texture("badlogic.jpg");

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный


        balloons = new Array<Balloon>();

        for (int i = 0; i <= 4; i++){
            balloons.add(new Balloon(i * 100,i*10));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            System.out.println("touchPos :"+touchPos);
            camera.unproject(touchPos);
            for (Balloon balloon : balloons) {
                if ((balloon.getPosition().x<touchPos.x)&(balloon.getPosition().x+100>touchPos.x)){
                    if ((balloon.getPosition().y<touchPos.y)&(balloon.getPosition().y+200>touchPos.y)){
                        System.out.println("touched the ball :");
                        balloon.setPosition(balloon.getPosition().x, -220-random(50));
                        balloon.setVelosity(200-random(100));
                        miss_ball++;
                    }
                }
            }

        }


    }

    @Override
    public void update(float dt) {
        handleInput();
        for (Balloon balloon : balloons) {
            balloon.update(dt);
            if (balloon.getPosition().y>720){
                balloon.setPosition(balloon.getPosition().x, -220-random(50));
                balloon.setVelosity(200-random(100));
                miss_ball++;}
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0,480,800);
        for (Balloon balloon : balloons) {
            sb.draw(balloon.getTexture(),balloon.getPosition().x,balloon.getPosition().y,100,200);

        }
        FontRed1.draw(sb, "Missed ballons: "+ miss_ball, 10, 780);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
