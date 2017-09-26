package ru.asupd.poop_ballon.Workers;

/**
 * класс для динамического изменения размеров
 * Created by Asup.D on 26.09.2017.
 */

public class Resizer {
    float size_x,size_y;
    float local_x,local_y;

    private final static float FIRST_MOVE=0.1f,SECOND_MOVE=0.0666f,THIRD_MOVE=0.05f,FORTH_MOVE=0.333f;
    int position=0;
    float current_dt=0;
    boolean start=false;
    public Resizer(int x,int y) {
        size_x=x;
        size_y=y;
    }
    public void update(float dt){
        if (start){
            //current_dt+=dt;
            switch (position){
                case 0:
                    current_dt+=dt;
                    if (current_dt<FIRST_MOVE){
                        local_x=size_x*((float)((current_dt*1.3)/FIRST_MOVE));
                        local_y=size_y*((float)((current_dt*1.3)/FIRST_MOVE));
                    }else
                    {
                        //current_dt=0;
                        position++;
                    }
                    break;
                case 1:
                    current_dt-=dt;
                    if (current_dt>SECOND_MOVE){
                        local_x=size_x*((float)((current_dt*0.8)/SECOND_MOVE));
                        local_y=size_y*((float)((current_dt*0.8)/SECOND_MOVE));
                    }
                    else
                    {
                        //current_dt=0;
                        position++;
                    }
                    break;
                case 2:
                    current_dt+=dt;
                    if (current_dt<THIRD_MOVE){
                        local_x=size_x*((float)((current_dt*1.1)/THIRD_MOVE));
                        local_y=size_y*((float)((current_dt*1.1)/THIRD_MOVE));
                    }
                    else
                    {
                        //current_dt=0;
                        position++;
                    }
                    break;
                case 3:
                    current_dt-=dt;
                    if (current_dt>FORTH_MOVE){
                        local_x=size_x*((float)((current_dt)/FORTH_MOVE));
                        local_y=size_y*((float)((current_dt)/FORTH_MOVE));
                    }
                    else
                    {
                        current_dt=0;
                        position++;
                        //position=0;
                    }
                    break;
                case 4:
                    current_dt+=dt;
                    if (current_dt>=1.0f)
                    {
                        position=0;
                        current_dt=0;
                    }
                    local_x=size_x;
                    local_y=size_y;
                    break;
            }
        }
    }

    public float getSize_y() {
        return local_y;
    }
    public float getSize_x() {
        return local_x;
    }
    public void start(){
        start=true;
    }

}
