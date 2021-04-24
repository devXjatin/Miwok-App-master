package com.example.android.miwok;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.numbers).setOnClickListener(this);
        findViewById(R.id.family).setOnClickListener(this);
        findViewById(R.id.colors).setOnClickListener(this);
        findViewById(R.id.phrases).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.numbers:{
                Intent intentNumbers = new Intent(this, NumbersActivity.class);
                startActivity(intentNumbers);
                break;
            }

            case R.id.family:{
                Intent intentFamily = new Intent(this, FamilyMembersActivity.class);
                startActivity(intentFamily);
                break;
            }

            case R.id.colors:{
                Intent intentColors = new Intent(this, ColorsAcitivity.class);
                startActivity(intentColors);
                break;
            }

            case R.id.phrases:{
                Intent intentPhrases = new Intent(this, PhrasesActivity.class);
                startActivity(intentPhrases);
                break;
            }
        }

    }
}



