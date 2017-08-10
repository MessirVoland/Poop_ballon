package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
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
        camera.setToOrtho(false, 640 , 800 );
        background = new Texture("badlogic.jpg");

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный


        balloons = new Array<Balloon>();

        for (int i = 1; i <= 4; i++){
            balloons.add(new Balloon(i * 100,i*10));
        }
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        for (Balloon balloon : balloons) {
            balloon.update(dt);
            if (balloon.getPosition().y>640){
                balloon.setPosition(balloon.getPosition().x, random(80));
                miss_ball++;}
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0,640,400);
        for (Balloon balloon : balloons) {
            sb.draw(balloon.getTexture(),balloon.getPosition().x,balloon.getPosition().y,100,300);

        }
        FontRed1.draw(sb, "Missed ballons: "+ miss_ball, 10, 600);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
