package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Для получения сетевого времени
 * Created by Asup.D on 10.10.2017.
 */

public final class Network_time {
    static {
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl("http://currentmillis.com/time/minutes-since-unix-epoch.php");
        Net.HttpResponseListener request = new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final InputStream resultAsStream = httpResponse.getResultAsStream();
                byte[] b=new byte[100];
                String s="";
                try {
                    resultAsStream.read(b);
                    String ss = new String(b) ;


                    ss=ss.substring(0, 8);
                    System.out.println("Current time: "+ss);

                    //ss="25126978";
                    long millis = Long.valueOf(ss);
                    System.out.println("Current millis "+ millis);
                    millis*=60;
                    System.out.println("Current millis*60 "+ millis);
                    // long longe= Long.parseLong(ss);
                    Calendar calendar = Calendar.getInstance();


                    System.out.println("System date :"+System.currentTimeMillis());
                    System.out.println("Currnt mili :"+ millis*1000);
                    calendar.setTimeInMillis(millis*1000);
                    calendar.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
                    System.out.println("Время: "+(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + calendar.get(Calendar.MINUTE));
                    System.out.println("Дата: "+calendar.get(Calendar.DAY_OF_MONTH) + ":" + calendar.get(Calendar.MONTH));

                } catch (IOException e) {
                    //
                    e.printStackTrace();
                }
                System.out.println("ok");

            }

            @Override
            public void failed(Throwable t) {
                System.out.println(t.toString());

            }

            @Override
            public void cancelled() {
                //

            }
        };
        Gdx.net.sendHttpRequest(httpRequest, request);
    }
    public void get(){

    }
}
