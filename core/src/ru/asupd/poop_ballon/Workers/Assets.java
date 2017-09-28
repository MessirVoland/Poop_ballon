package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.asupd.poop_ballon.Sprites.Animation;

/**
 * Created by Voland on 28.09.2017.
 */

public class Assets {
    public static final AssetManager manager = new AssetManager();

    public static final String poof_balloon_atlas_idle_o = "pop_o.png";
   // public static final String poof_balloon_atlas_o_Texture_region = "pop_o.png";
    public static final String poof_balloon_atlas_idle_g = "pop_g.png";
    public static final String poof_balloon_atlas_idle_b = "pop_b.png";
    public static final String poof_balloon_atlas_idle_y = "pop_y.png";
    public static final String poof_balloon_atlas_idle_r = "pop_r.png";
    public static final String poof_balloon_atlas_idle_p = "pop_p.png";
    public static final String balloon_blue ="Balloon_blue.png";
    public static final String balloon_green ="Balloon_green.png";
    public static final String balloon_yellow ="Balloon_yellow.png";
    public static final String balloon_red ="Balloon_red.png";
    public static final String balloon_purple ="Balloon_purple.png";
    public static final String balloon_orange ="round_b_o.png";

    public static final ParticleEffect effect = new ParticleEffect();



    public static void load(){

        manager.load(poof_balloon_atlas_idle_o, Texture.class);
        manager.load(poof_balloon_atlas_idle_g,Texture.class);
        manager.load(poof_balloon_atlas_idle_b,Texture.class);
        manager.load(poof_balloon_atlas_idle_y,Texture.class);
        manager.load(poof_balloon_atlas_idle_r,Texture.class);
        manager.load(poof_balloon_atlas_idle_p,Texture.class);

        manager.load(balloon_green,Texture.class);
        manager.load(balloon_yellow,Texture.class);
        manager.load(balloon_blue,Texture.class);
        manager.load(balloon_red,Texture.class);
        manager.load(balloon_purple,Texture.class);
        manager.load(balloon_orange,Texture.class);

        //manager.load(poof_balloon_atlas_o_Texture_region, TextureRegion.class);
        //effect = new ParticleEffect();
        effect.loadEmitters(Gdx.files.internal("particles/pop_b"));
        effect.loadEmitterImages(Gdx.files.internal("particles"));


    }
    public static void dispose(){
        manager.dispose();
    }
}
