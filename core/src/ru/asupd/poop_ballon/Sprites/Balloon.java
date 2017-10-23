package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;
import ru.asupd.poop_ballon.States.PlayState;
import ru.asupd.poop_ballon.Workers.Assets;
import ru.asupd.poop_ballon.Workers.Shaker;
import static com.badlogic.gdx.math.MathUtils.random;
import static ru.asupd.poop_ballon.States.PlayState.CHANSE_OF_WOODEN_BALLOON;
import static ru.asupd.poop_ballon.States.PlayState.ANIMATION_TIME;
import static ru.asupd.poop_ballon.States.PlayState.MEDAL_SCORE;
import static ru.asupd.poop_ballon.States.PlayState.balloons_manager;

/**
 * Шарик)
 * Created by Voland on 04.08.2017.
 */
public class Balloon {
    private Sprite sprite;
    private Vector3 position;
    private Vector3 velosity;
    private static final Random rand = new Random();

    private short color_of_balloon;
    private float currentTime;
    private float currentTime_or=0;
    private boolean pooped,live_out;
    private boolean can_respawn,sin_grav_bool;
    private boolean part_start=true;

    private boolean N_ST_color=false;
    private boolean wooden_color=false;
    private int number_of_n_st;

    private ParticleEffect effect;
    public ParticleEffect effect_gold;

    private short max_combo;
    private boolean with_combo_multi=false;


    private boolean combo=false;//Шар учавствует в комбо
    private int combo_number=0;//номер шара в комбо
    private boolean make_orange=false;//для оьбработки задержи перед изменением цвета в комбо

