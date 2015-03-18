package com.example.b_can.FirstAppliSwipe.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b_can.FirstAppliSwipe.Activities.DisplayImgActivity;
import com.example.b_can.FirstAppliSwipe.Model.Message;
import com.example.b_can.FirstAppliSwipe.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by b_can on 11/02/2015.
 */

public class MessagesAdapter extends ArrayAdapter<Message> {
    private boolean isSpeakButtonLongPressed = false;
    Intent activityIntent = null;
    private int dayToday;
    private int monthToday;
    private int yearToday;


    public MessagesAdapter(Context context, ArrayList<Message> users) {
        super(context, 0, users);
        //Set the right time text
        Calendar c = Calendar.getInstance();
        this.dayToday = c.get(Calendar.DAY_OF_MONTH);
        //Log.w("Date", "dayToday : " + dayToday);
        this.monthToday = c.get(Calendar.MONTH) + 1;
        //Log.w("Date", "monthToday : " + monthToday);
        this.yearToday = c.get(Calendar.YEAR);
        //Log.w("Date", "yearToday : " + yearToday);
        //Log.w("Date", "day : " + message.getDate().substring(6,8));
        //Log.w("Date", "month : " + message.getDate().substring(4,6));
        //Log.w("Date", "year : " + message.getDate().substring(0,4));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_message, parent, false);
        }

        // Lookup view for data population
        TextView pseudo = (TextView) convertView.findViewById(R.id.messPseudo);
        TextView civilite = (TextView) convertView.findViewById(R.id.messCivilite);
        TextView date = (TextView) convertView.findViewById(R.id.messDate);

        // Populate the data into the template view using the data object
        //Set the pseudo
        pseudo.setText(message.getPseudo());
        //Set the civility
        civilite.setText(message.getCivilite());

        String timeText;

        if(yearToday == Integer.parseInt(message.getDate().substring(0, 4))){
            //Same year
            if(monthToday == Integer.parseInt(message.getDate().substring(4,6))){
                //same month
                if(dayToday == Integer.parseInt(message.getDate().substring(6,8))){
                    //same day
                    timeText = "Today";
                }else{
                    //Less than a month
                    int diff = dayToday - Integer.parseInt(message.getDate().substring(6,8));
                    if (diff == 1){
                        timeText = "Yesterday";
                    }else{
                        timeText = diff + " days ago";
                    }
                }
            }else{
                //more than a month and less than a year
                int diff = monthToday - Integer.parseInt(message.getDate().substring(4,6));
                if (diff == 1){
                    timeText = "A month ago";
                }else{
                    timeText = diff + " months ago";
                }
            }
        }else{
            //more than a year
            int diff = yearToday - Integer.parseInt(message.getDate().substring(0,4));
            if (diff == 1){
                timeText = "A year ago";
            }else{
                timeText = diff + " years ago";
            }
        }

        Log.d("Date received", message.getDate().substring(0,8));
        Log.d("Date today", new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).substring(0,8));
        Log.d("Time text", timeText);

        date.setText(timeText);

        //date.setText(message.getDate());

        LinearLayout MessView = (LinearLayout) convertView.findViewById(R.id.itemListMessage);
        /*MessView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isSpeakButtonLongPressed) {
                    Log.d("Long Pressed", "Item long pressed");
                    activityIntent = new Intent(v.getContext(), DisplayImgActivity.class);
                    v.getContext().startActivity(activityIntent);
                    isSpeakButtonLongPressed = true;
                    return true;
                }
                return false;
            }
        });

        MessView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pEvent) {
                pView.onTouchEvent(pEvent);
                // We're only interested in when the button is released.
                if (pEvent.getAction() == MotionEvent.ACTION_UP) {
                    // We're only interested in anything if our speak button is currently pressed.
                    if (isSpeakButtonLongPressed) {
                        // Do something when the button is released.
                        Log.d("Long Pressed", "Item long pressed released");
                        isSpeakButtonLongPressed = false;

                    }
                }
                return false;
            }
        });*/

        return convertView;
    }

}