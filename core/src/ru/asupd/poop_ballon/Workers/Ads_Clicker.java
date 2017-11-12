package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;

import static ru.asupd.poop_ballon.States.PlayState.button_ads_free;

/**
 *
 * Created by voland on 12.11.17.
 */

public class Ads_Clicker {
    public Ads_Clicker(){

    }
    public boolean click (int x,int y){
        if (button_ads_free.getBoundingRectangle().contains(x,y)) {
            Gdx.net.openURI("https://vk.com/messir.voland");
            System.out.println("GDX net openURI");
            return true;
        }
        else
            return false;
    }
}
