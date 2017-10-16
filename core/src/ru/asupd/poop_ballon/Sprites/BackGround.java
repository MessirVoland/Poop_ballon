package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import ru.asupd.poop_ballon.Workers.Assets;

import static ru.asupd.poop_ballon.States.PlayState.miss_ball;

/**
 * Created by Asup.D on 16.10.2017.
 */

public class BackGround extends Creature {
    private Array<TextureRegion> background_frames;
    private TextureRegion back_ground_atlas;
    public BackGround() {
        background_frames = new Array<TextureRegion>();
        back_ground_atlas = new TextureRegion(Assets.instance.manager.get(Assets.back_ground_atlas));
        int frameWidth=back_ground_atlas.getRegionWidth()/4;
        for (int i=0;i<4;i++){
            background_frames.add(new TextureRegion(back_ground_atlas,i*frameWidth,0,frameWidth,back_ground_atlas.getRegionHeight()));
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {

    }

    public void draw(SpriteBatch sb) {
        sb.draw(background_frames.get(miss_ball), -25, -25, 550, 900);
    }
}
