package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import ru.asupd.poop_ballon.Sprites.Animation;

/**
 * Created by Voland on 28.09.2017.
 */

public class Assets implements Disposable, AssetErrorListener {

    public static final Assets instance = new Assets();
    public AssetManager manager = new AssetManager();

    //шары и анимация шаров
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

    //облачка
    public static final AssetDescriptor<Texture> cloud1 = new AssetDescriptor<Texture>("cloud1.png",Texture.class);
    public static final AssetDescriptor<Texture> cloud2 = new AssetDescriptor<Texture>("cloud2.png",Texture.class);
    public static final AssetDescriptor<Texture> cloud3 = new AssetDescriptor<Texture>("cloud3.png",Texture.class);
    public static final AssetDescriptor<Texture> cloud4 = new AssetDescriptor<Texture>("cloud4.png",Texture.class);

    //звезды
    public static final AssetDescriptor<Texture> star1 = new AssetDescriptor<Texture>("st1.png",Texture.class);
    public static final AssetDescriptor<Texture> star2 = new AssetDescriptor<Texture>("st2.png",Texture.class);
    public static final AssetDescriptor<Texture> star3 = new AssetDescriptor<Texture>("st3.png",Texture.class);
    public static final AssetDescriptor<Texture> star4 = new AssetDescriptor<Texture>("st4.png",Texture.class);
    public static final AssetDescriptor<Texture> star5 = new AssetDescriptor<Texture>("st5.png",Texture.class);
    public static final AssetDescriptor<Texture> star6 = new AssetDescriptor<Texture>("st6.png",Texture.class);
    public static final AssetDescriptor<Texture> star7 = new AssetDescriptor<Texture>("st7.png",Texture.class);

    //единичные объекты
    public static final AssetDescriptor<Texture> heart_baloon = new AssetDescriptor<Texture>("heart_baloon.png",Texture.class);
    public static final AssetDescriptor<Texture> heart_baloon_hole1 = new AssetDescriptor<Texture>("heart_baloon_hole1.png",Texture.class);
    public static final AssetDescriptor<Texture> heart_baloon_hole2 = new AssetDescriptor<Texture>("heart_baloon_hole2.png",Texture.class);
    public static final AssetDescriptor<Texture> heart_baloon_anim = new AssetDescriptor<Texture>("pop_HB.png",Texture.class);

