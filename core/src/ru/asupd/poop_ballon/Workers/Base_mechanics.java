package ru.asupd.poop_ballon.Workers;

/**
 * Сингл тон со всей механикой.
 * Created by Asup.D on 25.10.2017.
 */

public class Base_mechanics {
    //Название приложения для хранения настроек
    public static final String APP_STORE_NAME = "Poop_ballons_90471d221cb7702a2b7ab38a5433c26e";
    //Скорость и время анимиции оранжения
    public static final float ANIMATION_SPEED_RESIZE=0.04f;//скосрость изменения размера
    public final static float ANIMATION_TIME=0.28f; //время изменения размера

    private static final Base_mechanics ourInstance = new Base_mechanics();

    public static Base_mechanics getInstance() {
        return ourInstance;
    }

    private Base_mechanics() {
    }
}
