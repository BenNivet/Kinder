package com.example.b_can.FirstAppliSwipe.Old;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.b_can.FirstAppliSwipe.Model.User;
import com.example.b_can.FirstAppliSwipe.MySQLiteHelper;
import com.example.b_can.FirstAppliSwipe.R;

import java.util.Locale;

/**
 * Created by b_can on 19/02/2015.
 */

public class SettingsFragment extends Fragment {

    private User user;
    private MySQLiteHelper db;
    private EditText pseudo;
    private EditText password;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        db = new MySQLiteHelper(this.getActivity());
        user = db.getUser(1);

        pseudo = (EditText) rootView.findViewById(R.id.pseudoSetting);
        password = (EditText) rootView.findViewById(R.id.passwordSetting);

        pseudo.setText(user.getPseudo());

        final TextView txtSeekBar =(TextView) rootView.findViewById(R.id.textSeekBar);
        txtSeekBar.setText(user.getDistance() + 1 + "Km");

        final SeekBar sk=(SeekBar) rootView.findViewById(R.id.seekBarSettings);
        sk.setProgress(user.getDistance() + 1);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                txtSeekBar.setText(progress +1 + " Km");
            }
        });

        // Add a listener to the show position button
        Button showPosition = (Button) rootView.findViewById(R.id.showPosition);
        showPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SettingsFragment", "Show position in Google Map");
                String uri = String.format(Locale.FRANCE, "geo:43.639983,1.368002");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        //TODO listener on save and store new infos in the local DB
        // Add a listener to the show position button
        Button saveSettings = (Button) rootView.findViewById(R.id.saveSettings);
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
                Log.d("Save setting", "distance user avant :" + user.getDistance());
                user.setDistance(sk.getProgress()-1);
                Log.d("Save setting", "distance user apres :" + user.getDistance());
                db.updateUser(user);
                Log.d("Save setting", "DB updated");
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
                viewPager.setCurrentItem(1);
            }
        });

        return rootView;
    }

}
