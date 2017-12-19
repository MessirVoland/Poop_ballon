package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

import ru.asupd.poop_ballon.States.PlayState;

import static ru.asupd.poop_ballon.States.MenuState.settings;
import static ru.asupd.poop_ballon.States.PlayState.prefs;

/**
 * Класс для звуков;
 * Created by Asup.D on 11.10.2017.
 */

public final class Sound_effects {

    public static final float minX = 0.75f;//Диапазон изменения хлопка
    public static final float maxX = 1.7f;

    private Sound poop_Sound;//звук лопания
    private Sound click_Sound;//звук нажатия нопки
    private Sound snd_big_baloon;//Пролет красного шара
    private Sound snd_life;
    private Sound snd_heart, //- лопание шара здоровья
    snd_bomb, //- взрыв бомбы
    snd_bsg, //- звук взрыва бронзового,серебрянного и золотого шара
    snd_pop,// взрыв обычного шара(и спешл шара тоже)
    snd_progressl, //- звук когда заполняется прогрессбар на геймоверскрине(перед появлением медали)
    snd_red, //- взрыв рубинового,изумрудного и альмазного шара
    snd_scores, //- звук при подсчете очков
    snd_steel,snd_stone,snd_wood ,//- стальной,каменный и деревянные шары
    snd_take_medal, //- появление медали
    snd_titles; //- звук появления надписей wow, mega combo и всё вот это вот.)
    public Sound_effects() {

        poop_Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/poop.mp3"));
        click_Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/button.wav"));
        snd_big_baloon = Gdx.audio.newSound(Gdx.files.internal("sounds/snd_big_baloon.wav"));
        snd_life = Gdx.audio.newSound(Gdx.files.internal("sounds/snd_life.wav"));
        snd_titles = Gdx.audio.newSound(Gdx.files.internal("sounds/snd_titles.wav"));
        snd_scores = Gdx.audio.newSound(Gdx.files.internal("sounds/snd_scores.wav"));
        snd_progressl = Gdx.audio.newSound(Gdx.files.internal("sounds/snd_progressl.wav"));
        snd_take_medal = Gdx.audio.newSound(Gdx.files.internal("sounds/snd_take_medal.wav"));
        settings= new Settings(prefs);
        settings.hi_score_refresh();
    }
    static {


    }
    public void poop_sound(){
        if (!settings.isMute()) {
            long id = poop_Sound.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            poop_Sound.setPitch(id, finalX);
            poop_Sound.setVolume(id, PlayState.volume);
        }
    }
    public void click_sound(){
        if (!settings.isMute()) {
            long id = click_Sound.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            click_Sound.setPitch(id, finalX);
            click_Sound.setVolume(id, PlayState.volume);
        }
    }
    public void snd_big_baloon(){
        if (!settings.isMute()) {
            long id = snd_big_baloon.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            snd_big_baloon.setPitch(id, finalX);
            snd_big_baloon.setVolume(id, PlayState.volume);
        }
        else {
            //System.out.println("mute");
        }

    }
    public void snd_bomb(){
        if (!settings.isMute()) {
            long id = poop_Sound.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            poop_Sound.setPitch(id, finalX);
            poop_Sound.setVolume(id, PlayState.volume);
        }

    }
    public void snd_life(){
        if (!settings.isMute()) {
            System.out.println("sound_life");
            long id = snd_life.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            snd_life.setPitch(id, finalX);
            snd_life.setVolume(id, PlayState.volume);
            //System.out.println("volume: "+PlayState.volume);
        }

    }

    public void snd_titles(){
        if (!settings.isMute()) {
            System.out.println("sound_life");
            long id = snd_titles.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            snd_titles.setPitch(id, finalX);
            snd_titles.setVolume(id, PlayState.volume);
            //System.out.println("volume: "+PlayState.volume);
        }
    }
    public void snd_scores(){
        if (!settings.isMute()) {
            System.out.println("sound_life");
            long id = snd_scores.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            snd_scores.setPitch(id, finalX);
            snd_scores.setVolume(id, PlayState.volume);
            //System.out.println("volume: "+PlayState.volume);
        }
    }
    public void snd_progressl(){
        if (!settings.isMute()) {
            System.out.println("sound_life");
            long id = snd_progressl.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            snd_progressl.setPitch(id, finalX);
            snd_progressl.setVolume(id, PlayState.volume);
            //System.out.println("volume: "+PlayState.volume);
        }
    }
    public void snd_take_medal(){
        if (!settings.isMute()) {
            System.out.println("sound_life");
            long id = snd_take_medal.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            snd_take_medal.setPitch(id, finalX);
            snd_take_medal.setVolume(id, PlayState.volume);
            //System.out.println("volume: "+PlayState.volume);
        }
    }

}
