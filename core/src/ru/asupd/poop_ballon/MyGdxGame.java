package ru.asupd.poop_ballon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.asupd.poop_ballon.States.LoadingState;
import ru.asupd.poop_ballon.States.MenuState;
import ru.asupd.poop_ballon.States.PlayState;
import ru.asupd.poop_ballon.Workers.ActionResolver;
import ru.asupd.poop_ballon.Workers.AdsController;
import ru.asupd.poop_ballon.Workers.Assets;
import ru.asupd.poop_ballon.Workers.PlayServices;

import static ru.asupd.poop_ballon.States.PlayState.perfomancecounter;


public class MyGdxGame implements ApplicationListener {
	private GameStateManager gsm;
	private SpriteBatch batch;

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800; // 800
	public static AdsController adsController_my;
	public static ActionResolver actionresolver_my;
	public static PlayServices playServices_my;
    public static boolean showed_ads=false;
	public static void show_banner(){
		adsController_my.showBannerAd();
	}

	public MyGdxGame(AdsController adsController,ActionResolver actionresolver,PlayServices playServices) {
		if (adsController != null) {
			adsController_my = adsController;
		} else {
			adsController_my = new DummyAdsController();
		}
		playServices_my=playServices;
		actionresolver_my=actionresolver;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
        //Gdx.gl20.glEnable(GL30.GL_BLEND);
		Gdx.gl.glClearColor(0, 0, 0, 1);
        PlayState.setUNPAUSE();
		new Gdx().app.postRunnable(new Runnable() {
			@Override
			public void run() {
                if(adsController_my.isOnline())
				{adsController_my.showBannerAd();
				showed_ads=true;}
			}
		});
		gsm.push(new LoadingState(gsm));

	}

    @Override
    public void resize(int width, int height) {

    }

    @Override
	public void render () {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Gdx.gl.glGenBuffer();
        if (gsm!=null) {
            gsm.update(Gdx.graphics.getDeltaTime());
            gsm.render(batch);
        }

        Gdx.gl.glFlush();
	}

	@Override
	public void pause() {
        System.out.println("paused");
		PlayState.setPAUSE();
      //  Assets.instance.dispose();

       //gsm.set(new MenuState(gsm));
		//PlayState.setPAUSE();
      //  gsm = null;
		//

        System.out.println("End pause");
	}

	@Override
	public void resume() {

        //Assets.load();
        //Assets.manager.update();
		//Texture.setAssetManager(manager);
        //System.out.println("Resumed");
       // Assets.instance.load(new AssetManager());
       // gsm = new GameStateManager();
		//gsm.push(new MenuState(gsm));

		//Assets.dispose();
		//Assets.load();
		//Assets.manager.update();
        //Assets.manager.finishLoading();
	}

	@Override
	public void dispose () {
        Assets.instance.dispose();
	}

	private class DummyAdsController implements AdsController {
		@Override
		public void showBannerAd() {
			System.out.println("TRY to showBannerAD");
		}

		@Override
		public void showBannerAd_full() {
			System.out.println("TRY to showBannerAD_full");
		}

		@Override
		public void hideBannerAd() {
			System.out.println("Hide banner");
		}

        @Override
        public boolean isWifiConnected() {
			System.out.println("WIFI");
			return true;
        }

		@Override
		public boolean isOnline() {
			System.out.println("iS online");
			return true;
		}
	}

}
