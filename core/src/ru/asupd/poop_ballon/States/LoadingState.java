package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Workers.Assets;

/**
 *
 * Created by Asup.D on 13.12.2017.
 */

public class LoadingState extends State {
    Sprite loadind_img=new Sprite(new Texture("loading_screen.png"));
    boolean one=true;
    private BitmapFont Font1=new BitmapFont();;
    //Thread loader;


    public LoadingState(GameStateManager gsm) {
        super(gsm);
        loadind_img.setPosition(0,0);
        one=true;

        /*loader=new Thread(new Runnable() {
            @Override
            public void run() {
                Assets.instance.load(new AssetManager());
            }
        });

        loader.setPriority(10);
        loader.start();*/

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Assets.instance.load(new AssetManager());
            }
        });
    }
    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isTouched()){

        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        loadind_img.draw(sb);

        if (!Assets.instance.manager.update()) {
            Font1.draw(sb, " Loanding >>> " + +Assets.instance.manager.getProgress() * 100 + "%", 15, 730);
        }else
        {
            if (one) {
                one=false;
                gsm.set(new MenuState(gsm));
            }
        }
        sb.end();
    }

    @Override
    public void dispose() {

    }


}
