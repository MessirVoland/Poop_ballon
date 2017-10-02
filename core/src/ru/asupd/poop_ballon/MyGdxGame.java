package ru.asupd.poop_ballon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.asupd.poop_ballon.States.MenuState;
import ru.asupd.poop_ballon.States.PlayState;
import ru.asupd.poop_ballon.Workers.Assets;


public class MyGdxGame implements ApplicationListener {
	private GameStateManager gsm;
	private SpriteBatch batch;

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800; // 800

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

    @Override
    public void resize(int width, int height) {

    }

    @Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (gsm!=null) {
            gsm.update(Gdx.graphics.getDeltaTime());
            gsm.render(batch);
        }
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
}
