package ru.asupd.poop_ballon.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.asupd.poop_ballon.GameStateManager;
import ru.asupd.poop_ballon.Workers.Assets;
import ru.asupd.poop_ballon.Workers.Score;

import static ru.asupd.poop_ballon.MyGdxGame.showed_ads;

/**
 *
 * Created by Asup.D on 13.12.2017.
 */

public class  LoadingState extends State {
    Sprite loadind_img=new Sprite(new Texture("loading_screen.png"));
    boolean one=true;
    private BitmapFont Font1=new BitmapFont();;
    Sprite progress_bar_bgnd= new Sprite(new Texture(Gdx.files.internal("bar.png")));
    NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("loading.9.png")),10,10,10,10);
    Texture proc_t=new Texture("proc.png");
    Sprite proc ;
    Score loading_score=new Score();
    float wait_100;
    //Thread loader;


    public LoadingState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false,480,800 );
        loadind_img.setPosition(0,0);
        wait_100=0;
        proc_t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        proc=new Sprite(proc_t);
        one=true;
        if (showed_ads){
            proc.setPosition(260,310);
            progress_bar_bgnd.setPosition(15,290);
        }else {
            proc.setPosition(260,310);
            progress_bar_bgnd.setPosition(15, 290);
        }
        proc.scale(0.2f);

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
        if (!one){
            wait_100+=dt;
            if (wait_100>=0.5f){
                wait_100=0;
                gsm.set(new MenuState(gsm));
                //gsm.set(new GameoverState(gsm,-190,null));
            }
        }
        if (Gdx.input.isTouched()){

        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        loadind_img.draw(sb);
        progress_bar_bgnd.draw(sb);


        if (!Assets.instance.manager.update()) {
            //Font1.draw(sb, " Loanding >>> " + +Assets.instance.manager.getProgress() * 100 + "%", 15, 730);
            patch.draw(sb, progress_bar_bgnd.getX() + 13, progress_bar_bgnd.getY() + 10,((Assets.instance.manager.getProgress()*405))+20 , 50);
            //loading_score.draw_center(sb, (int) progress_bar_bgnd.getX() + 170, (int) progress_bar_bgnd.getY() + 20);
            loading_score.setScore((long) (Assets.instance.manager.getProgress()*100));
        }else
        {
            //Font1.draw(sb, " Loanding >>> " + +Assets.instance.manager.getProgress() * 100 + "%", 15, 730);
            patch.draw(sb, progress_bar_bgnd.getX() + 13, progress_bar_bgnd.getY() + 10,((1*405))+20 , 50);

            if (one) {
                one=false;
                loading_score.setScore(100);
                //gsm.set(new MenuState(gsm));
            }
        }
        loading_score.draw_center(sb, (int) progress_bar_bgnd.getX() + 170, (int) progress_bar_bgnd.getY() + 20);
        proc.draw(sb);

        sb.end();
    }

    @Override
    public void dispose() {

    }


}
