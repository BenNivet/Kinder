package com.example.b_can.FirstAppliSwipe.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageView;

import com.example.b_can.FirstAppliSwipe.R;

import java.io.File;

/**
 * Created by b_can on 14/02/2015.
 */

public class DisplayImgActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.display_photo_mess);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String dir = "/FlingLike/";
        File myFolder= new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + dir);
        Bundle b = getIntent().getExtras();
        String path = b.getString("path");
        File imgFile = new  File(path);
        ImageView imgMess = (ImageView)findViewById(R.id.displayImgMess);
        Log.d("Photo Displayed", "File : " + imgFile.toString());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgMess.setImageBitmap(myBitmap);
        }

        //ImageView imgMess = (ImageView)findViewById(R.id.displayImgMess);
        //Log.d("ressources", "ressources après : " + imgMess.getContentDescription());
        //imgMess.setImageResource(R.drawable.ic_launcher);
        //imgMess.setContentDescription("ic_launcher");
        //Log.d("ressources", "ressources après : " + imgMess.getContentDescription());

        //DisplayImageView display = new DisplayImageView(this);
        //display.invalidate();

        imgMess.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pEvent) {
                Log.d("Long Pressed listener", "Beginning of the listener");
                pView.onTouchEvent(pEvent);
                finish();
                return false;
            }
        });

        // Add a listener to the Close button
        ImageView closeButton = (ImageView) findViewById(R.id.button_close2);
        closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;

    }

}
