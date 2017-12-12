package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

import ru.asupd.poop_ballon.States.PlayState;

/**
 * Класс для звуков;
 * Created by Asup.D on 11.10.2017.
 */

public final class Sound_effects {

    public static final float minX = 0.75f;//Диапазон изменения хлопка
    public static final float maxX = 1.7f;

    private Sound poop_Sound;//звук лопания
    private Sound click_Sound;//звук нажатия нопки
    public Sound_effects() {
        poop_Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/poop.mp3"));
        click_Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/button.wav"));
    }
    static {

    }
    public void poop_sound(){
        if (!PlayState.settings.isMute()) {
            long id = poop_Sound.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            poop_Sound.setPitch(id, finalX);
            poop_Sound.setVolume(id, PlayState.volume);
        }
    }
    public void click_sound(){
        if (!PlayState.settings.isMute()) {
            long id = click_Sound.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            click_Sound.setPitch(id, finalX);
            click_Sound.setVolume(id, PlayState.volume);
        }
    }
}
