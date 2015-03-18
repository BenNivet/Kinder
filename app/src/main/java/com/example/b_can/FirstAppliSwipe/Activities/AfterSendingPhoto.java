package com.example.b_can.FirstAppliSwipe.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.b_can.FirstAppliSwipe.R;

import java.io.File;

/**
 * Created by b_can on 15/02/2015.
 */

public class AfterSendingPhoto extends Activity {

    private Bitmap myBitmap;
    private Thread thread;
    private int duration = 3000; //3 secondes
    private String timeStamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sending_photo);


        Bundle b = getIntent().getExtras();
        int cameraOrient = b.getInt("camera");
        timeStamp = b.getString("timeStamp");

        ImageView imgSent = (ImageView)findViewById(R.id.after_sending);
        String dir = "/FlingLike/";
        File myFolder= new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + dir);
        File imgFile = new  File(myFolder + "/IMG_" + timeStamp + ".jpg");
        Log.d("PhotoTaken", "File : " + imgFile.toString());
        if(imgFile.exists()){
            Matrix matrix = new Matrix();
            matrix.setRotate(0);
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
            imgSent.setImageBitmap(myBitmap);
            //myImage.setImageBitmap(myBitmap);
        }

        RotateAnimation rotateAnimation1 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation1.setInterpolator(new AccelerateInterpolator());
        rotateAnimation1.setDuration(duration);
        rotateAnimation1.setRepeatCount(0);
        imgSent.startAnimation(rotateAnimation1);

        thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(duration);
                    }
                }catch(InterruptedException ex){
                }
                finish();
            }
        };

        thread.start();

        imgSent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pEvent) {
                Log.d("Begin click", "Finish");
                synchronized(thread){
                    thread.notifyAll();
                }
                pView.onTouchEvent(pEvent);
                finish();
                return false;
            }
        });

    }

}