    public Balloon(int x, int y, int grav, boolean respawn) {

        //Позиция и вектор ускорения
        position = new Vector3(x, y, 0);
        velosity = new Vector3(random(100) - 50, grav, 0);

        //Генерация нестандартного шара
        if ((PlayState.settings.hi_score()-500)>=MEDAL_SCORE) {
            if (rand.nextInt(99) < CHANSE_OF_WOODEN_BALLOON-balloons_manager.getCurrent_difficult_up()+1) {
                N_ST_color = true;
            }
        }

        pooped = false;
        live_out = false;
        can_respawn = respawn;
        sin_grav_bool = rand.nextBoolean();
        currentTime = 0;

        if (!N_ST_color) {
            if (grav < 260) {
                color_of_balloon = 0;
                sprite = new Sprite(Assets.instance.manager.get(Assets.balloon_green, Texture.class));
                sprite.setPosition(position.x,position.y);
                effect = new ParticleEffect(Assets.effect_green);
            }
            else if (grav < 320) {
                color_of_balloon = 1;
                sprite = new Sprite(Assets.instance.manager.get(Assets.balloon_yellow, Texture.class));
                sprite.setPosition(position.x,position.y);
                effect = new ParticleEffect(Assets.effect_yellow);
            }
            else if (grav < 380) {
                color_of_balloon = 2;
                sprite = new Sprite(Assets.instance.manager.get(Assets.balloon_blue, Texture.class));
                sprite.setPosition(position.x,position.y);
                effect = new ParticleEffect(Assets.effect_blue);
            }
            else if (grav < 420) {
                color_of_balloon = 3;
                sprite = new Sprite(Assets.instance.manager.get(Assets.balloon_red, Texture.class));
                sprite.setPosition(position.x,position.y);
                effect = new ParticleEffect(Assets.effect_red);
            }
            else {
                color_of_balloon = 4;
                sprite = new Sprite(Assets.instance.manager.get(Assets.balloon_purple, Texture.class));
                sprite.setPosition(position.x,position.y);
                effect = new ParticleEffect(Assets.effect_purple);
            }
           }
        else
        {
            color_of_balloon = 12;
            switch ((PlayState.settings.hi_score()-500)/MEDAL_SCORE) {
                case 0:
                    break;
                case 1:
                    effect = new ParticleEffect(Assets.ballon_n_st_ice);
                    break;
                case 2:
                    effect = new ParticleEffect(Assets.ballon_n_st_wood);
                    break;
                case 3:
                    effect = new ParticleEffect(Assets.ballon_n_st_stone);
                    break;
                case 4:
                    effect = new ParticleEffect(Assets.ballon_n_st_kript);
                    effect_gold = new ParticleEffect(Assets.gold_stars);
                    break;
                case 5:
                    effect = new ParticleEffect(Assets.ballon_n_st_initblue);
                    effect_gold = new ParticleEffect(Assets.gold_stars);
                    break;
                case 6:
                    effect = new ParticleEffect(Assets.ballon_n_st_pheon);
                    effect_gold = new ParticleEffect(Assets.gold_stars);
                    break;
                case 7:
                    effect = new ParticleEffect(Assets.ballon_n_st_bronze);
                    effect_gold = new ParticleEffect(Assets.gold_stars);
                    break;
                case 8:
                    effect = new ParticleEffect(Assets.ballon_n_st_silver);
                    effect_gold = new ParticleEffect(Assets.gold_stars);
                    break;
                default:
                    effect = new ParticleEffect(Assets.ballon_n_st_gold);
                    effect_gold = new ParticleEffect(Assets.gold_stars);
                    break;
            }
            //animation_idle = new Animation(new TextureRegion(Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_p, Texture.class)), 3, ANIMATION_TIME);
            // animation_current_balloon = new Animation(new TextureRegion(Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_o, Texture.class)), 3, ANIMATION_TIME);
            if (effect_gold!=null) {
                effect_gold.setPosition(position.x + 45, position.y + 45);
                effect_gold.start();
            }

        }

    }
    public void setNumber_of_n_st(int number_of_n_st) {
        this.number_of_n_st = number_of_n_st;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setMax_combo(short max_combo) {
        this.max_combo = max_combo;
    }

    public void setWooden_color(boolean wooden_color) {
        this.wooden_color = wooden_color;
    }

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
    public void start_combo_part(){
        with_combo_multi=true;
    }

    public void setPooped() {
        if (effect_gold!=null){
            effect_gold.dispose();
        }
        if (combo){
           // color_of_balloon=10;
            currentTime_or=0.08f*combo_number;
            combo=false;
           // System.out.println("Current_time: "+currentTime);
            make_orange=true;
            if (!N_ST_color) {
                effect = new ParticleEffect(Assets.effect_orange);
            }else
                switch (PlayState.settings.hi_score()/MEDAL_SCORE) {
                    case 0:
                        break;
                    case 1:
                        effect = new ParticleEffect(Assets.ballon_n_st_ice);
                        break;
                    case 2:
                        effect = new ParticleEffect(Assets.ballon_n_st_wood);
                        break;
                    case 3:
                        effect = new ParticleEffect(Assets.ballon_n_st_stone);
                        break;
                    case 4:
                        effect = new ParticleEffect(Assets.ballon_n_st_kript);
                        break;
                    case 5:
                        effect = new ParticleEffect(Assets.ballon_n_st_initblue);
                        break;
                    case 6:
                        effect = new ParticleEffect(Assets.ballon_n_st_pheon);
                        break;
                    case 7:
                        effect = new ParticleEffect(Assets.ballon_n_st_bronze);
                        break;
                    case 8:
                        effect = new ParticleEffect(Assets.ballon_n_st_silver);
                        break;
                    default:
                        effect = new ParticleEffect(Assets.ballon_n_st_gold);
                        break;
                }

        }
        else{
            if (N_ST_color){
                color_of_balloon++;
            }else {
                color_of_balloon += 5;
            }
            this.pooped = true;
        }


    }

    //public int getCombo() {
     //   return combo_number;
    //}

    public void setCombo(int number_of_ball_in_combo){
        if (number_of_ball_in_combo!=0) {
            combo = true;
            with_combo_multi=false;
            combo_number = number_of_ball_in_combo;

        }else
        {
            combo = false;
        }

    }


    public boolean isN_ST_color() {
        return N_ST_color;
    }

    public  Vector3 getPosition() {
        return position;
    }

    //public Texture getTexture() {
    //    return texture;
    //}

    public void setPosition(float x,float y) {
        this.position.x = x;
        this.position.y = y;
        //animation_current_balloon.setCurrentFrameTime(0.0f);
    }
    //public TextureRegion getFrames(){
       // return animation_current_balloon.getFrames();
   // }
    //public TextureRegion getFrames_idle(){
        //return animation_idle.getFrames();
    //}

    public boolean isLive_out() {
        return live_out;
    }
    public void part_start(){
        if (part_start) {
           // effect.start();
            switch (number_of_n_st) {
                case 0:
                    break;
                case 1:
                    effect = new ParticleEffect(Assets.ballon_n_st_ice);
                    break;
                case 2:
                    effect = new ParticleEffect(Assets.ballon_n_st_wood);
                    break;
                case 3:
                    effect = new ParticleEffect(Assets.ballon_n_st_stone);
                    break;
                case 4:
                    effect = new ParticleEffect(Assets.ballon_n_st_kript);
                    break;
                case 5:
                    effect = new ParticleEffect(Assets.ballon_n_st_initblue);
                    break;
                case 6:
                    effect = new ParticleEffect(Assets.ballon_n_st_pheon);
                    break;
                case 7:
                    effect = new ParticleEffect(Assets.ballon_n_st_bronze);
                    break;
                case 8:
                    effect = new ParticleEffect(Assets.ballon_n_st_silver);
                    break;
                default:
                    effect = new ParticleEffect(Assets.ballon_n_st_gold);
                    break;
            }
            PlayState.combo_effects.add(new ParticleEffect(effect));
            PlayState.combo_effects.get(PlayState.combo_effects.size - 1).start();
            PlayState.combo_effects.get(PlayState.combo_effects.size - 1).setPosition(position.x + 45, position.y + 145);

          //  effect.setPosition(position.x + 45, position.y + 145);
            part_start=false;
        }
    }

    //public void setAnimation_idle(Animation animation_idle) {
        //this.animation_idle = animation_idle;
    //}

  //  public boolean isAnim_end() {
  //      return anim_end;
  //  }
    /*public void update_part(float dt){
        effect.update(dt);
    }*/

    public void update(float dt, Shaker shaker) {
        //Зололые звездочки
        if (effect_gold != null) {
            effect_gold.update(dt);
            effect_gold.setPosition(position.x + 45, position.y + 45);
        }

        //Групповое изменение цвета в комбо
        if (make_orange) {
            currentTime_or += dt;
            if (currentTime_or >= 0.08f * max_combo) {
                //System.out.println("Combo_number: "+combo_number);
                //if (currentTime_or>=1.0f){
                make_orange = false;
                shaker.inc();
                if (wooden_color) {
                    color_of_balloon = 12;
                } else {
                    color_of_balloon = 10;
                }
                combo = true;
                this.pooped = true;
            }
        }

        int sin_grav = 50;
        if ((velosity.x < sin_grav) & (sin_grav_bool)) {
            velosity.x++;
        }
        if ((velosity.x > -sin_grav) & (!sin_grav_bool)) {
            velosity.x--;
        }

        if (velosity.x > sin_grav) {
            sin_grav_bool = false;
        }
        if (velosity.x < -sin_grav) {
            sin_grav_bool = true;
        }


        if (pooped) {
            effect.update(dt);
            currentTime += dt;
            if (currentTime > ANIMATION_TIME) {
                if ((!combo) & (!make_orange)) {
                    live_out = true;

                } else

                {
                    if (with_combo_multi) {
                        //System.out.println(max_combo);
                        switch (max_combo) {
                            default:
                                PlayState.combo_effects.add(new ParticleEffect(Assets.combo_6x));
                                break;
                            case 2:
                                PlayState.combo_effects.add(new ParticleEffect(Assets.combo_2x));
                                break;
                            case 3:
                                PlayState.combo_effects.add(new ParticleEffect(Assets.combo_3x));
                                break;
                            case 4:
                                PlayState.combo_effects.add(new ParticleEffect(Assets.combo_4x));
                                break;
                            case 5:
                                PlayState.combo_effects.add(new ParticleEffect(Assets.combo_5x));
                                break;
                            case 6:
                                PlayState.combo_effects.add(new ParticleEffect(Assets.combo_6x));
                                break;
                        }
                        PlayState.combo_effects.get(PlayState.combo_effects.size - 1).start();
                        PlayState.combo_effects.get(PlayState.combo_effects.size - 1).setPosition(position.x + 45, position.y + 145);
                        //System.out.println("Combo started");
                        with_combo_multi = false;
                    }

                    combo = false;
                    currentTime = 0;
                    // animation_current_balloon.update(dt);
                    //System.out.println(color_of_balloon);
                    color_of_balloon++;
                }
            }

        } else {
            if (can_respawn) {
                velosity.scl(dt);
                position.add(velosity.x, velosity.y, 0);
                if (sprite!=null){
                    sprite.setPosition(position.x,position.y);
                }
                if (position.x > 390) {
                    position.x = 390;
                }
                if (position.x < 0) {
                    position.x = 0;
                }
                velosity.scl(1 / dt);
            }
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

