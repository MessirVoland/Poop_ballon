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
    private Sound poop_Sound;//звук лопания
    public Sound_effects() {
        poop_Sound = Gdx.audio.newSound(Gdx.files.internal("poop.mp3"));
    }
    static {

    }
    public void poop_sound(){
        if (!PlayState.settings.isMute()) {
            long id = poop_Sound.play(PlayState.volume);
            Random rand = new Random();
            float finalX = rand.nextFloat() * (PlayState.maxX - PlayState.minX) + PlayState.minX;
            poop_Sound.setPitch(id, finalX);
            poop_Sound.setVolume(id, PlayState.volume);
        }
    }
}
