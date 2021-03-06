package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import static ru.asupd.poop_ballon.States.MenuState.sound_effects;
import static ru.asupd.poop_ballon.States.PlayState.getCurrent_difficult_up;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.COMBO_TIME;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.ONE_FRAME_COUNT;
import static ru.asupd.poop_ballon.Workers.Base_mechanics.TIME_COUNT_LAST_GAME_SCORE;

/**
 * ускорение счета
 * Created by Voland on 16.09.2017.
 */

public class Score {
    private Texture numbers;//числа
    private Sprite plus;//плюсик

    private Array<Sprite> frames_numbers;//числа'
    private long[] megred_high_score = new long[15];//для отображения счета
    private long buffer=0;
    private float current_dt=0.0f;
    private float current_dt_combo=0.0f;
    private long local_score=0;
    private boolean combo=false;
    private boolean wooden;
    private int current_wooden;
    private int combo_num=0;
    private boolean own_time;
    private float ONE_FRAME_COUNT_local;
    private long faster;



    public Score() {
        own_time=false;
        numbers = new Texture("numbers.png");
        numbers.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        plus =new Sprite(new Texture("plus.png"));
        frames_numbers = new Array<Sprite>();
        for (int j=0;j<=9;j++){
            frames_numbers.add(new Sprite(numbers,j*25,0,25,31));
        }
		buffer=0;
    }
    public Score(float own_time_count){
        own_time=true;
        numbers = new Texture("numbers.png");
        numbers.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        plus =new Sprite(new Texture("plus.png"));
        frames_numbers = new Array<Sprite>();
        for (int j=0;j<=9;j++){

            frames_numbers.add(new Sprite(numbers,j*25,0,25,31));
        }
        buffer=0;
    }

    public void update_render_time(){
        //System.out.println("Frame_time old "+ONE_FRAME_COUNT_local);
        ONE_FRAME_COUNT_local=TIME_COUNT_LAST_GAME_SCORE/(buffer);
        //System.out.println("Frame_time new "+ONE_FRAME_COUNT_local);
        faster=0;
    }
    public void setCombo(int combo_in,boolean wood,int current_wood){
        //if (!combo) {
            combo_num = combo_in;
            wooden = wood;
            //System.out.println("Combo in " +combo_in+" curr_wood "+current_wood+" diff "+getCurrent_difficult_up());
            current_wooden = combo_in * combo_in * current_wood * getCurrent_difficult_up();
            //System.out.println("Added +" + current_wooden);
            combo = true;
            current_dt_combo=0;
      //  }
    }
    public void setScore(long any_score){
        local_score=any_score;
        for (int k=0;k<=6;k++) {
            megred_high_score[k] = any_score % 10;
            any_score = any_score / 10;
        }
    }

    public void update(float dt){
        if (buffer>0){
            current_dt+=dt;
            if (own_time){

                if (current_dt >= ONE_FRAME_COUNT_local) {


                    faster = (int) (current_dt/ONE_FRAME_COUNT_local);
                    current_dt = 0;

                    if (buffer>=faster) {
                        buffer -= faster;
                        local_score+=faster;
                    }else
                    {
                        faster=buffer;
                        buffer=0;
                        local_score+=faster;
                        faster=0;
                    }
                    setScore(local_score);
                }
            }else {
                if (current_dt >= ONE_FRAME_COUNT) {
                    current_dt = 0;
                    buffer--;
                    local_score++;
                    setScore(local_score);
                }
            }
        }
        if (combo){
            current_dt_combo+=dt;
            if (current_dt_combo>=COMBO_TIME){
                combo=false;
                current_dt_combo=0;
            }
        }

    }
    public long getScore(){
        return local_score+buffer;
    }
    public long getScore(boolean with_out_buffer){
        return local_score;
    }
    public void end_count(){
        setScore(local_score+=buffer);
        buffer=0;
    }

    public void addScore(long added_score){
        buffer+=added_score;
    }


