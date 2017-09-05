package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Asup.D on 05.09.2017.
 */

public class Boss_balloon extends Creature {
    private Vector3 position;
    private Vector3 velosity;
    private Rectangle bounds;
    private Texture texture_boss;

    private int health; //здоровье босса
    private boolean live,started; //жив ли босс
    private byte phase;//фаза босса

    private int clicked_phase_count;

    public Boss_balloon(int x, int y, int grav ) {
        live = true;started=false;

        texture_boss =  new Texture("ghost_balloon.png");
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, grav, 0);
        health=100;
        phase =1;
        clicked_phase_count=0;
        bounds = new Rectangle(x, y, 95 , 190);
    }
    public Vector3 getPosition() {
        return position;
    }
    public  void Start(){
        started=true;
    }

    public boolean isStarted() {
        return started;
    }

    public Texture getTexture_boss() {
        return texture_boss;
    }

    public void clicked_boss(){
        if (health<=0){
            kill_boss();
        }

        //Действия по клику на 1 фазу
        if (phase==1) {
            clicked_phase_count++;
            if (clicked_phase_count>=5){
                clicked_phase_count=0;
                reposition();
            }
        }

        //перевод на 2 фазу
        if ((health<=75)&(phase==1)){phase=2;}

        //вторая фаза
        if (phase==2){
            clicked_phase_count++;
            if (clicked_phase_count>=5){
                clicked_phase_count=0;
                //reposition();
                velosity.y+=20;
            }
            velosity.y+=1;
            velosity.x=random(4*150)-300;
        }
        if (health<=50){health-=200;}


        health--;
    }
    public void kill_boss(){
        live=false;
        started=false;

    }
    public void reposition(){
        position.x=random(300);
        position.y-=300;
        velosity.y+=80;
    }

    public boolean isLive() {
        return live;
    }
    @Override
    public void update(float dt) {

        velosity.scl(dt);
        position.add(velosity.x, velosity.y, 0);
        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);

        if (position.y>1000){
            position.y=-200;
        }
        if (position.x>480){
            position.x=0;
        }
        if (position.x<0){
            position.x=390;
        }
    }

    @Override
    public void dispose() {
        texture_boss.dispose();

    }
}
