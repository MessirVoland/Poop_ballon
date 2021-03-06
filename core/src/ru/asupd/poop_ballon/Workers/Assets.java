package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Disposable;

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

    //Запчасти экарана Play State
    public static final AssetDescriptor<Texture> pause_bgnd = new AssetDescriptor<Texture>("pause.png",Texture.class);
    public static final AssetDescriptor<Texture> your_high_score = new AssetDescriptor<Texture>("best_score_g.png",Texture.class);
    public static final AssetDescriptor<Texture> texture_poop_balloon = new AssetDescriptor<Texture>("poop_balloon.png",Texture.class);
    public static final AssetDescriptor<Texture> tap_to_play = new AssetDescriptor<Texture>("tap_to_play.png",Texture.class);
    public static final AssetDescriptor<Texture> big_balloon = new AssetDescriptor<Texture>("big_balloon.png",Texture.class);
    public static final AssetDescriptor<Texture> score = new AssetDescriptor<Texture>("score.png",Texture.class);

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
    public static final AssetDescriptor<Texture> balloon_ns_x2 = new AssetDescriptor<Texture>("b_x2.png",Texture.class);
    //public static final String anim_balloon_wooden = "pop_o.png";
    public static final AssetDescriptor<Texture> balloon_ice = new AssetDescriptor<Texture>("b_x3.png",Texture.class);
    public static final AssetDescriptor<Texture> balloon_stone = new AssetDescriptor<Texture>("b_x4.png",Texture.class);
    public static final AssetDescriptor<Texture> balloon_kript = new AssetDescriptor<Texture>("b_x5.png",Texture.class);
    public static final AssetDescriptor<Texture> balloon_blueinit = new AssetDescriptor<Texture>("b_x6.png",Texture.class);
    public static final AssetDescriptor<Texture> balloon_pheon = new AssetDescriptor<Texture>("b_x7.png",Texture.class);
    public static final AssetDescriptor<Texture> balloon_bronze = new AssetDescriptor<Texture>("b_x8.png",Texture.class);
    public static final AssetDescriptor<Texture> balloon_silver = new AssetDescriptor<Texture>("b_x9.png",Texture.class);
    public static final AssetDescriptor<Texture> balloon_ns_x10 = new AssetDescriptor<Texture>("b_x10.png",Texture.class);

    public static final AssetDescriptor<Texture> bomb_balloon_t = new AssetDescriptor<Texture>("bomb_balloon.png",Texture.class);
    public static final AssetDescriptor<Texture> bomb_balloon_tt = new AssetDescriptor<Texture>("bomb_balloon1.png",Texture.class);

    public static ParticleEffect fire_bomb1= new ParticleEffect();
    public static ParticleEffect fire_bomb2= new ParticleEffect();
    public static ParticleEffect bomb_blow= new ParticleEffect();
    public static ParticleEffect miss_ball= new ParticleEffect();

    //медали
    public static final AssetDescriptor<Texture> medal_ice = new AssetDescriptor<Texture>("medal_x2.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_wooden = new AssetDescriptor<Texture>("medal_x3.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_stone = new AssetDescriptor<Texture>("medal_x4.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_kript = new AssetDescriptor<Texture>("medal_x5.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_blueinit = new AssetDescriptor<Texture>("medal_x6.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_pheon = new AssetDescriptor<Texture>("medal_x7.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_bronze = new AssetDescriptor<Texture>("medal_x8.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_silver = new AssetDescriptor<Texture>("medal_x9.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_gold = new AssetDescriptor<Texture>("medal_x10.png",Texture.class);



    public static final AssetDescriptor<Texture> medal_w_ice =    new AssetDescriptor<Texture>("medal_emty_x2.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_w_wooden = new AssetDescriptor<Texture>("medal_emty_x3.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_w_stone =  new AssetDescriptor<Texture>("medal_emty_x4.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_w_kript =  new AssetDescriptor<Texture>("medal_emty_x5.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_w_blueinit=new AssetDescriptor<Texture>("medal_emty_x6.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_w_pheon =  new AssetDescriptor<Texture>("medal_emty_x7.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_w_bronze = new AssetDescriptor<Texture>("medal_emty_x8.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_w_silver = new AssetDescriptor<Texture>("medal_emty_x9.png",Texture.class);
    public static final AssetDescriptor<Texture> medal_w_gold =   new AssetDescriptor<Texture>("medal_emty_x10.png",Texture.class);

    public static final AssetDescriptor<Texture> back_ground_atlas = new AssetDescriptor<Texture>("bacgound_atlas.png",Texture.class);

    //Меню
    public static final AssetDescriptor<Texture> restart_ico = new AssetDescriptor<Texture>("restart_ico.png",Texture.class);
    public static final AssetDescriptor<Texture> pause_button = new AssetDescriptor<Texture>("pause_but.png",Texture.class);

    public static final AssetDescriptor<Texture> sprt_exit = new AssetDescriptor<Texture>("arrow_bck.png",Texture.class);
    public static final AssetDescriptor<Music> background_Music = new AssetDescriptor<Music>("sound.mp3",Music.class);
    public static final AssetDescriptor<Texture> faq = new AssetDescriptor<Texture>("faq.png",Texture.class);
    public static final AssetDescriptor<Texture> instr_pause_screen = new AssetDescriptor<Texture>("instr_pause_screen.png",Texture.class);
    public static final AssetDescriptor<Texture> plate = new AssetDescriptor<Texture>("plate.png",Texture.class);
    public static final AssetDescriptor<Texture> instr1 = new AssetDescriptor<Texture>("instr1.png",Texture.class);
    public static final AssetDescriptor<Texture> instr2 = new AssetDescriptor<Texture>("instr2.png",Texture.class);
    public static final AssetDescriptor<Texture> instr3 = new AssetDescriptor<Texture>("instr3.png",Texture.class);
    public static final AssetDescriptor<Texture> arrow = new AssetDescriptor<Texture>("arrow.png",Texture.class);


    public static final AssetDescriptor<Texture> button_ads_free= new AssetDescriptor<Texture>("button_ads_free.png",Texture.class);
    public static final AssetDescriptor<Texture> button_vk= new AssetDescriptor<Texture>("button_vk.png",Texture.class);
    public static final AssetDescriptor<Texture> button_fb= new AssetDescriptor<Texture>("button_facebook.png",Texture.class);
    public static final AssetDescriptor<Texture> button_tw= new AssetDescriptor<Texture>("button_twitter.png",Texture.class);




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

    public static ParticleEffect c_3x=new ParticleEffect();
    public static ParticleEffect c_4x=new ParticleEffect();
    public static ParticleEffect c_5x=new ParticleEffect();
    public static ParticleEffect c_6x=new ParticleEffect();

    //public static ParticleEffect combo_7x = new ParticleEffect();
    public static ParticleEffect hearth_ballon_part = new ParticleEffect();
    public static ParticleEffect hearth_ballon_part_hole1 = new ParticleEffect();
    public static ParticleEffect hearth_ballon_part_hole2 = new ParticleEffect();
    public static ParticleEffect hearth_ballon_part_heart = new ParticleEffect();
    public static ParticleEffect hearth_ballon_part_basket = new ParticleEffect();

    //xfcnbws ytcnfylhfnys[ ifhjd
    public static ParticleEffect ballon_n_st_ice= new ParticleEffect();
    public static ParticleEffect ballon_n_st_wood= new ParticleEffect();
    public static ParticleEffect ballon_n_st_stone= new ParticleEffect();
    public static ParticleEffect ballon_n_st_kript= new ParticleEffect();
    public static ParticleEffect ballon_n_st_initblue= new ParticleEffect();
    public static ParticleEffect ballon_n_st_pheon= new ParticleEffect();
    public static ParticleEffect ballon_n_st_bronze= new ParticleEffect();
    public static ParticleEffect ballon_n_st_silver= new ParticleEffect();
    public static ParticleEffect ballon_n_st_gold= new ParticleEffect();
    public static ParticleEffect gold_stars= new ParticleEffect();
    public static ParticleEffect gem_stars= new ParticleEffect();

    public static ParticleEffect score_500_effect_wow= new ParticleEffect();
    public static ParticleEffect score_500_effect_cool= new ParticleEffect();
    public static ParticleEffect score_500_effect_amazing= new ParticleEffect();

    public static ParticleEffect beat_high_score= new ParticleEffect();
    public static ParticleEffect well_play_particle= new ParticleEffect();
    public static ParticleEffect nice_try_particle= new ParticleEffect();

    public static ParticleEffect beam= new ParticleEffect();
    public static ParticleEffect medal_x2= new ParticleEffect();
    public static ParticleEffect medal_x3= new ParticleEffect();
    public static ParticleEffect medal_x4= new ParticleEffect();
    public static ParticleEffect medal_x5= new ParticleEffect();
    public static ParticleEffect medal_x6= new ParticleEffect();
    public static ParticleEffect medal_x7= new ParticleEffect();
    public static ParticleEffect medal_x8= new ParticleEffect();
    public static ParticleEffect medal_x9= new ParticleEffect();

    //Опции
    public static final AssetDescriptor<Texture> plah= new AssetDescriptor<Texture>("plah.png",Texture.class);
    public static final AssetDescriptor<Texture> ico_mute= new AssetDescriptor<Texture>("sound_off.png",Texture.class);
    public static final AssetDescriptor<Texture> ico_unmute= new AssetDescriptor<Texture>("sound_on.png",Texture.class);

    public static final AssetDescriptor<Texture> sprt_vibro= new AssetDescriptor<Texture>("vibro_on.png",Texture.class);
    public static final AssetDescriptor<Texture> sprt_unvibro= new AssetDescriptor<Texture>("vibro_off.png",Texture.class);

    public static final AssetDescriptor<Texture> sprt_music= new AssetDescriptor<Texture>("music_on.png",Texture.class);
    public static final AssetDescriptor<Texture> sprt_unmusic= new AssetDescriptor<Texture>("music_off.png",Texture.class);

    public static final AssetDescriptor<Texture> sprt_achiv= new AssetDescriptor<Texture>("achives_ico.png",Texture.class);
    public static final AssetDescriptor<Texture> leaderboard= new AssetDescriptor<Texture>("high_ico.png",Texture.class);

    public static final AssetDescriptor<Texture> bgnd_white= new AssetDescriptor<Texture>("white_bck.png",Texture.class);

    public static final AssetDescriptor<Texture> game_name = new AssetDescriptor<Texture>("poop_balloon.png",Texture.class);





    //private AssetManager assetManager;




    /*public static void loadParticleEffects(){
        ParticleEffectLoader.ParticleEffectParameter pep = new ParticleEffectLoader.ParticleEffectParameter();
        pep.atlasFile = "assets/particles";
        manager.load(Particles_of_balloon_g,ParticleEffect.class,pep);
    }*/
    public void load(AssetManager assetManager){
        this.manager = assetManager;
        assetManager.setErrorListener(this);

        //manager.load(poof_balloon_atlas_idle_o, Texture.class);
        //manager.load(poof_balloon_atlas_idle_g,Texture.class);
       // manager.load(poof_balloon_atlas_idle_b,Texture.class);
        //manager.load(poof_balloon_atlas_idle_y,Texture.class);
       // manager.load(poof_balloon_atlas_idle_r,Texture.class);
       // manager.load(poof_balloon_atlas_idle_p,Texture.class);

        manager.load(balloon_green,Texture.class);
        manager.load(balloon_yellow,Texture.class);
        manager.load(balloon_blue,Texture.class);
        manager.load(balloon_red,Texture.class);
        manager.load(balloon_purple,Texture.class);
        manager.load(balloon_orange,Texture.class);

        manager.load(pause_bgnd);
        manager.load(tap_to_play);
        manager.load(your_high_score);
        manager.load(texture_poop_balloon);
        manager.load(big_balloon);
        manager.load(score);

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

        manager.load(balloon_ice);
        manager.load(balloon_ns_x2);
        manager.load(balloon_stone);
        manager.load(balloon_kript);
        manager.load(balloon_blueinit);
        manager.load(balloon_pheon);
        manager.load(balloon_bronze);
        manager.load(balloon_silver);
        manager.load(balloon_ns_x10);
        manager.load(bomb_balloon_t);
        manager.load(bomb_balloon_tt);

        manager.load(medal_ice);
        manager.load(medal_wooden);
        manager.load(medal_stone);
        manager.load(medal_kript);
        manager.load(medal_blueinit);
        manager.load(medal_pheon);
        manager.load(medal_bronze);
        manager.load(medal_silver);
        manager.load(medal_gold);

        manager.load(medal_w_ice);
        manager.load(medal_w_wooden);
        manager.load(medal_w_stone);
        manager.load(medal_w_kript);
        manager.load(medal_w_blueinit);
        manager.load(medal_w_pheon);
        manager.load(medal_w_bronze);
        manager.load(medal_w_silver);
        manager.load(medal_w_gold);

        manager.load(back_ground_atlas);

        manager.load(restart_ico);
        manager.load(pause_button);


        manager.load(background_Music);
        manager.load(sprt_exit);
        manager.load(faq);
        manager.load(instr_pause_screen);
        manager.load(plate);
        manager.load(arrow);
        manager.load(instr1);
        manager.load(instr2);
        manager.load(instr3);

        manager.load(button_ads_free);
        manager.load(button_vk);
        manager.load(button_fb);
        manager.load(button_tw);

        //manager.load(poof_balloon_atlas_o_Texture_region, TextureRegion.class);
        //effect = new ParticleEffect();
        effect_green.loadEmitters(Gdx.files.internal("particles/pop_green.p"));
        effect_yellow.loadEmitters(Gdx.files.internal("particles/pop_yellow.p"));
        effect_blue.loadEmitters(Gdx.files.internal("particles/pop_blue.p"));
        effect_red.loadEmitters(Gdx.files.internal("particles/pop_red.p"));
        effect_purple.loadEmitters(Gdx.files.internal("particles/pop_perp.p"));
        effect_orange.loadEmitters(Gdx.files.internal("particles/pop_orange.p"));

        effect_green.loadEmitterImages(Gdx.files.internal("particles"));
        effect_yellow.loadEmitterImages(Gdx.files.internal("particles"));
        effect_blue.loadEmitterImages(Gdx.files.internal("particles"));
        effect_red.loadEmitterImages(Gdx.files.internal("particles"));
        effect_purple.loadEmitterImages(Gdx.files.internal("particles"));
        effect_orange.loadEmitterImages(Gdx.files.internal("particles"));

        combo_2x.loadEmitters(Gdx.files.internal("particles/2x_combo.p"));
        combo_3x.loadEmitters(Gdx.files.internal("particles/3x_combo.p"));
        combo_4x.loadEmitters(Gdx.files.internal("particles/4x_combo.p"));
        combo_5x.loadEmitters(Gdx.files.internal("particles/5x_combo.p"));
        combo_6x.loadEmitters(Gdx.files.internal("particles/6x_combo.p"));
        //combo_7x.loadEmitters(Gdx.files.internal("particles/7x_combo"));

        combo_2x.loadEmitterImages(Gdx.files.internal("particles"));
        combo_3x.loadEmitterImages(Gdx.files.internal("particles"));
        combo_4x.loadEmitterImages(Gdx.files.internal("particles"));
        combo_5x.loadEmitterImages(Gdx.files.internal("particles"));
        combo_6x.loadEmitterImages(Gdx.files.internal("particles"));
        //combo_7x.loadEmitterImages(Gdx.files.internal("particles/pop_green"));

        c_3x.load(Gdx.files.internal("particles/3x_c.p"),Gdx.files.internal("particles"));
        c_4x.load(Gdx.files.internal("particles/4x_c.p"),Gdx.files.internal("particles"));
        c_5x.load(Gdx.files.internal("particles/5x_c.p"),Gdx.files.internal("particles"));
        c_6x.load(Gdx.files.internal("particles/6x_c.p"),Gdx.files.internal("particles"));


        hearth_ballon_part.load(Gdx.files.internal("particles/plus5.p"),Gdx.files.internal("particles"));
        hearth_ballon_part_hole1.load(Gdx.files.internal("particles/heart_hole1.p"),Gdx.files.internal("particles"));
        hearth_ballon_part_hole2.load(Gdx.files.internal("particles/heart_hole2.p"),Gdx.files.internal("particles"));
        hearth_ballon_part_heart.load(Gdx.files.internal("particles/heart.p"),Gdx.files.internal("particles"));
        hearth_ballon_part_basket.load(Gdx.files.internal("particles/basket.p"),Gdx.files.internal("particles"));

        ballon_n_st_ice.load(Gdx.files.internal("particles/pop_x2.p"),Gdx.files.internal("particles"));
        ballon_n_st_wood.load(Gdx.files.internal("particles/pop_x3.p"),Gdx.files.internal("particles"));
        ballon_n_st_stone.load(Gdx.files.internal("particles/pop_x4.p"),Gdx.files.internal("particles"));
        ballon_n_st_kript.load(Gdx.files.internal("particles/pop_x5.p"),Gdx.files.internal("particles"));
        ballon_n_st_initblue.load(Gdx.files.internal("particles/pop_x6.p"),Gdx.files.internal("particles"));
        ballon_n_st_pheon.load(Gdx.files.internal("particles/pop_x7.p"),Gdx.files.internal("particles"));
        ballon_n_st_bronze.load(Gdx.files.internal("particles/pop_x8.p"),Gdx.files.internal("particles"));
        ballon_n_st_silver.load(Gdx.files.internal("particles/pop_x9.p"),Gdx.files.internal("particles"));
        ballon_n_st_gold.load(Gdx.files.internal("particles/pop_x10.p"),Gdx.files.internal("particles"));

        gold_stars.load(Gdx.files.internal("particles/star_metal.p"),Gdx.files.internal("particles"));
        gem_stars.load(Gdx.files.internal("particles/star_gem.p"),Gdx.files.internal("particles"));
        fire_bomb1.load(Gdx.files.internal("particles/fire_bomb1.p"),Gdx.files.internal("particles"));
        fire_bomb2.load(Gdx.files.internal("particles/fire_bomb2.p"),Gdx.files.internal("particles"));
        bomb_blow.load(Gdx.files.internal("particles/bomb_blow.p"),Gdx.files.internal("particles"));
        score_500_effect_wow.load(Gdx.files.internal("particles/wow.p"),Gdx.files.internal("particles"));
        score_500_effect_cool.load(Gdx.files.internal("particles/cool.p"),Gdx.files.internal("particles"));
        score_500_effect_amazing.load(Gdx.files.internal("particles/amazing.p"),Gdx.files.internal("particles"));

        miss_ball.load(Gdx.files.internal("particles/miss_ball.p"),Gdx.files.internal("particles"));
        beat_high_score.load(Gdx.files.internal("particles/high_score.p"),Gdx.files.internal("particles"));

        well_play_particle.load(Gdx.files.internal("particles/well_played.p"),Gdx.files.internal("particles"));
        nice_try_particle.load(Gdx.files.internal("particles/nice_try.p"),Gdx.files.internal("particles"));

        beam.load(Gdx.files.internal("particles/beams.p"),Gdx.files.internal("particles"));
        medal_x2.load(Gdx.files.internal("particles/medal_x2.p"),Gdx.files.internal("particles"));
        medal_x3.load(Gdx.files.internal("particles/medal_x3.p"),Gdx.files.internal("particles"));
        medal_x4.load(Gdx.files.internal("particles/medal_x4.p"),Gdx.files.internal("particles"));
        medal_x5.load(Gdx.files.internal("particles/medal_x5.p"),Gdx.files.internal("particles"));
        medal_x6.load(Gdx.files.internal("particles/medal_x6.p"),Gdx.files.internal("particles"));
        medal_x7.load(Gdx.files.internal("particles/medal_x7.p"),Gdx.files.internal("particles"));
        medal_x8.load(Gdx.files.internal("particles/medal_x8.p"),Gdx.files.internal("particles"));
        medal_x9.load(Gdx.files.internal("particles/medal_x9.p"),Gdx.files.internal("particles"));

        manager.load(plah);
        manager.load(ico_mute);
        manager.load(ico_unmute);

        manager.load(sprt_vibro);
        manager.load(sprt_unvibro);

        manager.load(sprt_music);
        manager.load(sprt_unmusic);

        manager.load(leaderboard);
        manager.load(sprt_achiv);

        manager.load(bgnd_white);

        manager.load(game_name);



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
    public static void make_PlayState_linear(){
        Assets.instance.manager.get(Assets.texture_poop_balloon).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.tap_to_play).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.score).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    public static void make_linear(){
       // Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_o,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       // Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_g,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       // Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_b,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       // Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_y,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       // Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_r,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
      //  Assets.instance.manager.get(Assets.poof_balloon_atlas_idle_p,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

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
        Assets.instance.manager.get(Assets.heart_baloon_hole1).setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.heart_baloon_hole2).setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);


    }
    public static void make_resized_balloons_linear(){
        Assets.instance.manager.get(Assets.balloon_orange,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_ice).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_ns_x2).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_stone).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_kript).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_blueinit).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_pheon).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_bronze).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_silver).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.balloon_ns_x10).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public static void make_medals_linear(){
        Assets.instance.manager.get(Assets.medal_ice).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    public static void make_medals_w_linear(){

    }

    public static void make_ads_linear(){
        Assets.instance.manager.get(Assets.button_ads_free).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.button_vk).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.instance.manager.get(Assets.button_fb).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }
}
