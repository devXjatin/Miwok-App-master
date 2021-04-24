package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyMembersActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayerFamily;

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //Custom ArrayList to store Data
        final ArrayList<Words> familyMembers = new ArrayList<>();
        familyMembers.add(new Words("father", "apa",
                R.drawable.family_father, R.raw.family_father));
        familyMembers.add(new Words("mother", "ata",
                R.drawable.family_mother, R.raw.family_mother));
        familyMembers.add(new Words("son", "angsi",
                R.drawable.family_son, R.raw.family_son));
        familyMembers.add(new Words("daughter", "tune",
                R.drawable.family_daughter, R.raw.family_daughter));
        familyMembers.add(new Words("older brother", "taachi",
                R.drawable.family_older_brother, R.raw.family_older_brother));
        familyMembers.add(new Words("younger brother", "chalitti",
                R.drawable.family_younger_brother, R.raw.family_younger_brother));
        familyMembers.add(new Words("older sister", "tete",
                R.drawable.family_older_sister, R.raw.family_older_sister));
        familyMembers.add(new Words("younger sister", "kollete",
                R.drawable.family_younger_sister, R.raw.family_younger_sister));
        familyMembers.add(new Words("grand mother", "ama",
                R.drawable.family_grandmother, R.raw.family_grandmother));
        familyMembers.add(new Words("grand father", "apa",
                R.drawable.family_grandfather, R.raw.family_grandfather));

        //Custom ArrayAdapter to view members on the screen
        WordsAdapter familyAdapter = new WordsAdapter(this, familyMembers,
                R.color.category_family);
        ListView listFamilyMembers = (ListView) findViewById(R.id.rootListView);
        listFamilyMembers.setAdapter(familyAdapter);

        listFamilyMembers.setOnItemClickListener((parent, view, position, id) -> {
            Words familyPosition = familyMembers.get(position);
            releaseMediaPlayer();
            mediaPlayerFamily = MediaPlayer.create(FamilyMembersActivity.this,
                    familyPosition.getAudioResourceId());
            mediaPlayerFamily.start();
            mediaPlayerFamily.setOnCompletionListener(completionListener);
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if(mediaPlayerFamily != null){
            mediaPlayerFamily.release();
            mediaPlayerFamily = null;
        }
    }

}