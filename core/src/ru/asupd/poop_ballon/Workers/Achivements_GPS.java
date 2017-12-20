package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static ru.asupd.poop_ballon.MyGdxGame.playServices_my;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.APP_STORE_NAME;

/**
 * Модуль отвечающий за ачивки
 * Created by Voland on 11.12.2017.
 */

public class Achivements_GPS {
     Preferences pref_s = Gdx.app.getPreferences(APP_STORE_NAME);

    public Achivements_GPS() {
        if (!pref_s.contains("achive_0")) {
            pref_s.putBoolean("achive_0", false);
            pref_s.flush();
        }
    }
    public boolean trigger_Alpha_People(){
        //нужно ускорить триггер
        //System.out.println("Achivement "+ pref_s.getBoolean("achive_0"));

        return pref_s.getBoolean("achive_0");
    }
    public boolean unlock_Alpha_People(){
        //Перепроверка ачивок
        //if (pref_s.contains("achive_0"))
        //{
          //  if (playServices_my!=null) {
                // playServices_my.unlockAchievement(0);
           //     pref_s.putBoolean("achive_0", true);
                //System.out.println("Unlock Achivment");
        if (playServices_my!=null) {
            playServices_my.unlockAchievement("CgkIvYetxegNEAIQAw");
        }
           // }else
          //  {
          //      pref_s.putBoolean("achive_0", false);
           // }
          //  pref_s.flush();
          //  return true;
       // }
        return false;
    }
    public void unlock_yellow_king(){
        if (playServices_my!=null) {
            playServices_my.unlockAchievement("CgkIvYetxegNEAIQCQ");
        }
    }

}
