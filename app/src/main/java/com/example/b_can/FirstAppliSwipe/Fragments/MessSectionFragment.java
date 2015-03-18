package com.example.b_can.FirstAppliSwipe.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.b_can.FirstAppliSwipe.Activities.DisplayImgActivity;
import com.example.b_can.FirstAppliSwipe.Adapters.MessagesAdapter;
import com.example.b_can.FirstAppliSwipe.Model.Message;
import com.example.b_can.FirstAppliSwipe.MySQLiteHelper;
import com.example.b_can.FirstAppliSwipe.R;
import com.example.b_can.FirstAppliSwipe.SwipeToDismiss.SwipeDismissListViewTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b_can on 13/02/2015.
 */

public class MessSectionFragment extends Fragment {

    private ListView mListView;
    private MySQLiteHelper db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_section_mess, container, false);

        this.mListView = (ListView) v.findViewById(R.id.listView01);
        // Construct the data source
        ArrayList<Message> arrayOfMessage = new ArrayList<Message>();
        // Create the adapter to convert the array to views
        final MessagesAdapter adapter = new MessagesAdapter(this.getActivity(), arrayOfMessage);
        mListView.setAdapter(adapter);

        //right message
        db = new MySQLiteHelper(this.getActivity());
        List<Message> listMess =  db.getAllMessages();
        adapter.addAll(listMess);

        //fake messages
        arrayOfMessage = fillArrayMessage();
        adapter.addAll(arrayOfMessage);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        mListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    adapter.remove(adapter.getItem(position));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        },
                        false);
        mListView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        mListView.setOnScrollListener(touchListener.makeScrollListener());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = mListView.getItemAtPosition(position);
                Intent activityIntent = new Intent(getActivity(), DisplayImgActivity.class);
                Bundle b = new Bundle();
                List<Message> listMess =  db.getAllMessages();

                b.putString("path", listMess.get(0).getPath()); //Path
                activityIntent.putExtras(b); //Put your id to your next Intent
                getActivity().startActivity(activityIntent);
            }
        });

        return v;
    }

    private ArrayList<Message> fillArrayMessage() {
        ArrayList<Message> arrayOfMessage = new ArrayList<Message>();

        for (int i=5;i>0;i--){
            Message message = new Message("Test field " + i , "This is a test " + i,"2015022" + i + "_13062455", null);
            arrayOfMessage.add(message);
        }

        for (int i=9;i>0;i--){
            Message message = new Message("Test field " + i , "This is a test " + i,"2015020" + i + "_13062455", null);
            arrayOfMessage.add(message);
        }

        for (int i=9;i>0;i--){
            Message message = new Message("Test field " + i , "This is a test " + i,"2015010" + i + "_13062455", null);
            arrayOfMessage.add(message);
        }

        for (int i=9;i>0;i--){
            Message message = new Message("Test field " + i , "This is a test " + i,"2014100" + i + "_13062455", null);
            arrayOfMessage.add(message);
        }
        // Add item to adapter
        /*Message message1 = new Message("Benjamin", "Male", "Today");
        arrayOfMessage.add(message1);
        Message message2 = new Message("Lena", "Female", "Today");
        arrayOfMessage.add(message2);
        Message message3 = new Message("Marion", "Female", "2 days ago");
        arrayOfMessage.add(message3);
        Message message4 = new Message("Anne", "Female", "2 days ago");
        arrayOfMessage.add(message4);
        Message message5 = new Message("Robert", "Male", "2 days ago");
        arrayOfMessage.add(message5);
        Message message6 = new Message("Jane", "Female", "4 days ago");
        arrayOfMessage.add(message6);
        Message message7 = new Message("David", "Male", "4 days ago");
        arrayOfMessage.add(message7);
        Message message8 = new Message("Lucie", "Female", "1 week ago");
        arrayOfMessage.add(message8);
        Message message9 = new Message("Julie", "Female", "1 week ago");
        arrayOfMessage.add(message9);
        Message message10 = new Message("Pierre", "Male", "1 week ago");
        arrayOfMessage.add(message10);

        Message message11 = new Message("Benjamin", "Male", "1 week ago");
        arrayOfMessage.add(message11);
        Message message12 = new Message("Lena", "Female", "1 week ago");
        arrayOfMessage.add(message12);
        Message message13 = new Message("Marion", "Female", "1 week ago");
        arrayOfMessage.add(message13);
        Message message14 = new Message("Anne", "Female", "2 weeks ago");
        arrayOfMessage.add(message14);
        Message message15 = new Message("Robert", "Male", "2 weeks ago");
        arrayOfMessage.add(message15);
        Message message16 = new Message("Jane", "Female", "4 weeks ago");
        arrayOfMessage.add(message16);
        Message message17 = new Message("David", "Male", "4 weeks ago");
        arrayOfMessage.add(message17);
        Message message18 = new Message("Lucie", "Female", "5 weeks ago");
        arrayOfMessage.add(message18);
        Message message19 = new Message("Julie", "Female", "5 weeks ago");
        arrayOfMessage.add(message19);
        Message message20 = new Message("Pierre", "Male", "5 weeks ago");
        arrayOfMessage.add(message20);*/

        //TODO Do the same thing in JSON
        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.
            /*JSONArray jsonArray = new ;
            ArrayList<Message> newUsers = Message.fromJson(jsonArray)*/

        return arrayOfMessage;
    }


}
