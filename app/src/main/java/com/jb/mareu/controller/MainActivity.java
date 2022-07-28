package com.jb.mareu.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jb.mareu.R;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (FloatingActionButton) findViewById(R.id.floatingActionButton_addMeeting);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent formActivityIntent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(formActivityIntent);
            }
        });

    }
}