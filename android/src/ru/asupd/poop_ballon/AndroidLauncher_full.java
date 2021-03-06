package ru.asupd.poop_ballon;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import ru.asupd.poop_ballon.Workers.AdsController;

/**
 * Created by Asup.D on 27.10.2017.
 */

public class AndroidLauncher_full extends AndroidApplication implements AdsController {
    public static final String TAG="AndroidLauncher";

    protected AdView adView_full_ads;
    protected AdView adView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout layout = new RelativeLayout(this);

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;

        //config.useGL30=true;//не использовать с рекламой
        //** Лучше вообще не использовать

        // Create the libgdx View
        View gameView = initializeForView(new MyGdxGame(this,null,null), config);
        gameView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        layout.addView(gameView);

        // Create and setup the AdMob view
        //adView = new AdView(this);
        //adView.setAdListener(new AdListener() {
        //	@Override
        //	public void onAdLoaded() {

        //	Log.i(TAG, "Ad Loaded13.. rr.");
        //	}
        //});
        //adView.setAdSize(AdSize.SMART_BANNER);
        //adView.setAdUnitId("ca-app-pub-6755493316893566/6656095586");

        setupAds();
        AdRequest.Builder builder = new AdRequest.Builder();
        //builder.addTestDevice("ADCD72548573E2D66A2AFC8594EDF6F6");
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(adView,adParams);
        //adView.loadAd(builder.build());

        setContentView(layout);
    }
    public void setupAds() {
        adView = new AdView(this);
        adView.setVisibility(View.INVISIBLE);
        adView.setBackgroundColor(0xff000000); // black
        //adView.setAdUnitId("ca-app-pub-1769194239356799/3445897678");//google
        //adView.setAdUnitId("ca-app-pub-1769194239356799/9412540227");//samsung
        adView.setAdUnitId("ca-app-pub-1769194239356799/5197963043"); // test ads
        adView.setAdSize(AdSize.SMART_BANNER);

        adView_full_ads=new AdView(this);
        adView_full_ads.setVisibility(View.INVISIBLE);
        adView.setBackgroundColor(0xff000000); // black
        adView.setAdUnitId("ca-app-pub-1769194239356799/4759804724");
        adView.setAdSize(AdSize.FULL_BANNER);
    }

    @Override
    public void showBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adView.setVisibility(View.VISIBLE);
                AdRequest.Builder builder = new AdRequest.Builder();
                AdRequest ad = builder.build();
               // adView.loadAd(ad);
            }
        });
    }

    @Override
    public void showBannerAd_full() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adView_full_ads.setVisibility(View.VISIBLE);
                AdRequest.Builder builder = new AdRequest.Builder();
                AdRequest ad = builder.build();
                //adView_full_ads.loadAd(ad);
            }
        });
    }

    @Override
    public void hideBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni;
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (ni == null && ni.isConnected()) {
            ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        }
        return (ni != null && ni.isConnected());
    }
    public boolean isOnline() {

        return false;
    }
}
