package ru.asupd.poop_ballon.Workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

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

                    System.out.println(ss);
                    long millis = Long.valueOf("25126978");
                    System.out.println(millis);
                    millis*=60;
                    System.out.println(millis);
                    // long longe= Long.parseLong(ss);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(millis);
                    System.out.println(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
                    System.out.println(calendar.get(Calendar.DAY_OF_MONTH) + ":" + calendar.get(Calendar.MONTH));

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
}
