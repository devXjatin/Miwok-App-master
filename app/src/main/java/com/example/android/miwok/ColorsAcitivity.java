package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsAcitivity extends AppCompatActivity {
    private MediaPlayer mediaPlayerColors;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager audioManager;

    //it is used to chane focus
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new
            AudioManager.OnAudioFocusChangeListener(){
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange ==
                            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        //pause playback
                        mediaPlayerColors.pause();
                        mediaPlayerColors.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        //resume playback
                        mediaPlayerColors.start();
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaPlayer();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Words> colors = new ArrayList<>();

        colors.add(new Words("red", "wetetti",
                R.drawable.color_red, R.raw.color_red));
        colors.add(new Words("mustard yellow", "chiwiita",
                R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        colors.add(new Words("dusty yellow", "topiisa",
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colors.add(new Words("green", "chokokki",
                R.drawable.color_green, R.raw.color_green));
        colors.add(new Words("brown", "takakki",
                R.drawable.color_brown, R.raw.color_brown));
        colors.add(new Words("gray", "topoppi",
                R.drawable.color_gray, R.raw.color_gray));
        colors.add(new Words("black", "kululli",
                R.drawable.color_black, R.raw.color_black));
        colors.add(new Words("white", "kelilli",
                R.drawable.color_white, R.raw.color_white));

        /**
         * Adapter two view colors on screen
         */
        WordsAdapter wordsAdapter = new WordsAdapter(this, colors, R.color.category_colors);
        ListView listColorsView = (ListView) findViewById(R.id.rootListView);
        listColorsView.setAdapter(wordsAdapter);

        listColorsView.setOnItemClickListener((parent, view, position, id) -> {
            Words colorsPosition = colors.get(position);
            releaseMediaPlayer();

            //request to the audioFocus
            int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

            //Check audioFocus is granted or not
            if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mediaPlayerColors = MediaPlayer.create(ColorsAcitivity.this,
                        colorsPosition.getAudioResourceId());
                mediaPlayerColors.start();
                mediaPlayerColors.setOnCompletionListener(completionListener);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * to release the memory after completing the sound
     */
    private void releaseMediaPlayer(){
        if(mediaPlayerColors != null){
            mediaPlayerColors.release();
            mediaPlayerColors = null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}