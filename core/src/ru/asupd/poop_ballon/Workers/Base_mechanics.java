package ru.asupd.poop_ballon.Workers;

/**
 * Сингл тон со всей механикой.
 * Created by Asup.D on 25.10.2017.
 */

public class Base_mechanics {

    //Раздел Констант графики и пр элементов-----------------------------------------------------------------/

    //Название приложения для хранения настроек
    public static final String APP_STORE_NAME = "Poop_ballons_90471d221cb7702a2b7ab38a5433c26e";
    //Скорость и время анимиции оранжения
    public static final float ANIMATION_SPEED_RESIZE=0.02f;//скосрость изменения размера 0.04
    public final static float ANIMATION_TIME=0.18f; //время изменения размера 0.28
    //Cкорость и время анимации надписии tap_to_play
    public static final float ANIMATION_TIME_TAP_TO_PLAY=0.45f;//время
    public static float ANIMATIO_TIME_TO_PLAY_SIZE=0.0050f; //скорость
    //СЧет
    public static final float ONE_FRAME_COUNT=0.0025f;//время одно кадра смены счета +1
    public static final float COMBO_TIME=0.455f;//время которое висит +4 +9 рядом со счетом
    public static final float TIME_DT_COMBO=0.08f;//Время между взрывами комбо
    public static final float TIME_COUNT_LAST_GAME_SCORE=3.0f;//время на подсчет отчков в конце

    //-------------------------------------------------------------------------------------------------------/

    //Раздел констант для баланса игры-----------------------------------------/

    public static final int MEDAL_START=500;//старт счета медалей
    public static final int MEDAL_SCORE=500;//медали
    public static final int SEQUENCE_OF_HEARTH_BALLOON=300;//шары здоровья каждые 120 умноженные на текущую медаль
    public static final int CHANSE_OF_WOODEN_BALLOON=15;//15% Шанс нестандартного шара вместо обычного
    public static final int CHANSE_OF_SPAWN_BOMB=5;//5% Шанс бомбы после клика х3 комбо
    public static final int SIZE_OF_COMBO_FOR_BOMB_SPAWN=3;//х3 Размер комбо для проверки спавна бомбы
    public static final float IMMORTAL_TIME=0.5f;

    public static final long[] NEEDED_SCORE={
            10000,//1 Дерево
            20000,//2 Камень
            40000,//3 Железо
            80000,//4 Бронза
           160000,//5 Серебро
           320000,//6 Золото
           640000,//7 Рубин
          1280000,//8 Изумруд
          2560000 //9 Алмаз
    };


    //-------------------------------------------------------------------------/

    private static final Base_mechanics ourInstance = new Base_mechanics();

    public static Base_mechanics getInstance() {
        return ourInstance;
    }

    private Base_mechanics() {
    }
}
