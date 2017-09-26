package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ru.asupd.poop_ballon.Workers.Shaker;

import static com.badlogic.gdx.math.MathUtils.random;
import static ru.asupd.poop_ballon.States.PlayState.ANIMATION_TIME;

/**
 * Шарик)
 * Created by Voland on 04.08.2017.
 */

public class Balloon {
    private Vector3 position;
    private Vector3 velosity;
    private Rectangle bounds;
    //private Texture texture_pooped,texture_bloody;
    //private Texture texture;

    private Animation animation_current_balloon;
    private Texture poof_balloon_atlas;

    private int color_of_balloon;
    private float currentTime;
    private float currentTime_or=0;
    private boolean pooped,live_out;
    private boolean can_respawn,sin_grav_bool;

    private int max_combo;

    public void setMax_combo(int max_combo) {
        this.max_combo = max_combo;
    }

    private boolean combo=false;//Шар учавствует в комбо
    private int combo_number=0;//номер шара в комбо
    private boolean make_orange=false;//для оьбработки задержи перед изменением цвета в комбо

    public int getColor_of_balloon() {
        return color_of_balloon;
    }

    public void setVelosity(int grav) {

        this.velosity.y = grav;
    }
    public boolean isPooped() {
        //if pooped
        return pooped|make_orange;
    }

    public void setPooped() {

        if (combo){
           // color_of_balloon=10;
            currentTime_or=0.08f*combo_number;
            combo=false;
           // System.out.println("Current_time: "+currentTime);
            make_orange=true;
        }
        else{
            color_of_balloon+=5;
            this.pooped = true;
        }


    }

    //public int getCombo() {
     //   return combo_number;
    //}

    public void setCombo(int number_of_ball_in_combo){
        if (number_of_ball_in_combo!=0) {
            combo = true;
            combo_number = number_of_ball_in_combo;

        }else
        {
            combo = false;
        }

    }


    public Balloon(int x, int y, int grav, boolean respawn){
        position = new Vector3(x, y, 0);
        velosity = new Vector3(random(100)-50, 0, 0);

        poof_balloon_atlas = new Texture("pop_o.png");

        animation_current_balloon = new Animation(new TextureRegion(poof_balloon_atlas),3,ANIMATION_TIME);

        // zerovelosity = new Vector3(0, 0, 0);
        this.velosity.y = grav;
        pooped=false;
        live_out=false;
        can_respawn=respawn;
        sin_grav_bool=random.nextBoolean();
        currentTime=0;
        // texture_pooped=new Texture("blow.png");
        // texture_bloody=new Texture("Blood_Splatter.png");

        //color_of_balloon=random(3);
        if (grav<260){
            color_of_balloon=0;
        }else
        if (grav<320){
            color_of_balloon=1;
        }
        else
        if (grav<380){
            color_of_balloon=2;
        }
        else
        if(grav<420){
            color_of_balloon=3;
        }
        else{
            color_of_balloon=4;
        }
        bounds = new Rectangle(x, y, 95 , 190);
    }

    public Vector3 getPosition() {
        return position;
    }

    //public Texture getTexture() {
    //    return texture;
    //}

    public void setPosition(float x,float y) {
        this.position.x = x;
        this.position.y = y;
        animation_current_balloon.setCurrentFrameTime(0.0f);
    }
    public TextureRegion getFrames(){
        return animation_current_balloon.getFrames();
    }

    public boolean isLive_out() {
        return live_out;
    }

    public void update(float dt, Shaker shaker){

    if (make_orange){
        currentTime_or+=dt;
        if (currentTime_or>=0.08f*max_combo){
            //System.out.println("Combo_number: "+combo_number);
        //if (currentTime_or>=1.0f){
            make_orange=false;
            shaker.inc();
            color_of_balloon=10;
            combo=true;

            this.pooped = true;
        }
    }

        int sin_grav = 50;
        if ((velosity.x< sin_grav)&(sin_grav_bool)){
       velosity.x++;
   }
   if ((velosity.x>-sin_grav)&(!sin_grav_bool)){
       velosity.x--;
   }

   if (velosity.x> sin_grav){
       sin_grav_bool=false;
   }
   if (velosity.x<-sin_grav){
       sin_grav_bool=true;
   }


   if (pooped){
        currentTime+=dt;
       if (combo_number>=1) {
           animation_current_balloon.update(dt);
       }
       if (currentTime>ANIMATION_TIME)
       {
           if ((!combo)&(!make_orange)) {
               live_out = true;
               animation_current_balloon.dispose();
               poof_balloon_atlas.dispose();
           }else
            {
                combo = false;
                currentTime = 0;
               // animation_current_balloon.update(dt);
                color_of_balloon++;
           }
       }

   }
   else {
       if (can_respawn) {
           velosity.scl(dt);
           position.add(velosity.x, velosity.y, 0);
           if (position.x>390){position.x=390;}
           if (position.x<0  ){position.x=0;}
           velosity.scl(1 / dt);

       }
            bounds.setPosition(position.x, position.y);
        }
    }

   // public Rectangle getBounds(){
    //    return bounds;
   // }

    public void dispose() {
       // System.out.println("Balloon disposed");
    }
    public void stop_spawn(){
        can_respawn=false;
    }
    public void start_spawn(){
        can_respawn=true;
    }

}

