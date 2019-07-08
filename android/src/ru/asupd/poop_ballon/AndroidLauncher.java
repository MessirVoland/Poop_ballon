package ru.asupd.poop_ballon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.android.gms.common.api.ApiException;
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

	private static GoogleSignInClient signInClient;
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

		signInClient = GoogleSignIn.getClient(this,GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//signInSilently();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if (result.isSuccess()) {
				// The signed in account is stored in the result.
				GoogleSignInAccount signedInAccount = result.getSignInAccount();
			} else {
				String message = result.getStatus().getStatusMessage();
				new AlertDialog.Builder(this).setMessage(message)
						.setNeutralButton(android.R.string.ok, null).show();
			}
		}
	}

	private void startSignInIntent() {
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
									.requestEmail()
									.build();
/*
							//Activity currActivity = appConstantsObj.getCurrentActivity();
							GoogleSignInClient signInClient = GoogleSignIn.getClient(gameView.getContext(),gso);
							Intent signInIntent = signInClient.getSignInIntent();
   							startActivityForResult(signInIntent, RC_SIGN_IN);*/



		GoogleSignInClient signInClient = GoogleSignIn.getClient(this,gso);
		Intent signInIntent = signInClient.getSignInIntent();
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}






	//Дурацкая функция из-за которой скорей всего всё и вылетает!
	//Тихий вход в аккаунт
	@Override
	public void signIn() {
		if (isOnline()) {
			signInSilently();
		}else
		{
			System.out.println("NOT ON LINE");
		}
	}


	//Выйти из акка
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

    //Оценить игру
	@Override
	public void rateGame() {
		String str = "Your PlayStore Link";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	//Разблокировать ачивку
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

	//Счет матча
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

	//Подтвердить общий счет
	@Override
	public void submitScore_ALLScore(long highScore) {
		if (isSignedIn() == true)
		{System.out.println("Signet_All_Score");
			Games.getLeaderboardsClient(this, signedInAccount).submitScore(getString(R.string.leaderboard_world_maximum_score), highScore);
		}
		else
		{
			signIn();
		}
	}

	//показать ачивки иначе войти в акк
	@Override
	public void showAchievement() {
		if (isSignedIn()==true){
			Games.getAchievementsClient(this, signedInAccount).getAchievementsIntent().addOnSuccessListener(new OnSuccessListener<Intent>() {
				@Override
				public void onSuccess(Intent intent) {
					startActivityForResult(intent,RC_ACHIEVEMENT_UI);
				}
			});
		}
		else
		{
			signIn();
		}
	}

	//Показать результат иначе войти в акк.
	@Override
	public void showScore() {
		if (isSignedIn())
		{
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

	//Перезайти если уже зашел
	@Override
    public boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }


    //Установить адс
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

	//Показать постороить? баннер
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

	//Показать баннер
	@Override
	public void showBannerAd_full() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mInterstitialAd.isLoaded()) {
					mInterstitialAd.show();
				}
			}
		});
	}

	//Спрятать баннер
	@Override
	public void hideBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adView.setVisibility(View.INVISIBLE);
			}
		});
	}

	//Проверка соединения с вафлей
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

	//Функция проверки ОНЛАЙНА
	public boolean isOnline() {
		System.out.println("Check isOnline from mob");
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}


	//Хуй знает что
	@Override
	public boolean getSignedInGPGS() {
		return false;
	}
	@Override
	public void loginGPGS() {	}
	@Override
	public void submitScoreGPGS(int score) {	}
	//Конец




	//Тихий вход в акк
	private void signInSilently() {
		/*
		mGoogleSignInClient.silentSignIn().addOnCompleteListener(this,
				new OnCompleteListener<GoogleSignInAccount>() {
					@Override
					public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
						if (task.isSuccessful()) {
							greetingMsg="Welcome back, ";
							onConnected(task.getResult());
						} else {
							onDisconnected();
						}
					}
				});
		*/

		//
		signInClient.silentSignIn().addOnCompleteListener(this,
				new OnCompleteListener<GoogleSignInAccount>() {
					@Override
					public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
						if (task.isSuccessful()) {
							// The signed in account is stored in the task's result.
							signedInAccount = task.getResult();
							GamesClient gamesClient = Games.getGamesClient(AndroidLauncher.this, signedInAccount);
                            //gamesClient = Games.getGamesClient(AndroidLauncher.this,signedInAccount);
                            //gamesClient.setViewForPopups(layout);
						} else {
							// Player will need to sign-in explicitly using via UI
							//GoogleSignInOptions signInOption = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;

                            //signInClient = GoogleSignIn.getClient(activity, signInOption);

							//intent = signInClient.getSignInIntent();
                            //startActivityForResult(intent, RC_SIGN_IN);
							//LeaderboardsClient = null;
							//PlayersClient = null;

							/*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
									.requestEmail()
									.build();

							//Activity currActivity = appConstantsObj.getCurrentActivity();
							GoogleSignInClient signInClient = GoogleSignIn.getClient(gameView.getContext(),gso);
							Intent signInIntent = signInClient.getSignInIntent();
   							startActivityForResult(signInIntent, RC_SIGN_IN);*/
							startSignInIntent();
						}
					}
				});

	}
/*
	private void onConnected(GoogleSignInAccount googleSignInAccount) {

		//mLeaderboardsClient = Games.getLeaderboardsClient(this, googleSignInAccount);
		//mPlayersClient = Games.getPlayersClient(this, googleSignInAccount);

		mPlayersClient.getCurrentPlayer()
				.addOnCompleteListener(new OnCompleteListener<Player>() {
					@Override
					public void onComplete(@NonNull Task<Player> task) {
						String displayName;
						if (task.isSuccessful()) {
							displayName = task.getResult().getDisplayName();
						} else {
							Exception e = task.getException();
							handleException(e, getString(R.string.players_exception));
							displayName = "???";
						}

						if(!greetingDisplayed)
							welcomeMessage(displayName);
					}
				});
	}*/

	@Override
	public void unlockAchievementGPGS(String achievementId) {

	}

	@Override
	public void getLeaderboardGPGS() {	}


	@Override
	public void getAchievementsGPGS() {	}

	@Override
	public void onSignInFailed() {	}

	@Override
	public void onSignInSucceeded() {   }
}