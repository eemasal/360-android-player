package com.emad.android.meidaprojectvideosimplemented;

import android.app.Activity;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.Log;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.StreamingTexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.Quaternion;
import org.rajawali3d.primitives.Sphere;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Emad on 11/29/2017.
 */

public class VideoRenderer2 extends RajawaliCardboardRenderer {

    MainActivity2 mainActivity2;
    String videopath;
    public static MediaPlayer mMediaPlayer;
    private StreamingTexture mVideoTexture;
    String nextVideoPath;
    public static double x;
    public static List<Double> pitchArray;
    public static List<Double>RollArray;
    public static List<Double>YawArray;


    public Sphere sphere;

    public VideoRenderer2(Activity activity) {
        super(activity);

        mainActivity2 = (MainActivity2) activity;

    }



    @Override
    public void initScene() {
        pitchArray =new ArrayList<Double>();
        RollArray =new ArrayList<Double>();
        YawArray =new ArrayList<Double>();

        int [] videos = {R.raw.first, R.raw.second,R.raw.third};
        Random r = new Random();
        int high =3;
        int low = 0;
        int ran =r.nextInt(high-low)+low;


        mMediaPlayer = MediaPlayer.create(getContext(), Splash.videos[Splash.order.get(1)]);

        mMediaPlayer.setLooping(false);

        mVideoTexture = new StreamingTexture("myVideo", mMediaPlayer);
        final Material material = new Material();
        material.setColorInfluence(0);
        try {
            material.addTexture(mVideoTexture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        Texture texture = new Texture("drawableTexture", R.raw.screenshot);


        sphere = new Sphere(50, 64, 32);
        sphere.setScaleX(-1);
        sphere.setMaterial(material);

        final Material material1= new Material();
        try {
            material1.addTexture(texture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        getCurrentScene().addChild(sphere);
        Quaternion quat = new Quaternion(0,0,0,0);
        sphere.setOrientation(quat);

        sphere.setColor(0);
        getCurrentCamera().setPosition(0, 0, 0);

        getCurrentCamera().setFieldOfView(75);

        mMediaPlayer.start();




        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("bis", "video completed");
                mp.stop();
                sphere.setMaterial(material1);
                Timer t= new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mainActivity2.finish();

                            Intent intent = new Intent(getContext(),MainActivity3.class);
                            mainActivity2.startActivity(intent);

                    }
                },5000);


            }
        });

        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                mp.stop();
                mainActivity2.finish();
            }
        });

    }




    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        mVideoTexture.update();
        x=Math.toDegrees(getCurrentCamera().getOrientation().getPitch());
        pitchArray.add(x);
        double y = Math.toDegrees(getCurrentCamera().getOrientation().getRoll());
        RollArray.add(y);
        double z = Math.toDegrees(getCurrentCamera().getOrientation().getYaw());
        YawArray.add(z);
        float x= (float) getRefreshRate();
//        double x = sphere.getOrientation().getPitch();

    }


    @Override
    public void onRenderSurfaceDestroyed(SurfaceTexture surfaceTexture) {
        super.onRenderSurfaceDestroyed(surfaceTexture);
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }



}

