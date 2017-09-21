package ru.asupd.poop_ballon.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Asup.D on 12.12.2016.
 */

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    public Animation(TextureRegion region,int frameCount,float cycleTime) {
        frames = new Array<TextureRegion>();
        int frameWidth=region.getRegionWidth()/frameCount;
        for (int i=0;i<frameCount;i++){
            frames.add(new TextureRegion(region,i*frameWidth,0,frameWidth,region.getRegionHeight()));
        }
        this.frameCount=frameCount;
        maxFrameTime=cycleTime / frameCount;
        frame =0;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void setCurrentFrameTime(float currentFrameTime) {

        this.currentFrameTime = currentFrameTime;
    }

    public void update(float dt){

        currentFrameTime+=dt;
        if (currentFrameTime>maxFrameTime){
            frame++;
            currentFrameTime=0;
        }
        if (frame>=frameCount){
            frame =0;
        }
    }

    public TextureRegion getFrames() {
        return frames.get(frame);
    }
}