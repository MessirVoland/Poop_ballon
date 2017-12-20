package ru.asupd.poop_ballon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
//import com.google.example.games.basegameutils.GameHelper;
//import com.google.example.games.basegameutils.GameHelper;

import ru.asupd.poop_ballon.Workers.ActionResolver;
import ru.asupd.poop_ballon.Workers.AdsController;
import ru.asupd.poop_ballon.Workers.GameHelperListener;
import ru.asupd.poop_ballon.Workers.PlayServices;

import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.example.games.basegameutils.GameHelper;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;

public class AndroidLauncher extends AndroidApplication implements AdsController,GameHelperListener, ActionResolver,PlayServices {
	public static final String TAG="AndroidLauncher";
    RelativeLayout layout;

	protected AdView adView;
	protected AdView adView_full_ads;
	private InterstitialAd mInterstitialAd;
	//GameHelper gameHelper;
	View gameView;

	private GoogleSignInClient signInClient;
	private GoogleSignInAccount signedInAccount=null;
    //private GoogleSignInAccount account;
    private GamesClient gamesClient;
	private Intent intent;
	private GoogleSignInResult result;

	private final static int requestCode = 1;
	//GoogleSignInClient mGoogleSignInClient;

	private static final int RC_LEADERBOARD_UI = 9004;
	private static final int RC_SIGN_IN = 9001;
	private static final int RC_ACHIEVEMENT_UI = 9003;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        layout = new RelativeLayout(this);

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
		//gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		//gameHelper.enableDebugLog(false);

		//GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		//{
		//	@Override
		//	public void onSignInFailed(){ }

		//	@Override
		//	public void onSignInSucceeded(){ }
		//};

		//gameHelper.setup(gameHelperListener);
		gameView = initializeForView(new MyGdxGame(this,this,this), config);
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
		//startSignInIntent();
       // gamesClient.setViewForPopups(new View(this));


	}

	@Override
	protected void onStart() {
		super.onStart();
		//gameHelper.onStart(this);
	}
	@Override
	protected void onStop() {
		super.onStop();
		//gameHelper.onStop();
        //signInClient.revokeAccess();

	}

	@Override
	protected void onResume() {
		super.onResume();
		signInSilently();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//new AlertDialog.Builder(this).setMessage("requestCode:" +requestCode).setNeutralButton(android.R.string.ok, null).show();
		if (requestCode == RC_SIGN_IN) {
			result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			//while (result.getStatus().getStatusCode()==12502){
				//result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			//}
			//new AlertDialog.Builder(this).setMessage("result: " +result.getStatus()).setNeutralButton(android.R.string.ok, null).show();
			//new AlertDialog.Builder(this).setMessage(result.getStatus().getStatusMessage()).setNeutralButton(android.R.string.ok, null).show();
			if (result.isSuccess()) {
				// The signed in account is stored in the result.
				 signedInAccount = result.getSignInAccount();


                //new AlertDialog.Builder(this).setMessage("Signed in. All ok. =)").setNeutralButton(android.R.string.ok, null).show();
			} else {
				String message = result.getStatus().getStatusMessage();
				if (message == null || message.isEmpty()) {
					message = "Error smth went wrong: 132";
				}
				//new AlertDialog.Builder(this).setMessage(message).setNeutralButton(android.R.string.ok, null).show();
			}

		}
	}
	//@Override
	//protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	//	super.onActivityResult(requestCode, resultCode, data);
		//gameHelper.onActivityResult(requestCode, resultCode, data);
