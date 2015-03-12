package com.example.b_can.FirstAppliSwipe.Activities;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b_can.FirstAppliSwipe.R;

/**
 * Created by cante on 10/02/15.
 */

public class ConnectPage extends Activity {

    private static Camera cameraObject;
    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;
    private EditText username=null;
    private EditText  password=null;
    private Button signIn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        signIn = (Button)findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("") &&
                        password.getText().toString().equals("")){
                    //Toast.makeText(getApplicationContext(), "Redirecting...",
                    //   Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConnectPage.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid ID or password.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