    public void draw_center(SpriteBatch sb,int x,int y){

        int sm_pxl=String.valueOf(local_score).length();
        //System.out.println("asd: "+sm_pxl);
        int sm_local=0;
        int peren;

        sm_pxl--;
        x+=40;
        peren= 10+sm_pxl*10;
        switch (sm_pxl){
            case 9:
                sb.draw(frames_numbers.get((int) megred_high_score[9]), x -peren, y);
                peren-=20;
                sm_local++;
            case 8:
                sb.draw(frames_numbers.get((int) megred_high_score[8]), x -peren, y);
                peren-=20;
                sm_local++;
            case 7:
                sb.draw(frames_numbers.get((int) megred_high_score[7]), x -peren, y);
                peren-=20;
                sm_local++;
            case 6:
                sb.draw(frames_numbers.get((int) megred_high_score[6]), x -peren, y);
                peren-=20;
                sm_local++;
            case 5:
                sb.draw(frames_numbers.get((int) megred_high_score[5]), x -peren, y);
                peren-=20;
                sm_local++;
            case 4:
                sb.draw(frames_numbers.get((int) megred_high_score[4]), x -peren, y);
                sm_local++;
                peren-=20;
            case 3:
                sb.draw(frames_numbers.get((int) megred_high_score[3]), x -peren, y);
                sm_local++;
                peren-=20;
            case 2:
                sb.draw(frames_numbers.get((int) megred_high_score[2]), x -peren, y);
                sm_local++;
                peren-=20;
            case 1:
                sb.draw(frames_numbers.get((int)megred_high_score[1]), x -peren, y);
                sm_local++;
                peren-=20;
            case 0:
                sb.draw(frames_numbers.get((int) megred_high_score[0]), x -peren, y);
                break;
        }
    }

    public void draw_center(SpriteBatch sb,int x,int y,int width,int height){

        int sm_pxl=String.valueOf(local_score).length();
        //System.out.println("asd: "+sm_pxl);
        int sm_local=0;
        int peren;

        sm_pxl--;
        x+=40;
        peren= 10+sm_pxl*10;
        switch (sm_pxl){
            case 9:
                sb.draw(frames_numbers.get((int) megred_high_score[9]), x -peren, y,width,height);
                peren-=20;
                sm_local++;
            case 8:
                sb.draw(frames_numbers.get((int) megred_high_score[8]), x -peren, y,width,height);
                peren-=20;
                sm_local++;
            case 7:
                sb.draw(frames_numbers.get((int) megred_high_score[7]), x -peren, y,width,height);
                peren-=20;
                sm_local++;
            case 6:
                sb.draw(frames_numbers.get((int) megred_high_score[6]), x -peren, y,width,height);
                peren-=20;
                sm_local++;
            case 5:
                sb.draw(frames_numbers.get((int) megred_high_score[5]), x -peren, y,width,height);
                peren-=20;
                sm_local++;
            case 4:
                sb.draw(frames_numbers.get((int) megred_high_score[4]), x -peren, y,width,height);
                sm_local++;
                peren-=20;
            case 3:
                sb.draw(frames_numbers.get((int) megred_high_score[3]), x -peren, y,width,height);
                sm_local++;
                peren-=20;
            case 2:
                sb.draw(frames_numbers.get((int) megred_high_score[2]), x -peren, y,width,height);
                sm_local++;
                peren-=20;
            case 1:
                sb.draw(frames_numbers.get((int)megred_high_score[1]), x -peren, y,width,height);
                sm_local++;
                peren-=20;
            case 0:
                sb.draw(frames_numbers.get((int) megred_high_score[0]), x -peren, y,width,height);
                break;
        }
    }