//	}


	@Override
	public void signIn() {
        signInSilently();
        //if (!isSignedIn()|signedInAccount==null) {

        //}


		/*
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					//gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}*/
	}



	@Override
    public void signOut() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // at this point, the user is signed out.
                    }
                });
    }

	@Override
	public void rateGame() {
		String str = "Your PlayStore Link";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement(String id_achiv) {
		if (isSignedIn()==true) {
			if (id_achiv=="CgkIvYetxegNEAIQCQ"){
				Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).increment(id_achiv, 1);
			}else
			//Games.Achievements.unlock(gameHelper.getApiClient(), id_achiv);
			Games.getAchievementsClient(this, signedInAccount).unlock(id_achiv);
		}
		else{
			signIn();
		}

	}

	@Override
	public void submitScore(long highScore) {
		if (isSignedIn() == true)
		{System.out.println("Signet");
			//
			// Games.Leaderboards.submitScore(gameHelper.getApiClient(),getString(R.string.leaderboard_world_high_score), highScore);
			Games.getLeaderboardsClient(this, signedInAccount).submitScore(getString(R.string.leaderboard_world_high_score), highScore);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void submitScore_ALLScore(long highScore) {
		if (isSignedIn() == true)
		{System.out.println("Signet_All_Score");
			Games.getLeaderboardsClient(this, signedInAccount).submitScore(getString(R.string.leaderboard_world_maximum_score), highScore);
			//Games.Leaderboards.submitScore(gameHelper.getApiClient(),
			//		getString(R.string.leaderboard_world_maximum_score), highScore);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void showAchievement() {
		//Games.Achievements.unlock(gameHelper.getApiClient(),
		//		getString(R.string.achievement_dum_dum));
		if (isSignedIn()==true){
			//startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
			Games.getAchievementsClient(this, signedInAccount).getAchievementsIntent().addOnSuccessListener(new OnSuccessListener<Intent>() {
				@Override
				public void onSuccess(Intent intent) {
					startActivityForResult(intent,RC_ACHIEVEMENT_UI);
				}
			});
		}
		else
		{
			//signInSilently();
			//new AlertDialog.Builder(this).setMessage(signedInAccount.getId()).setNeutralButton(android.R.string.ok, null).show();
			signIn();
		}
	}

	@Override
	public void showScore() {
		if (isSignedIn())
		{
			//startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()),requestCode);
			//startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
				//	getString(R.string.leaderboard_world_high_score)), requestCode);
			Games.getLeaderboardsClient(this,signedInAccount).getAllLeaderboardsIntent().addOnSuccessListener(new OnSuccessListener<Intent>() {
						@Override
						public void onSuccess(Intent intent) {
							startActivityForResult(intent, RC_LEADERBOARD_UI);
						}
					});
		}
		else
		{
			signIn();
		}
	}

	@Override
    public boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
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

		//signInSilently();
		//Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this)).submitScore("CgkIibnQwPAeEAIQAQ",score);
		//Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this)).submitScore("CgkIibnQwPAeEAIQAQ",score);
	}
	private void signInSilently() {
		signInClient = GoogleSignIn.getClient(this,GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

		signInClient.silentSignIn().addOnCompleteListener(this,
				new OnCompleteListener<GoogleSignInAccount>() {
					@Override
					public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
						if (task.isSuccessful()) {
							// The signed in account is stored in the task's result.
							signedInAccount = task.getResult();
                            //gamesClient = Games.getGamesClient(AndroidLauncher.this,signedInAccount);
                            //gamesClient.setViewForPopups(layout);
						} else {
							// Player will need to sign-in explicitly using via UI
                            //signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
                            intent = signInClient.getSignInIntent();
                            startActivityForResult(intent, RC_SIGN_IN);
						}
					}
				});
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {

	}

	@Override
	public void getLeaderboardGPGS() {
			//Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
			//		.getLeaderboardIntent("CgkIibnQwPAeEAIQAQ")
			//		.addOnSuccessListener(new OnSuccessListener<Intent>() {
				//		@Override
				//		public void onSuccess(Intent intent) {
				//			startActivityForResult(intent, RC_LEADERBOARD_UI);
				//		}
				//	});
	}


	@Override
	public void getAchievementsGPGS() {

	}

	@Override
	public void onSignInFailed() {

	}

	@Override
	public void onSignInSucceeded() {
        //signInSilently();
	}
	private void startSignInIntent() {
	//	GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
	//			GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
	//	Intent intent = signInClient.getSignInIntent();
	//	startActivityForResult(intent, RC_SIGN_IN);
	}
}