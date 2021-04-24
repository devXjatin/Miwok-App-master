 package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordsAdapter extends ArrayAdapter<Words> {
    private int colorsResourceID;
    public WordsAdapter(Activity activity, ArrayList<Words> words,
                        int colorsResourceID) {
        super(activity,0,words);
        this.colorsResourceID = colorsResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,
                    false);
        }

        Words currentWords = getItem(position);

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwokTextView);
        miwokTextView.setText(currentWords.getMiwokTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.defaultTextView);
        defaultTextView.setText(currentWords.getDefaultTranslation());


        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView);
        if(currentWords.hasImage()) {

            imageView.setImageResource(currentWords.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.linearContainer);
        int color = ContextCompat.getColor(getContext(),colorsResourceID);
        textContainer.setBackgroundColor(color);
        return listItemView;

    }

}
