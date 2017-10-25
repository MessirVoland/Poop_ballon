package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ru.asupd.poop_ballon.States.PlayState;
import ru.asupd.poop_ballon.Workers.Assets;

import static com.badlogic.gdx.math.MathUtils.random;
import static ru.asupd.poop_ballon.States.PlayState.balloons;
import static ru.asupd.poop_ballon.States.PlayState.balloons_manager;
import static ru.asupd.poop_ballon.States.PlayState.combo_effects;
import static ru.asupd.poop_ballon.States.PlayState.getCurrent_difficult_up;
import static ru.asupd.poop_ballon.States.PlayState.make_poop_Sound;
import static ru.asupd.poop_ballon.States.PlayState.score_num;
import static ru.asupd.poop_ballon.States.PlayState.settings;
import static ru.asupd.poop_ballon.States.PlayState.shaker;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.CHANSE_OF_SPAWN_BOMB;

/**
 * Класс бомбы
 * Created by Asup.D on 16.10.2017.
 */

public class Bomb_balloon extends Creature{

    private Vector3 velosity;
    private Sprite bomb_sprite;
    private boolean fly=false;
    private boolean left_side;
    private int heals;
    private boolean pooped;
    private float current_delay;
    private int ballons_disposed;
    private ParticleEffect fire_bomb1,fire_bomb2;
    boolean sin_grav_bool=random.nextBoolean();
    public Bomb_balloon() {
        heals=0;
      //  fire_bomb1=new ParticleEffect(Assets.fire_bomb1);
      //  fire_bomb2=new ParticleEffect(Assets.fire_bomb2);
        bomb_sprite = new Sprite(Assets.instance.manager.get(Assets.bomb_balloon_t));
        //bomb_sprite.setPosition(random(480-105),-140);
        if (random(2)==1)
            left_side=true;
        else
            left_side=false;

        if (left_side) {
            bomb_sprite.setPosition(-105, 400 - 139 / 2);
            velosity = new Vector3(120, 0, 0);
        }
        else {
            bomb_sprite.setPosition(480, 400 - 139 / 2);
            velosity = new Vector3(-120, 0, 0);
        }
        pooped = false;
    }

