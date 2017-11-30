package com.emad.android.meidaprojectvideosimplemented;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    static public ArrayList<Integer> order;
    static final String [] activities = {"MainActivity", "MainActivity2", "MainActivity3" };
    static public int [] videos = {R.raw.first, R.raw.second,R.raw.third};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        order = new ArrayList<>();
        for (int i=0 ; i<3; i++){
            order.add(i);
        }
        Collections.shuffle(order);


        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
        },3000);


    }
}
