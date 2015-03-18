package com.example.b_can.FirstAppliSwipe.Adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b_can.FirstAppliSwipe.Model.OneComment;
import com.example.b_can.FirstAppliSwipe.R;

/**
 * Created by b_can on 11/02/2015.
 */

public class DiscussArrayAdapter extends ArrayAdapter<OneComment>{

    private TextView messageText;
    private ImageView messagePhoto;
    private List<OneComment> listMessages = new ArrayList<OneComment>();
    private LinearLayout wrapper;

    @Override
    public void add(OneComment object) {
        listMessages.add(object);
        super.add(object);
    }

    public DiscussArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.listMessages.size();
    }

    public OneComment getItem(int index) {
        return this.listMessages.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listitem_discuss, parent, false);
        }

        wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

        OneComment coment = getItem(position);

        //If just text in comment
        //if(coment.justText){
            messageText = (TextView) row.findViewById(R.id.commentMessage);
            messageText.setText(coment.comment);
            messageText.setBackgroundResource(coment.left ? R.drawable.bubble_yellow : R.drawable.bubble_green);
        //With photo
        /*}else{
            messagePhoto = (ImageView) row.findViewById(R.id.commentPhoto);
            messageText = (TextView) row.findViewById(R.id.commentMessage);
            messageText.setText(coment.comment + "test2");
            messagePhoto.setImageResource(R.drawable.ic_launcher);
            messagePhoto.setBackgroundResource(coment.left ? R.drawable.bubble_yellow : R.drawable.bubble_green);
        }*/

        wrapper.setGravity(coment.left ? Gravity.LEFT : Gravity.RIGHT);

        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}