    //нестандартные шары
    public static final AssetDescriptor<Texture> balloon_wooden = new AssetDescriptor<Texture>("wood_b.png",Texture.class);
    public static final String anim_balloon_wooden = "pop_o.png";
    public static final AssetDescriptor<Texture> balloon_stone = new AssetDescriptor<Texture>("stone_b.png",Texture.class);
    //медали
    public static final AssetDescriptor<Texture> medal_wooden = new AssetDescriptor<Texture>("medal_w.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_stone = new AssetDescriptor<Texture>("medal_s.png",Texture.class);

    public static final AssetDescriptor<Texture> back_ground_atlas = new AssetDescriptor<Texture>("bacgound_atlas.png",Texture.class);

    //Меню
    public static final AssetDescriptor<Texture> restart_ico = new AssetDescriptor<Texture>("restart_ico.png",Texture.class);
    public static final AssetDescriptor<Texture> pause_button = new AssetDescriptor<Texture>("pause_but.png",Texture.class);

    public static final AssetDescriptor<Music> background_Music = new AssetDescriptor<Music>("sound.mp3",Music.class);


    //Частицы
    //public static final String Particles_of_balloon_g="particles/pop_b";

    public static ParticleEffect effect_green = new ParticleEffect();
    public static ParticleEffect effect_yellow = new ParticleEffect();
    public static ParticleEffect effect_blue = new ParticleEffect();
    public static ParticleEffect effect_red = new ParticleEffect();
    public static ParticleEffect effect_purple = new ParticleEffect();
    public static ParticleEffect effect_orange = new ParticleEffect();

    public static ParticleEffect combo_2x = new ParticleEffect();
    public static ParticleEffect combo_3x = new ParticleEffect();
    public static ParticleEffect combo_4x = new ParticleEffect();
    public static ParticleEffect combo_5x = new ParticleEffect();
    public static ParticleEffect combo_6x = new ParticleEffect();
    //public static ParticleEffect combo_7x = new ParticleEffect();
    public static ParticleEffect hearth_ballon_part = new ParticleEffect();
    public static ParticleEffect hearth_ballon_part_hole1 = new ParticleEffect();
    public static ParticleEffect hearth_ballon_part_hole2 = new ParticleEffect();
    public static ParticleEffect hearth_ballon_part_heart = new ParticleEffect();


    //private AssetManager assetManager;




    /*public static void loadParticleEffects(){
        ParticleEffectLoader.ParticleEffectParameter pep = new ParticleEffectLoader.ParticleEffectParameter();
        pep.atlasFile = "assets/particles";
        manager.load(Particles_of_balloon_g,ParticleEffect.class,pep);
    }*/
    public void load(AssetManager assetManager){
        this.manager = assetManager;
        assetManager.setErrorListener(this);

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

        manager.load(cloud1);
        manager.load(cloud2);
        manager.load(cloud3);
        manager.load(cloud4);

        manager.load(star1);
        manager.load(star2);
        manager.load(star3);
        manager.load(star4);
        manager.load(star5);
        manager.load(star6);
        manager.load(star7);

        manager.load(heart_baloon);
        manager.load(heart_baloon_hole1);
        manager.load(heart_baloon_hole2);
        manager.load(heart_baloon_anim);

        manager.load(balloon_wooden);
        manager.load(balloon_stone);

        manager.load(medal_wooden);
        manager.load(medal_stone);

        manager.load(back_ground_atlas);

        manager.load(restart_ico);
        manager.load(pause_button);


        manager.load(background_Music);

        //manager.load(poof_balloon_atlas_o_Texture_region, TextureRegion.class);
        //effect = new ParticleEffect();
        effect_green.loadEmitters(Gdx.files.internal("particles/pop_green"));
        effect_yellow.loadEmitters(Gdx.files.internal("particles/pop_yellow"));
        effect_blue.loadEmitters(Gdx.files.internal("particles/pop_blue"));
        effect_red.loadEmitters(Gdx.files.internal("particles/pop_red"));
        effect_purple.loadEmitters(Gdx.files.internal("particles/pop_perp"));
        effect_orange.loadEmitters(Gdx.files.internal("particles/pop_orange"));

        effect_green.loadEmitterImages(Gdx.files.internal("particles"));
        effect_yellow.loadEmitterImages(Gdx.files.internal("particles"));
        effect_blue.loadEmitterImages(Gdx.files.internal("particles"));
        effect_red.loadEmitterImages(Gdx.files.internal("particles"));
        effect_purple.loadEmitterImages(Gdx.files.internal("particles"));
        effect_orange.loadEmitterImages(Gdx.files.internal("particles"));

        combo_2x.loadEmitters(Gdx.files.internal("particles/2x_combo"));
        combo_3x.loadEmitters(Gdx.files.internal("particles/3x_combo"));
        combo_4x.loadEmitters(Gdx.files.internal("particles/4x_combo"));
        combo_5x.loadEmitters(Gdx.files.internal("particles/5x_combo"));
        combo_6x.loadEmitters(Gdx.files.internal("particles/6x_combo"));
        //combo_7x.loadEmitters(Gdx.files.internal("particles/7x_combo"));

        combo_2x.loadEmitterImages(Gdx.files.internal("particles"));
        combo_3x.loadEmitterImages(Gdx.files.internal("particles"));
        combo_4x.loadEmitterImages(Gdx.files.internal("particles"));
        combo_5x.loadEmitterImages(Gdx.files.internal("particles"));
        combo_6x.loadEmitterImages(Gdx.files.internal("particles"));
        //combo_7x.loadEmitterImages(Gdx.files.internal("particles/pop_green"));

        hearth_ballon_part.load(Gdx.files.internal("particles/plus5"),Gdx.files.internal("particles"));
        hearth_ballon_part_hole1.load(Gdx.files.internal("particles/heart_hole1"),Gdx.files.internal("particles"));
        hearth_ballon_part_hole2.load(Gdx.files.internal("particles/heart_hole2"),Gdx.files.internal("particles"));
        hearth_ballon_part_heart.load(Gdx.files.internal("particles/heart"),Gdx.files.internal("particles"));




        //assetManager.finishLoading();


    }
    public void dispose(){
        //try {
        //System.exit(1);
         //   manager.dispose();
          //  instance.dispose();
        //} catch (Throwable t){
        //    System.out.println("Smm: "+t );
       // }
    }
    public static void make_linear(){
        Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_o,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_g,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_b,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_y,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_r,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_p,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Assets.instance.manager.get(Assets.balloon_green,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_yellow,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_blue,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_red,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_purple,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_orange,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Assets.instance.manager.get(Assets.cloud1).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.cloud2).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.cloud3).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.cloud4).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Assets.instance.manager.get(Assets.heart_baloon).setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);






    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }
}