    public void draw(SpriteBatch sb,int x,int y){
        int sm_pxl=String.valueOf(local_score).length();
        int sm_local=0;
        sm_pxl--;
        switch (sm_pxl){
            default:
                sb.draw(frames_numbers.get((int)megred_high_score[sm_pxl]), x + sm_local*20, y);
                sm_local++;
                break;
            case 5:
                sb.draw(frames_numbers.get((int)megred_high_score[5]), x + sm_local*20, y);
                sm_local++;
            case 4:
                sb.draw(frames_numbers.get((int)megred_high_score[4]), x + sm_local*20, y);
                sm_local++;
            case 3:
                sb.draw(frames_numbers.get((int)megred_high_score[3]), x + sm_local*20, y);
                sm_local++;
            case 2:
                sb.draw(frames_numbers.get((int)megred_high_score[2]), x + sm_local*20, y);
                sm_local++;
            case 1:
                sb.draw(frames_numbers.get((int)megred_high_score[1]), x + sm_local*20, y);
                sm_local++;
            case 0:
                sb.draw(frames_numbers.get((int)megred_high_score[0]), x + sm_local*20, y);
                break;
        }


        if (combo){
            sm_pxl++;
            sm_pxl*=20;
            sb.draw(plus,x+sm_pxl,y-3,31,37);
            if (!wooden) {
                switch (combo_num) {
                    case 2:
                        sb.draw(frames_numbers.get(4),x+26+sm_pxl,y - 3, 31, 37);
                        break;
                    case 3:
                        sb.draw(frames_numbers.get(9),x+26+sm_pxl,y - 3, 31, 37);
                        break;
                    case 4:
                        sb.draw(frames_numbers.get(1),x+26+sm_pxl,y - 3, 31, 37);
                        sb.draw(frames_numbers.get(6),x+52+sm_pxl,y - 3, 31, 37);
                        break;
                    case 5:
                        sb.draw(frames_numbers.get(2),x+26+sm_pxl,y - 3, 31, 37);
                        sb.draw(frames_numbers.get(5),x+52+sm_pxl,y - 3, 31, 37);
                        break;
                    case 9:
                        sb.draw(frames_numbers.get(5),x+26+sm_pxl,y - 3, 31, 37);
                        sb.draw(frames_numbers.get(0),x+52+sm_pxl,y - 3, 31, 37);
                        break;


               }
            }else
           {
                int local_current_wood=current_wooden;
                if (current_wooden==0){

                }
                else if (current_wooden/1000>=1)
                {
                    sb.draw(frames_numbers.get(local_current_wood%10), x+128+sm_pxl, y - 3, 31, 37);
                    local_current_wood=local_current_wood/10;
                    sb.draw(frames_numbers.get(local_current_wood%10), x+78 +sm_pxl, y - 3, 31, 37);
                    local_current_wood=local_current_wood/10;
                    sb.draw(frames_numbers.get(local_current_wood%10), x+52 +sm_pxl, y - 3, 31, 37);
                    local_current_wood=local_current_wood/10;
                    sb.draw(frames_numbers.get(local_current_wood),    x+26 +sm_pxl, y - 3, 31, 37);
                }
                else if (current_wooden/100>=1)
                {
                    sb.draw(frames_numbers.get(local_current_wood%10), x+78 +sm_pxl, y - 3, 31, 37);
                    local_current_wood=local_current_wood/10;
                    sb.draw(frames_numbers.get(local_current_wood%10), x+52 +sm_pxl, y - 3, 31, 37);
                    local_current_wood=local_current_wood/10;
                    sb.draw(frames_numbers.get(local_current_wood),    x+26 +sm_pxl, y - 3, 31, 37);
                }
                else if (current_wooden/10>=1)
                {
                    sb.draw(frames_numbers.get(local_current_wood%10), x+52 +sm_pxl, y - 3, 31, 37);
                    local_current_wood=local_current_wood/10;
                    sb.draw(frames_numbers.get(local_current_wood),    x+26 +sm_pxl, y - 3, 31, 37);
                }
                else
                {
                    sb.draw(frames_numbers.get(local_current_wood),    x+26 +sm_pxl, y - 3, 31, 37);
                }

            }
        }



    }
    public long getBuffer() {
        return buffer;
    }
    public void dispose(){
    }
}