    @Override
    public void update(float dt) {
        if (fly) {
            velosity.scl(dt);
            bomb_sprite.setPosition(bomb_sprite.getX() + velosity.x, bomb_sprite.getY() + velosity.y);
            velosity.scl(1 / dt);
            if (((!left_side)&(bomb_sprite.getX()<-105))|((left_side)&(bomb_sprite.getX()>480))){
                    restart();

                }
            if (fire_bomb1!=null){
                fire_bomb1.update(dt);
                fire_bomb1.setPosition(bomb_sprite.getX()+99,bomb_sprite.getY()+113);

            }
            if (fire_bomb2!=null){
                fire_bomb2.update(dt);
                fire_bomb2.setPosition(bomb_sprite.getX()+74,bomb_sprite.getY()+116);
            }
            int sin_grav = 50;

            if ((velosity.y < sin_grav) & (sin_grav_bool)) {
                velosity.y++;
            }
            if ((velosity.y > -sin_grav) & (!sin_grav_bool)) {
                velosity.y--;
            }
            if (velosity.y > sin_grav) {
                sin_grav_bool = false;
            }
            if (velosity.y < -sin_grav) {
                sin_grav_bool = true;
            }
        }
        if (pooped){
            current_delay+=dt;
            if (current_delay>=0.4f){
                pooped=false;
                setPooped();
            }
        }
    }
    public void try_to_fly(){
        if ((!fly)&(random(99)<CHANSE_OF_SPAWN_BOMB)){
            fly=true;

        }
    }
    public void draw(SpriteBatch sb){
        if (fly) {
            bomb_sprite.draw(sb);
            if (fire_bomb1!=null){
                fire_bomb1.draw(sb);
            }
            if (fire_bomb2!=null){
                fire_bomb2.draw(sb);
            }
        }
    }
    public void clicked(int x,int y){
        if (fly){
            if (((y > bomb_sprite.getY()) & (y < bomb_sprite.getY() + bomb_sprite.getWidth())) &
                ((x > bomb_sprite.getX()) & (x < bomb_sprite.getX() + bomb_sprite.getHeight()))) {

                combo_effects.add(new ParticleEffect(Assets.hearth_ballon_part));
                combo_effects.get(PlayState.combo_effects.size-1).setPosition(bomb_sprite.getX()+50, bomb_sprite.getY()+140);
                combo_effects.get(PlayState.combo_effects.size-1).start();
                score_num.addScore(5);
                switch (heals) {
                    case 0:
                        heals++;

                        fire_bomb1=new ParticleEffect(Assets.fire_bomb1);
                        fire_bomb1.setPosition(bomb_sprite.getX()+99,bomb_sprite.getY()+113);
                        fire_bomb1.start();

                        break;
                    case 1:
                        heals++;
                        float xx=bomb_sprite.getX(), yy=bomb_sprite.getY();
                        bomb_sprite = new Sprite(Assets.instance.manager.get(Assets.bomb_balloon_tt));
                        bomb_sprite.setPosition(xx,yy);
                        fire_bomb2=new ParticleEffect(Assets.fire_bomb2);
                        fire_bomb2.setPosition(bomb_sprite.getX()+74,bomb_sprite.getY()+116);
                        fire_bomb2.start();
                        if (fire_bomb1!=null){
                            fire_bomb1.setDuration(0);
                            fire_bomb1.dispose();
                            fire_bomb1=null;
                        }
                        break;
                    case 2:
                        if (fire_bomb2!=null){
                            fire_bomb2.setDuration(0);
                            fire_bomb2.dispose();
                            fire_bomb2=null;
                        }
                        ballons_disposed = 0;
                        combo_effects.add(new ParticleEffect(Assets.bomb_blow));
                        combo_effects.get(combo_effects.size - 1).setPosition(bomb_sprite.getX() + 50, bomb_sprite.getY() + 65);
                        combo_effects.get(combo_effects.size - 1).start();
                        restart();
                        current_delay = 0;
                        pooped = true;
                        break;
                    case 3:
                        heals=2;
                        break;
                }
            }

        }
    }
    private void setPooped(){
        shaker.shake(0.7f);
        shaker.setUP_intensity(8.0f);
        if (settings.isVibro()){
            Gdx.input.vibrate(250);
        }
        for (Balloon balloon : balloons) {
            if (balloons_manager.wooden) {
                score_num.addScore(getCurrent_difficult_up());
                // System.out.println("Added 2 W score");
            } else {
                score_num.addScore(1);
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
            balloons.add(new Balloon(random(4) * 96, -260 - random(80), PlayState.get_speed_for_balloon(), !PlayState.boss_balloon.isStarted()));
        }
    }
    private void restart(){
        fly=false;
        if (fire_bomb1!=null){
            fire_bomb1.setDuration(0);
            fire_bomb1.dispose();
            fire_bomb1=null;
        }
        if (fire_bomb2!=null){
            fire_bomb2.setDuration(0);
            fire_bomb2.dispose();
            fire_bomb2=null;
        }
        sin_grav_bool=random.nextBoolean();
        heals=0;
        bomb_sprite = new Sprite(Assets.instance.manager.get(Assets.bomb_balloon_t));
        if (fire_bomb1!=null) {
            fire_bomb1.setPosition(bomb_sprite.getX() + 99, bomb_sprite.getY() + 113);
        }
       // fire_bomb1=new ParticleEffect(Assets.fire_bomb1);
       // fire_bomb2=new ParticleEffect(Assets.fire_bomb2);
        if (random(2)==1) {
            left_side = true;
            bomb_sprite.setPosition(-105,400-139/2);
            velosity.x=(random(40)+100);
        }
        else {
            left_side = false;
            bomb_sprite.setPosition(480,400-139/2);
            velosity.x=-(random(40)+100);
        }

    }

    @Override
    public void dispose() {

    }
}
