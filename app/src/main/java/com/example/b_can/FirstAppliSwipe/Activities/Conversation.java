package com.example.b_can.FirstAppliSwipe.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b_can.FirstAppliSwipe.Adapters.DiscussArrayAdapter;
import com.example.b_can.FirstAppliSwipe.Model.OneComment;
import com.example.b_can.FirstAppliSwipe.R;
import com.example.b_can.FirstAppliSwipe.Activities.SimpleGestureFilter.SimpleGestureListener;

/**
 * Created by b_can on 12/02/2015.
 */

public class Conversation extends Activity implements SimpleGestureListener{
    private SimpleGestureFilter detector;
    private DiscussArrayAdapter adapter;
    private ListView lv;
    private EditText editText1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        detector = new SimpleGestureFilter(this,this);

        lv = (ListView) findViewById(R.id.listView1);

        adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);

        lv.setAdapter(adapter);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText1.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    adapter.add(new OneComment(false, true, editText1.getText().toString(), null));
                    editText1.setText("");
                    return true;
                }
                return false;
            }
        });

        addItems();
        lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        lv.setSelection(adapter.getCount() - 1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    private void addItems() {
        adapter.add(new OneComment(true, true, "Debut du test de conversation par bulles", null));
        boolean left;
        for (int i = 0; i < 4; i++) {
            if(i== 0 || i==2)
                left = false;
            else
                left = true;
            String words = "Coucou les bubbles" + i;
            adapter.add(new OneComment(left, true, words, null));
        }
        for (int i = 0; i < 4; i++) {
            if(i== 0 || i==2)
                left = false;
            else
                left = true;
            String words = "Milieu de la conversation" + i;
            adapter.add(new OneComment(left, true, words, null));
        }
        for (int i = 0; i < 2; i++) {
            if(i== 0)
                left = false;
            else
                left = true;
            String words = "Ajout de text long pour tester la mise en forme : Contrary to popular belief, " +
                    "Lorem Ipsum is not simply random text. Et encore une fois : Contrary to popular belief, " +
                    "Lorem Ipsum is not simply random text. FIN du text long ! " + i;
            adapter.add(new OneComment(left, true, words, null));
        }
        //We try with image
        for (int i = 0; i < 6; i++) {
            if(i== 0 || i==2 || i==4)
                left = false;
            else
                left = true;
            String words = "Image : " ;
            adapter.add(new OneComment(left, false, words, null));
        }
        for (int i = 0; i < 4; i++) {
            if(i== 0 || i==2)
                left = false;
            else
                left = true;
            String words = "Adieu la conversation" + i;
            adapter.add(new OneComment(left, true, words, null));
        }
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
            finish();
        }
        return true;

    }

}