package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Asup.D on 05.09.2017.
 */

public class Boss_balloon extends Creature {
    private Vector3 position;
    private Vector3 velosity;
    private Vector3 velosity_clicked;//вектор движения вниз
    private Rectangle bounds;
    private Texture texture_boss;

    public static final float ANIMATION_TIME_BOSS_IDLE= 0.383f;//Время анимации обычного состояния
    public static final float ANIMATION_TIME_BOSS_CLICKED= 0.050f;//и при клике
    public static final float TIME_BOSS_CLICKED= 0.201f;//Время при котором босс считается кликнутым

    private Texture texture_boss_atlas;//Атлас для загрузки анимаций босса
    private Animation boos_animation_idle;//обычное состояние
    private Animation boos_animation_clicked;//при клике


    private int health; //здоровье боссаqqqqqqqqqqqqqqqqqq
    /*жив ли босс в мире\ старт босса\ смерть босса\ клик по боссу */
    private boolean live,started,clicked;

    private static boolean dead=false;
    private byte phase;//фаза босса
    private boolean missed;//Пропуск шара
    private boolean reposition;//
    private boolean give_score=false;

    private int clicked_phase_count;
    private float current_dt;

    public boolean isDead() {
        return dead;
    }

    public boolean isMissed() {
        return missed;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Boss_balloon(int x, int y, int grav) {
        //логические операнды
        live = true;
        started=false;
        dead=false;
        missed =false;
        clicked=false;
        reposition = false;
        //texture_boss =  new Texture("ghost_balloon.png");

        texture_boss_atlas = new Texture("ghost_norm.png");
        boos_animation_idle = new Animation(new TextureRegion(texture_boss_atlas),6,ANIMATION_TIME_BOSS_IDLE);
        texture_boss_atlas = new Texture("ghost_hit.png");
        boos_animation_clicked = new Animation(new TextureRegion(texture_boss_atlas),2,ANIMATION_TIME_BOSS_CLICKED);

        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, grav, 0);
        velosity_clicked = new Vector3(0,-550,0);
        health=26;
        phase =1;
        clicked_phase_count=0;
        bounds = new Rectangle(x, y, 95 , 190);

        current_dt=0.0f;
    }
    public Vector3 getPosition() {
        return position;
    }
    public  void Start(){
        started=true;
    }

    public boolean isGive_score() {
        return give_score;
    }

    public void setGive_score(boolean give_score) {
        this.give_score = give_score;
    }

    public boolean isStarted() {
        return started;
    }

    public TextureRegion getTexture_boss() {
        if (clicked){
            return boos_animation_clicked.getFrames();
        }
        else {
            return boos_animation_idle.getFrames();
        }
    }

    public void clicked_boss(){
        clicked=true;
        current_dt=0.0f;
        if (health<=0){
            kill_boss();
        }

        //Действия по клику на 1 фазу
        if (phase==1) {
            clicked_phase_count++;
            if (clicked_phase_count>=2){
                clicked_phase_count=0;
                reposition=true;
                velosity.x=random(8*150)-600;
                reposition();
            }
        }

        //перевод на 2 фазу
        if ((health<=13)&(phase==1)){phase=2;}

        //вторая фаза
        if (phase==2){
            clicked_phase_count++;
            if (clicked_phase_count>=2){
                clicked_phase_count=0;
                //reposition();
                velosity.y-=10;
            }
            velosity.y+=1;
            velosity.x=random(4*150)-300;
        }
        if (health<=1){health-=200;}


        health--;
    }
    //Смерть босса но без триггера смерти
    public void kill_boss(){
        live=false;
        started=false;
        dead=false;
        give_score=true;
    }
    public void make_dead(){
        dead=true;
    }
    public void make_undead(){
        dead=false;
    }

    public boolean isLive() {
        return live;
    }

    public void setMissed(boolean missed) {
        this.missed = missed;
    }

    @Override
    public void update(float dt) {
        current_dt+=dt;
        if (current_dt>=TIME_BOSS_CLICKED){
            
            clicked=false;
        }

        if (clicked){
            boos_animation_clicked.update(dt);

        }
        else{
            boos_animation_idle.update(dt);
        }

        if ((clicked)&(reposition)) {
            velosity_clicked.scl(dt);
            if (position.y<=-200) {
                position.add(velosity_clicked.x, 0, 0);
            }
            else
            {
                position.add(velosity_clicked.x, velosity_clicked.y, 0);
            }
            velosity_clicked.scl(1 / dt);
            bounds.setPosition(position.x, position.y);
        }
        else {
            velosity.scl(dt);
            position.add(velosity.x, velosity.y, 0);
            velosity.scl(1 / dt);
            bounds.setPosition(position.x, position.y);
            reposition=false;
        }


        if (position.y>900){
            position.y=-200;
            missed=true;
            System.out.println("boss_missed");
        }
        if (position.x>370){
            //position.x=0;
            if (velosity.x>0){
                velosity.x=-velosity.x;
            }

        }
        if (position.x<0){
            //position.x=390;
            if (velosity.x<0) {
                velosity.x = -velosity.x;
            }
        }
    }
    public void reposition(){
        velosity.y+=30;
    }

    @Override
    public void dispose() {
        //texture_boss.dispose();

        texture_boss_atlas.dispose();
    }
}
