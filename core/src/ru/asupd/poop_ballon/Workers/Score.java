package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Voland on 16.09.2017.
 */

public class Score {
    private Texture numbers;//числа
    private Texture plus;//плюсик
    private Array<TextureRegion> frames_numbers;//числа'
    private int[] megred_high_score = new int[5];//для отображения счета
    private int buffer=0;
    private float current_dt=0.0f;
    private float current_dt_combo=0.0f;
    private int local_score=0;
    private boolean combo=false;
    private int combo_num=0;

    private static final float ONE_FRAME_COUNT=0.0025f;
    private static final float COMBO_TIME=0.455f;

    public Score() {
        numbers = new Texture("numbers.png");
        plus = new Texture("plus.png");
        frames_numbers = new Array<TextureRegion>();
        for (int j=0;j<=9;j++){
            frames_numbers.add(new TextureRegion(numbers,j*25,0,25,31));
        }
		buffer=0;

    }
    public void setCombo(int combo_in){
        combo_num=combo_in;
        combo=true;
    }
    public void setScore(int any_score){
        local_score=any_score;
        for (int k=0;k<=4;k++) {
            megred_high_score[k] = any_score % 10;
            any_score = any_score / 10;
        }
    }
    public void update(float dt){
        if (buffer>0){
            current_dt+=dt;
            if (current_dt>=ONE_FRAME_COUNT){
                current_dt=0;
                buffer--;
                local_score++;
                setScore(local_score);
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
    public int getScore(){
        return local_score+buffer;
    }
    public void addScore(int added_score){
        buffer+=added_score;
    }
    public void draw(SpriteBatch sb,int x,int y){
        if (combo){
            sb.draw(plus,x+80,y-3,25+6,31+6);
            switch (combo_num) {
                case 2:
                    sb.draw(frames_numbers.get(4), x + 100 + 6, y - 3, 25 + 6, 31 + 6);
                    break;
                case 3:
                    sb.draw(frames_numbers.get(9), x + 100 + 6, y - 3, 25 + 6, 31 + 6);
                    break;
                case 4:
                    sb.draw(frames_numbers.get(1), x + 100 + 6, y - 3, 25 + 6, 31 + 6);
                    sb.draw(frames_numbers.get(6), x + 120 + 6+6, y - 3, 25 + 6, 31 + 6);
                    break;
                case 5:
                    sb.draw(frames_numbers.get(2), x + 100 + 6, y - 3, 25 + 6, 31 + 6);
                    sb.draw(frames_numbers.get(5), x + 120 + 6+6, y - 3, 25 + 6, 31 + 6);
                    break;
                case 9:
                    sb.draw(frames_numbers.get(5), x + 100 + 6, y - 3, 25 + 6, 31 + 6);
                    sb.draw(frames_numbers.get(0), x + 120 + 6+6, y - 3, 25 + 6, 31 + 6);
                    break;

            }
        }
        sb.draw(frames_numbers.get(megred_high_score[0]),x+60,y,25,31);
        sb.draw(frames_numbers.get(megred_high_score[1]),x+40,y,25,31);
        sb.draw(frames_numbers.get(megred_high_score[2]),x+20,y,25,31);    //245
        sb.draw(frames_numbers.get(megred_high_score[3]),x,y,25,31);    //225
    }
    public int getBuffer() {
        return buffer;
    }
    public void dispose(){

    }
}
