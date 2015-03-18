package com.example.b_can.FirstAppliSwipe.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.b_can.FirstAppliSwipe.Activities.SimpleGestureFilter.SimpleGestureListener;
import com.example.b_can.FirstAppliSwipe.Model.Message;
import com.example.b_can.FirstAppliSwipe.Model.User;
import com.example.b_can.FirstAppliSwipe.MySQLiteHelper;
import com.example.b_can.FirstAppliSwipe.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by b_can on 17/02/2015.
 */

public class PhotoTaken extends Activity implements SimpleGestureListener{

    private Bitmap myBitmap;
    private Bitmap myBitmapOld;
    private Bitmap newBitmap;
    private EditText edittext;
    private MySQLiteHelper db;
    private String timeStamp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_taken);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        int cameraOrient = b.getInt("camera");
        timeStamp = b.getString("timeStamp");

        db = new MySQLiteHelper(this);

        String dir = "/FlingLike/";
        File myFolder= new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + dir);
        //TODO change path
        File imgFile = new  File(myFolder + "/IMG_" + timeStamp + ".jpg");
        Log.w("PhotoTaken", "File : " + imgFile.toString());
        if(imgFile.exists()){
            Matrix matrix = new Matrix();
            if(cameraOrient==Camera.CameraInfo.CAMERA_FACING_BACK)
                matrix.setRotate(90);
            else {
                //matrix.preScale(-1, 1);
                matrix.setRotate(270);
            }
            myBitmapOld = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.display_img_taken);

            myBitmap = getResizedBitmap(myBitmapOld, 1000);
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);

            newBitmap = myBitmap;
            myImage.setImageDrawable(new BitmapDrawable(myBitmap));
            //myImage.setImageBitmap(myBitmap);
        }

        edittext = (EditText) findViewById(R.id.editTextImgTaken);


        /*edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ImageView myImage = (ImageView) findViewById(R.id.display_img_taken);
                    myImage.setImageBitmap(myBitmap);
                }
                return true; // return is important...
            }
        });*/

        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ImageView myImage2 = (ImageView) findViewById(R.id.display_img_taken);
                    Bitmap.Config config2 = myBitmap.getConfig();
                    int width2 = myBitmap.getWidth();
                    int height2 = myBitmap.getHeight();

                    newBitmap = Bitmap.createBitmap(width2, height2, config2);
                    Canvas canvas = new Canvas(newBitmap);
                    canvas.drawBitmap(myBitmap, 0, 0, null);

                    /*Paint paint2 = new Paint();
                    paint2.setColor(Color.WHITE);
                    RectF rect;

                    rect = new RectF(0, canvas.getHeight()/2 - 30, canvas.getWidth(), canvas.getHeight()/2 + 30);
                    canvas.drawRect(rect, paint2);*/

                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(60);
                    //paint.set;
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(edittext.getText().toString(), canvas.getWidth()/2, canvas.getHeight()/2, paint);

                    myImage2.setImageBitmap(newBitmap);

                    edittext.setText("");
                    return true;
                }
                return false;
            }
        });


        // Add a listener to the taken image
        final ImageView imgTaken = (ImageView) findViewById(R.id.display_img_taken);
        imgTaken.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView myImage = (ImageView) findViewById(R.id.display_img_taken);
                        myImage.setImageBitmap(myBitmap);
                        newBitmap = myBitmap;
                        edittext.requestFocus();
                        ImageView myImage2 = (ImageView) findViewById(R.id.display_img_taken);
                        Bitmap.Config config2 = myBitmap.getConfig();
                        int width2 = myBitmap.getWidth();
                        int height2 = myBitmap.getHeight();

                        newBitmap = Bitmap.createBitmap(width2, height2, config2);
                        Canvas canvas = new Canvas(newBitmap);
                        canvas.drawBitmap(myBitmap, 0, 0, null);

                        Paint paint = new Paint();
                        paint.setColor(Color.BLACK);
                        paint.setTextSize(50);
                        //paint.set;
                        paint.setTextAlign(Paint.Align.CENTER);
                        canvas.drawText(edittext.getText().toString(), canvas.getWidth()/2, canvas.getHeight()/2, paint);

                        myImage2.setImageBitmap(newBitmap);

                        edittext.setText("");
                        hideShowKeyboard();
                    }
                }
        );

        // Add a listener to the Close button
        ImageView closeButton = (ImageView) findViewById(R.id.button_close);
        closeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Come back take photo fragment
                        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(edittext.getApplicationWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        finish();
                    }
                }
        );

        // Add a listener to the Send button
        Button sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Come back take photo fragment
                        Log.d("Photo sent", "Photo is sent to people");
                        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES), "FlingLike");
                        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                                    "IMG_" + timeStamp +".jpg");
                        try {
                            int quality = 100;
                            FileOutputStream fileOutStr = new FileOutputStream(mediaFile);
                            BufferedOutputStream bufOutStr = new BufferedOutputStream(
                                    fileOutStr);
                            newBitmap.compress(Bitmap.CompressFormat.PNG, quality, bufOutStr);
                            bufOutStr.flush();
                            bufOutStr.close();
                            //TODO store in DB
                            User user = db.getUser(1);
                            Message message = new Message();
                            message.setPseudo(user.getPseudo());
                            message.setCivilite("Test");
                            message.setDate(timeStamp);
                            message.setPath(mediaStorageDir.getPath() + File.separator +
                                    "IMG_" + timeStamp +".jpg");
                            db.addMessage(message);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(v.getContext(), AfterSendingPhoto.class);
                        Bundle b = new Bundle();
                        b.putString("timeStamp", timeStamp); //timestamp photo
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                        finish();
                    }
                }
        );

    }

    public void hideShowKeyboard(){
        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(edittext.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                Log.d("Swipe Right", "Swipe Right");
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                Log.d("Swipe Left", "Swipe Left");
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                Log.d("Swipe Down", "Swipe Down");
                break;
            case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
                Log.d("Swipe Up", "Swipe Up");
                break;

        }
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(edittext.getApplicationWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            finish();
        }
        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(edittext.getApplicationWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}