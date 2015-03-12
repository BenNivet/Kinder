package com.example.b_can.FirstAppliSwipe.Activities;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.b_can.FirstAppliSwipe.Fragments.BlankFragment;
import com.example.b_can.FirstAppliSwipe.Fragments.ConvSectionFragment;
import com.example.b_can.FirstAppliSwipe.Fragments.MessSectionFragment;
import com.example.b_can.FirstAppliSwipe.Fragments.TakePhotoFragment;
import com.example.b_can.FirstAppliSwipe.R;

/**
 * Created by cante on 10/02/15.
 */

public class MainActivity extends FragmentActivity {

    private static Camera cameraObject;
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;


    /*
    Function after the connection page to go on the first Fragment
 */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);


        mViewPager.setCurrentItem(1);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                //App Photo fragment
                case 1:
                    Fragment fragmentPhoto = new TakePhotoFragment();
                    Bundle argsPhoto = new Bundle();
                    fragmentPhoto.setArguments(argsPhoto);
                    return fragmentPhoto;
                    /*Fragment blankFragment = new BlankFragment();
                    return blankFragment;*/
                //Mess received fragment
                case 0:
                    Fragment fragmentMess = new MessSectionFragment();
                    Bundle argsMess = new Bundle();
                    fragmentMess.setArguments(argsMess);
                    return fragmentMess;
                //Conversation/favorite fragment
                case 2:
                    Fragment fragmentConv = new ConvSectionFragment();
                    Bundle argsConv = new Bundle();
                    fragmentConv.setArguments(argsConv);
                    return fragmentConv;
                //Mess received fragment
                /*case 0:
                    Fragment fragmentSettings = new SettingsFragment();
                    Bundle argsSettings = new Bundle();
                    fragmentSettings.setArguments(argsSettings);
                    return fragmentSettings;*/
                //App Photo fragment
                default:
                    Fragment fragmentPhotoDef = new TakePhotoFragment();
                    Bundle argsPhotoDef = new Bundle();
                    fragmentPhotoDef.setArguments(argsPhotoDef);
                    return fragmentPhotoDef;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public void displayImgMess(View v){
        Intent activityIntent =  new Intent(v.getContext(), DisplayImgActivity.class);
        startActivity(activityIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==R.id.action_settings) {
            Intent activityIntent =  new Intent(this, Settings.class);
            startActivity(activityIntent);
        }
        return true;

    }

}
