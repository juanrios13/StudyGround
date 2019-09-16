package com.futureapp.studyground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ProfileMainActivity extends AppCompatActivity {

    private ImageButton teach;
    private ImageButton study;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_profile_main);

        teach=(ImageButton) findViewById(R.id.teachButton);
        study=(ImageButton) findViewById(R.id.studyButton);

        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileMainActivity.this, StudyActivity.class);
                startActivity(intent);

            }
        });

        teach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileMainActivity.this, StudyActivity.class);
                startActivity(intent);

            }
        });

    }
}
