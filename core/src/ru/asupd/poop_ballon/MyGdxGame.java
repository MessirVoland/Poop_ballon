package ru.asupd.poop_ballon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.asupd.poop_ballon.States.MenuState;

public class MyGdxGame extends ApplicationAdapter {
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
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void pause() {
		super.pause();
       // gsm.push(new GameoverState(gsm));

	}

	@Override
	public void resume() {
		super.resume();
	//	gsm.set(new PlayState(gsm));
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
