package com.example.b_can.FirstAppliSwipe.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.b_can.FirstAppliSwipe.Activities.PhotoTaken;

import com.example.b_can.FirstAppliSwipe.Camera.CameraPreview;
import com.example.b_can.FirstAppliSwipe.R;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by b_can on 12/02/15.
 */

public class TakePhotoFragment extends Fragment {

    View rootView;
    private Camera mCamera;
    private CameraPreview mPreview;
    private MediaRecorder mMediaRecorder;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static int cameraCurrent;
    private boolean longClicked = false;
    private static String timeStamp;
    //private int cameraBack = Camera.CameraInfo.CAMERA_FACING_BACK;
    //private int cameraFront = Camera.CameraInfo.CAMERA_FACING_FRONT;

    public TakePhotoFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Log.w("onPictureTaken", "begin function PictureCallback");
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            Log.w("onPictureTaken", "Image created and returned");
            if (pictureFile == null){
                Log.w("onPictureTaken", "Error creating media file, check storage permissions: " );
                return;
            }
            Log.w("onPictureTaken", "Try to write the image");
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                Log.w("onPictureTaken", "Image written");
            } catch (FileNotFoundException e) {
                Log.w("onPictureTaken", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.w("onPictureTaken", "Error accessing file: " + e.getMessage());
            }
            Log.w("onPictureTaken", "End of this function");
            Intent intent = new Intent(getActivity(), PhotoTaken.class);
            Bundle b = new Bundle();
            b.putInt("camera", cameraCurrent); //Your id
            b.putString("timeStamp", timeStamp); //timestamp photo
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
        }
    };

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));    }


    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        Log.w("getOutputMediaFile", "Begin");

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "FlingLike");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.w("getOutputMediaFile", "failed to create directory");
                return null;
            }
        }

        Log.w("getOutputMediaFile", "Directory created");

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp +".jpg");

            Log.w("MyCameraApp", "Image created : " + mediaFile.toString());
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        Log.w("MyCameraApp", "Image returned");
        return mediaFile;
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        //swap the id of the camera to be used
        if(cameraCurrent == Camera.CameraInfo.CAMERA_FACING_BACK){
            cameraCurrent = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else {
            cameraCurrent = Camera.CameraInfo.CAMERA_FACING_BACK;
        }

        try {
            c = Camera.open(cameraCurrent); // attempt to get a Camera instance

        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.w("getCameraInstance", "Error opening camera: " + e.getMessage());
        }

        Camera.Parameters parameters = c.getParameters();

        if (cameraCurrent == Camera.CameraInfo.CAMERA_FACING_BACK){
            //set output camera mode
            parameters.setPictureFormat(PixelFormat.JPEG);
            //set focous mode
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

            c.setParameters(parameters);
        }else{
            //set output camera mode
            parameters.setPictureFormat(PixelFormat.JPEG);
            //set focous mode
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        c.setDisplayOrientation(90);


        return c; // returns null if camera is unavailable
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_section_photo, container, false);
        Log.w("onCreateView", "We create the view !");

        //The camera will change so we put on front to have back first
        cameraCurrent = Camera.CameraInfo.CAMERA_FACING_FRONT;
        // Create an instance of Camera
        while(mCamera == null)
            mCamera = getCameraInstance();
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this.getActivity(), mCamera);
        FrameLayout preview = (FrameLayout) rootView.findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        Log.w("Camera", mCamera.toString());

        // Add a listener to the Switch camera button
        //Button switchCamera = (Button) rootView.findViewById(R.id.button_switch);
        //switchCamera.setOnClickListener(switchCameraclickListener);

        // Add a listener to the Capture button
        final Button captureButton = (Button) rootView.findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int ms = c.get(Calendar.MILLISECOND);
                timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                timeStamp = timeStamp + ms;
                Log.w("captureclickListener", timeStamp);
                mCamera.takePicture(null, null, mPicture);
                Log.w("captureclickListener", "After click on capture button : " + mPicture.toString());

            }
        });

        //to capture the video if there is a long click
        //captureButton.setOnLongClickListener(captureLongClickListener);

        //to detect the end of the long click
        //captureButton.setOnTouchListener(releaseLongClickVideo);


        /*rootView = inflater.inflate(R.layout.fragment_section_photo, container, false);
        cameraObject = isCameraAvailiable();

        FrameLayout preview = (FrameLayout) rootView.findViewById(R.id.camera_preview);

        showCamera = new ShowCamera(this.getActivity(), cameraObject);
        preview.addView(showCamera);

        captureButton = (Button) rootView.findViewById(R.id.button_capture);

        captureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhotoTaken.class);
                startActivity(intent);
                //snapIt(v);
            }
        });*/

        return rootView;
    }

    View.OnClickListener switchCameraclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mCamera != null){
                mCamera.release();
                mCamera = null;
            }
            FrameLayout preview = (FrameLayout) rootView.findViewById(R.id.camera_preview);
            preview.removeView(mPreview);
            mPreview = null;
            try{
                mCamera = getCameraInstance();
                if(mCamera != null){
                    setCameraDisplayOrientation(getActivity(), cameraCurrent, mCamera);
                    mPreview = new CameraPreview(getActivity(), mCamera);
                    preview.addView(mPreview);
                }
            } catch (Exception e){
                //Log.d("switchCameraclickListener", "Error starting camera preview: " + e.getMessage());
            }

        }
    };


    /*View.OnClickListener captureclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCamera.takePicture(null, null, mPicture);
            Log.d("captureclickListener", "After click on capture button : " + mPicture.toString());
            Intent intent = new Intent(getActivity(), PhotoTaken.class);
            Bundle b = new Bundle();
            b.putInt("camera", cameraCurrent); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
        }
    };*/

    //TODO Capture a video on long click on the capture button
    View.OnLongClickListener captureLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            longClicked = true;
            Log.w("captureclickListener", "Long click on capture button : we'll take a video");
            Toast.makeText(getActivity(), "Long Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    View.OnTouchListener releaseLongClickVideo = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View pView, MotionEvent pEvent) {
            pView.onTouchEvent(pEvent);
            if(longClicked){
                // We're only interested in when the button is released.
                if (pEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.w("releaseLongClickVideo", "Long pressed released, we'll stop the video capture");
                    Toast.makeText(getActivity(), "Long Click released", Toast.LENGTH_SHORT).show();
                    longClicked = false;
                }
            }
            return false;
        }
    };


    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onPause(){
        super.onPause();
        if(mCamera != null){
            mCamera.release();
            mCamera = null;
        }
        FrameLayout preview = (FrameLayout) rootView.findViewById(R.id.camera_preview);
        preview.removeView(mPreview);
        mPreview = null;
        longClicked = false;
    }

    /*@Override
    public void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }*/

    public void onResume(){
        super.onResume();
        try{
            //The camera will change so we put on front to have back first
            Log.w("onResumeTakePhoto", "On resume");
            cameraCurrent = Camera.CameraInfo.CAMERA_FACING_FRONT;
            mCamera = getCameraInstance();
            if(null != mCamera){
                mCamera.setDisplayOrientation(90);
                mPreview = new CameraPreview(getActivity(), mCamera);
                FrameLayout preview = (FrameLayout) rootView.findViewById(R.id.camera_preview);
                preview.addView(mPreview);
            }
        } catch (Exception e){
            Log.w("onResumeTakePhoto", "Error starting camera preview: " + e.getMessage());
        }
    }

    /*@Override
    public void onResume(){
        super.onResume();
        try{
            // Create an instance of Camera
            mCamera = getCameraInstance();
            mPreview = new CameraPreview(this.getActivity(), mCamera);
            FrameLayout preview = (FrameLayout) rootView.findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        } catch (Exception e){
            Log.d("onResumeTakePhotoFragment", "Error starting camera preview: " + e.getMessage());
        }
    }*/

    private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
