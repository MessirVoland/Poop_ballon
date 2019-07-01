package ru.asupd.poop_ballon.Workers;

/**
 * Created by Asup.D on 15.11.2017.
 */

public interface PlayServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement(String id_achive);
    public void submitScore(long highScore);
    public void submitScore_ALLScore(long highScore);
    public void showAchievement();
    public void showScore();
    public boolean isOnline();
    public boolean isSignedIn();
}
