package ru.asupd.poop_ballon.Workers;

/**
 * Created by asupd on 31.10.17.
 */

public interface GameHelperListener {
    /**
     * Вызывается при неудачной попытке входа. В этом методе можно показать
     * пользователю кнопку «Sign-in» для ручного входа
     */
    void onSignInFailed();

    /** Вызывается при удачной попытке входа */
    void onSignInSucceeded();
}
