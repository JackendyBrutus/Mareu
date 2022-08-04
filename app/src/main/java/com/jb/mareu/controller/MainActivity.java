package com.jb.mareu.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jb.mareu.R;
import com.jb.mareu.model.Reunion;
import com.jb.mareu.service.ReunionService;

public class MainActivity extends AppCompatActivity {

    ReunionService reunionService;
    FloatingActionButton addButton;

    public final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reunionService = new ReunionService();
        addButton = findViewById(R.id.floatingActionButton_addMeeting);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent formActivityIntent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(formActivityIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //RÉCUPÈRE LA REUNION DE LA FORM ACTIVITY ET L'AJOUTE A LA LISTE QUE POSSEDE L'INSTANCE REUNIONSERVICE
            Reunion reunion = (Reunion) data.getSerializableExtra("reunion");
            reunionService.ajouterReunion(reunion);
        }
    }
}