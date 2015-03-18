package com.example.b_can.FirstAppliSwipe.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.b_can.FirstAppliSwipe.Model.User;
import com.example.b_can.FirstAppliSwipe.MySQLiteHelper;
import com.example.b_can.FirstAppliSwipe.R;

import java.util.Locale;

/**
 * Created by b_can on 25/02/2015.
 */
public class Settings extends Activity {

    private User user;
    private MySQLiteHelper db;
    private EditText pseudo;
    private EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        db = new MySQLiteHelper(this);
        user = db.getUser(1);

        pseudo = (EditText) findViewById(R.id.pseudoSetting);
        password = (EditText) findViewById(R.id.passwordSetting);

        pseudo.setText(user.getPseudo());

        final TextView txtSeekBar =(TextView) findViewById(R.id.textSeekBar);
        txtSeekBar.setText(user.getDistance() + 1 + "Km");

        final SeekBar sk=(SeekBar) findViewById(R.id.seekBarSettings);
        sk.setProgress(user.getDistance() + 1);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                txtSeekBar.setText(progress +1 + " Km");
            }
        });

        // Add a listener to the show position button
        Button showPosition = (Button) findViewById(R.id.showPosition);
        showPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SettingsFragment", "Show position in Google Map");
                String uri = String.format(Locale.FRANCE, "geo:43.639983,1.368002");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        // Add a listener to the show position button
        Button saveSettings = (Button) findViewById(R.id.saveSettings);
        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Save setting", "pseudo entrer :" + pseudo.getText().toString());
                Log.d("Save setting", "pseudo user avant :" + user.getPseudo());
                user.setPseudo(pseudo.getText().toString());
                Log.d("Save setting", "pseudo user apres :" + user.getPseudo());

                Log.d("Save setting", "pseudo entrer :" + pseudo.getText().toString());
                Log.d("Save setting", "password " + password);
                if (password.getText() != null) {
                    Log.d("Save setting", "password entrer" + password.getText());
                    Log.d("Save setting", "password user avant :" + user.getPassword());
                    user.setPassword(password.getText().toString());
                    Log.d("Save setting", "password user apres :" + user.getPassword());
                }
                Log.d("Save setting", "distance user avant :" + user.getDistance() + 1);
                user.setDistance(sk.getProgress());
                Log.d("Save setting", "distance user apres :" + user.getDistance() + 1);
                db.updateUser(user);
                Log.d("Save setting", "DB updated");
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }

}
