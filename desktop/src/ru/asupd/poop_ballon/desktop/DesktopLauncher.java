package ru.asupd.poop_ballon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.asupd.poop_ballon.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Pop Balloon";
		config.width=MyGdxGame.WIDTH;
		config.height=MyGdxGame.HEIGHT;
		//config.vSyncEnabled=false;
		//config.foregroundFPS = 0;
		//config.forceExit = true;
		new LwjglApplication(new MyGdxGame(null,null,null), config);

	}
}
