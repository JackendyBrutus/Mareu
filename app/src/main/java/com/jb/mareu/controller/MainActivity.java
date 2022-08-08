package com.jb.mareu.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jb.mareu.R;
import com.jb.mareu.model.Reunion;
import com.jb.mareu.service.ReunionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ReunionService reunionService;
    FloatingActionButton addButton;

    public final static int REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reunionService = new ReunionService();

        //TEST AJOUT D'UN ELEMENT AU PREALABLE
        // reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle 20", "Peach", Collections.singletonList("jackendy@gmail.com")));

        addButton = findViewById(R.id.floatingActionButton_addMeeting);

        recyclerView = findViewById(R.id.activity_mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(reunionService.getListeDeRencontre(), MainActivity.this);

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

            //Log.i("Reunion dans LR", reunionService.getListeDeRencontre().toString() + "");
        }
    }
}