package ru.asupd.poop_ballon.Workers;

/**
 * Created by Asup.D on 03.10.2017.
 */

public interface AdsController {
    public void showBannerAd();
    public void showBannerAd_full();
    public void hideBannerAd();
    public boolean isWifiConnected();
    public boolean isOnline();
}
