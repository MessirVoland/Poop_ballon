package ru.asupd.poop_ballon;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.example.games.basegameutils.GameHelper;
//import com.google.example.games.basegameutils.GameHelper;

import ru.asupd.poop_ballon.MyGdxGame;
import ru.asupd.poop_ballon.Workers.ActionResolver;
import ru.asupd.poop_ballon.Workers.AdsController;
import ru.asupd.poop_ballon.Workers.GameHelperListener;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements AdsController,GameHelperListener, ActionResolver {
	public static final String TAG="AndroidLauncher";

	protected AdView adView;
	protected AdView adView_full_ads;
	private InterstitialAd mInterstitialAd;
	GameHelper gameHelper;

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
		//gameView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
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
//		layout.addView(adView_full_ads,adParams);
		//adView.loadAd(builder.build());

		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-1769194239356799/4759804724");
		mInterstitialAd.loadAd(new AdRequest.Builder().build());

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

		//adView_full_ads=new AdView(this);
		//adView_full_ads.setVisibility(View.INVISIBLE);
		//adView.setBackgroundColor(0xff000000); // black
		//adView_full_ads.setAdUnitId("ca-app-pub-1769194239356799/4759804724");
		//adView_full_ads.setAdSize(AdSize.FULL_BANNER);



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
	public void showBannerAd_full() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//System.out.println("adLoaded"+mInterstitialAd.isLoaded());
				if (mInterstitialAd.isLoaded()) {
					mInterstitialAd.show();
				}
				//adView_full_ads.setVisibility(View.VISIBLE);
				//AdRequest.Builder builder = new AdRequest.Builder();
				//AdRequest ad = builder.build();
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
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	@Override
	public boolean getSignedInGPGS() {
		return false;
	}

	@Override
	public void loginGPGS() {

	}

	@Override
	public void submitScoreGPGS(int score) {

	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {

	}

	@Override
	public void getLeaderboardGPGS() {

	}

	@Override
	public void getAchievementsGPGS() {

	}

	@Override
	public void onSignInFailed() {

	}

	@Override
	public void onSignInSucceeded() {

	}
}