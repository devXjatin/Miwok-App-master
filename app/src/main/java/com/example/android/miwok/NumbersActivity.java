package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    /**
     * AudioManger is used for shift audio when other activity going to happen
     */
    AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //pause playback
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //resume playback
                mediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //stop playback
                releaseMediaPlayer();

            }

        }
    };
    /**
     * setOnCompeletionListener()-> is used when we want to give a notification when it
     *                              become finish.
     */
    private MediaPlayer.OnCompletionListener completionListener = new
            MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        //get service to the audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Insert Value in ArraysList
        final ArrayList<Words> words = new ArrayList<>();

        words.add(new Words("One", "lutti", R.drawable.number_one,
                R.raw.number_one));
        words.add(new Words("Two", "otiiko", R.drawable.number_two,
                R.raw.number_two));
        words.add(new Words("Three", "tolookasu",
                R.drawable.number_three, R.raw.number_three));
        words.add(new Words("Four", "oyyisa",
                R.drawable.number_four, R.raw.number_four));
        words.add(new Words("Five", "massokka",
                R.drawable.number_five, R.raw.number_five));
        words.add(new Words("Six", "temmokka",
                R.drawable.number_six, R.raw.number_six));
        words.add(new Words("Seven", "kenekaku",
                R.drawable.number_seven, R.raw.number_seven));
        words.add(new Words("Eight", "kawinta",
                R.drawable.number_eight, R.raw.number_eight));
        words.add(new Words("Nine", "wo e",
                R.drawable.number_nine, R.raw.number_nine));
        words.add(new Words("Ten", "na' accha",
                R.drawable.number_ten, R.raw.number_ten));

        /**
         *ArrayAdapter is used to view the list in the recycle view
         *
         */
        WordsAdapter wordsAdapter = new WordsAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.rootListView);
        listView.setAdapter(wordsAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words wordPosition = words.get(position);
                releaseMediaPlayer();

                //request to our audio manager
                int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                );
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this,
                            wordPosition.getAudioResourceId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(completionListener);
                }


        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);  //release the audioFocus
        }
    }
}