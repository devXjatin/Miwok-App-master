package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

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
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        //resume playback
                        mediaPlayer.start();
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

        //Custom ArrayList to store Data
        final ArrayList<Words> phrases = new ArrayList<>();
        phrases.add(new Words("Where are you going?", "minto wuksus"
                ,R.raw.phrase_where_are_you_going));
        phrases.add(new Words("What is Your Name?", "tinna oyaasina",
                R.raw.phrase_what_is_your_name));
        phrases.add(new Words("My name is", "oyyasit",
                R.raw.phrase_my_name_is));
        phrases.add(new Words("How are you feeling?", "michaksas",
                R.raw.phrase_how_are_you_feeling));
        phrases.add(new Words("I'm feeling good", "kuchi achit",
                R.raw.phrase_im_feeling_good));
        phrases.add(new Words("Are You Coming", "aanas'aa",
                R.raw.phrase_are_you_coming));
        phrases.add(new Words("Yes, I'm Coming", "haa'aanam",
                R.raw.phrase_yes_im_coming));
        phrases.add(new Words("I'm Coming", "aanam",
                R.raw.phrase_im_coming));

        //Custom ArrayAdapter to view on the screen
        WordsAdapter phrasesAdapter = new WordsAdapter(this, phrases,
                R.color.category_phrases);
        ListView listPhrasesView = (ListView) findViewById(R.id.rootListView);
        listPhrasesView.setAdapter(phrasesAdapter);

        listPhrasesView.setOnItemClickListener((parent, view, position, id) -> {
            Words wordsPosition = phrases.get(position);
            releaseMediaPlayer();

            //request to audio Manager
            int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

            if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mediaPlayer = MediaPlayer.create(PhrasesActivity.this,
                        wordsPosition.getAudioResourceId());
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
            audioManager.abandonAudioFocus(audioFocusChangeListener);   // release the audioFocus
        }
    }
}