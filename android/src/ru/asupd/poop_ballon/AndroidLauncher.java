package ru.asupd.poop_ballon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import ru.asupd.poop_ballon.MyGdxGame;
import ru.asupd.poop_ballon.Workers.AdsController;

public class AndroidLauncher extends AndroidApplication implements AdsController{
	public static final String TAG="AndroidLauncher";

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
		View gameView = initializeForView(new MyGdxGame(this), config);
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
		adView.setAdUnitId("ca-app-pub-6755493316893566/6656095586");
		adView.setAdSize(AdSize.SMART_BANNER);
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adView.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				adView.loadAd(ad);
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
}