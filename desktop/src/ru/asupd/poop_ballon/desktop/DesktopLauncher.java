package ru.asupd.poop_ballon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.asupd.poop_ballon.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Лопни шарик";
		config.width=MyGdxGame.WIDTH;
		config.height=MyGdxGame.HEIGHT;
		new LwjglApplication(new MyGdxGame(), config);

	}
}
