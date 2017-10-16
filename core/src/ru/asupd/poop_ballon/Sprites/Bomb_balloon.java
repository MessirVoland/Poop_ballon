package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ru.asupd.poop_ballon.States.PlayState;
import ru.asupd.poop_ballon.Workers.Assets;

import static com.badlogic.gdx.math.MathUtils.random;
import static ru.asupd.poop_ballon.States.PlayState.balloons;
import static ru.asupd.poop_ballon.States.PlayState.balloons_manager;
import static ru.asupd.poop_ballon.States.PlayState.make_poop_Sound;

/**
 * Класс бомбы
 * Created by Asup.D on 16.10.2017.
 */

public class Bomb_balloon extends Creature{
    private int CHANSE_OF_SPAWN=5;//5%
    private Vector3 velosity;
    private Sprite bomb_sprite;
    private boolean fly=false;
    private int ballons_disposed;
    public Bomb_balloon() {
        bomb_sprite = new Sprite(Assets.instance.manager.get(Assets.bomb_balloon_t));
        bomb_sprite.setPosition(random(480-105),-140);
        velosity = new Vector3(0,120,0);
    }

    @Override
    public void update(float dt) {
        if (fly) {
            velosity.scl(dt);
            bomb_sprite.setPosition(bomb_sprite.getX() + velosity.x, bomb_sprite.getY() + velosity.y);
            velosity.scl(1 / dt);
        }
    }
    public void try_to_fly(){
        if ((!fly)&(random(99)<CHANSE_OF_SPAWN)){
            fly=true;
            System.out.println("gjkt");
        }
    }
    public void draw(SpriteBatch sb){
        if (fly) {
            bomb_sprite.draw(sb);
        }
    }
    public void clicked(int x,int y){
        if (fly){
            if (((y>bomb_sprite.getY())&(y< bomb_sprite.getY()+ bomb_sprite.getWidth()))&
            ((x>bomb_sprite.getX())&(x< bomb_sprite.getX()+ bomb_sprite.getHeight()))){
                restart();
                ballons_disposed=0;
                for (Balloon balloon : balloons) {
                    if (balloons_manager.wooden) {
                        PlayState.score_num.addScore(balloons_manager.getCurrent_difficult_up());
                        // System.out.println("Added 2 W score");
                    } else {
                        PlayState.score_num.addScore(1);
                        //  System.out.println("Added 1 nW score");
                    }
                    if (!balloon.isPooped()) {
                        ballons_disposed++;
                    }
                    balloon.setPooped();
                    make_poop_Sound();


                   // balloons.add(new Balloon(random(4) * 96, -195 - random(50), PlayState.get_speed_for_balloon(), !PlayState.boss_balloon.isStarted()));
                }
                for (int i=0;i<ballons_disposed;i++){
                    balloons.add(new Balloon(random(4) * 96, -195 - random(50), PlayState.get_speed_for_balloon(), !PlayState.boss_balloon.isStarted()));
                }
            }
        }
    }
    private void restart(){
        fly=false;
        bomb_sprite.setPosition(random(480-105),-140);
        velosity.y=random(40)+100;
    }

    @Override
    public void dispose() {

    }
}
