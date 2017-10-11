package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import ru.asupd.poop_ballon.States.PlayState;
import ru.asupd.poop_ballon.Workers.Assets;

import static ru.asupd.poop_ballon.States.PlayState.ANIMATION_TIME;

/**
 * Created by Voland on 01.10.2017.
 */

public class Hearth_balloon extends Creature {
    private Vector3 position;
    private Vector3 velosity;
    private boolean fly=false;
    private Animation animation;
    boolean pooped=false;
    private int clicks;
    private float current_time_dt=0.0f;

    public boolean isCan_fly() {
        return can_fly;
    }

    private boolean can_fly=true;

    private boolean sin_grav_bool;
    ParticleEffect effect1,effect2;
    ParticleEffect effect;
    Texture texture;
    boolean dispose=false;

    public Hearth_balloon() {
        dispose=false;
        position = new Vector3(-95,400-84,0);
        velosity = new Vector3(80,0,0);
        texture = Assets.instance.manager.get(Assets.heart_baloon);
        animation = new Animation(new TextureRegion(Assets.instance.manager.get(Assets.heart_baloon_anim)),3,ANIMATION_TIME);
        //animation = new Animation(new TextureRegion(Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_o,Texture.class)),3,ANIMATION_TIME);
        clicks=0;

        pooped=false;

    }

    public boolean isFly() {
        return fly;
    }

    public int getClicks() {
        return clicks;
    }

    public void clicked(){
        clicks++;
        if (clicks==1)
        {
            texture = Assets.instance.manager.get(Assets.heart_baloon_hole1);
            effect1 = new ParticleEffect();
            effect1.load(Gdx.files.internal("particles/heart_hole1"),Gdx.files.internal("particles"));
            PlayState.combo_effects.add(effect1);
            PlayState.combo_effects.get(PlayState.combo_effects.size-1).setPosition(position.x,position.y);
            PlayState.combo_effects.get(PlayState.combo_effects.size-1).start();
        }
        if (clicks==2)
        {
            texture = Assets.instance.manager.get(Assets.heart_baloon_hole2);
            effect2 = new ParticleEffect();
            effect2.load(Gdx.files.internal("particles/heart_hole2"),Gdx.files.internal("particles"));
            PlayState.combo_effects.add(effect2);
            PlayState.combo_effects.get(PlayState.combo_effects.size-1).setPosition(position.x,position.y);
            PlayState.combo_effects.get(PlayState.combo_effects.size-1).start();
        }


        //+5

        ParticleEffect effect = Assets.hearth_ballon_part;
        PlayState.combo_effects.add(new ParticleEffect(effect));
        PlayState.combo_effects.get(PlayState.combo_effects.size-1).setPosition(position.x+45,position.y+145);
        PlayState.combo_effects.get(PlayState.combo_effects.size-1).start();
        PlayState.score_num.addScore(5);



    }
    public void restart(){
        texture = Assets.instance.manager.get(Assets.heart_baloon);
        ParticleEffect effect = Assets.hearth_ballon_part_heart;
        PlayState.combo_effects.add(new ParticleEffect(effect));
        PlayState.combo_effects.get(PlayState.combo_effects.size-1).setPosition(position.x+45,position.y+145);
        PlayState.combo_effects.get(PlayState.combo_effects.size-1).start();
        position.x=-95;
        clicks=0;
        pooped=false;
    }
    public void remove(){
        texture = Assets.instance.manager.get(Assets.heart_baloon);
       // ParticleEffect effect = Assets.hearth_ballon_part_heart;
       // PlayState.combo_effects.add(new ParticleEffect(effect));
       // PlayState.combo_effects.get(PlayState.combo_effects.size-1).setPosition(position.x+45,position.y+145);
       // PlayState.combo_effects.get(PlayState.combo_effects.size-1).start();
        position.x=-95;
        clicks=0;
        pooped=false;
    }

    public Vector3 getPosition() {

        return position;
    }
    public void setPooped() {
        //pop_HB.png
        pooped=true;
        animation.setCurrentFrameTime(0);
        animation.setFrame(0);
        current_time_dt=0;

        effect1.dispose();
        effect2.dispose();
    }

    public boolean isPooped() {
        return pooped;
    }

    public boolean isDispose() {
        return dispose;
    }

    @Override
    public void update(float dt) {
        if (pooped){
            //System.out.println(position.x +" "+position.y);

            current_time_dt+=dt;
            animation.update(dt);
            if (current_time_dt>=ANIMATION_TIME){
                restart();
                dispose=true;
                current_time_dt=0;

            }

        }else {
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

            velosity.scl(dt);
            position.add(velosity.x, velosity.y, 0);
            velosity.scl(1 / dt);
            if (clicks>=1) {
                effect1.setPosition(position.x+10 , position.y+88 );
            }
            if (clicks==2) {
                effect2.setPosition(position.x+65 , position.y+58 );
            }
        }
    }

    public void setCan_fly(boolean can_fly) {
        this.can_fly = can_fly;
    }

    public void setFly(boolean fly) {
        if (fly){
            can_fly=false;

        }
        this.fly = fly;
    }

    public TextureRegion getTexture() {
        if (pooped){
            return animation.getFrames();
        }else {
            return new TextureRegion(texture);
        }
    }

    @Override
    public void dispose() {
        if (effect1!=null) {
            effect1.dispose();
        }
        if (effect2!=null) {
            effect2.dispose();
        }
    }
}
