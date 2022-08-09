package com.jb.mareu.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jb.mareu.R;
import com.jb.mareu.model.Reunion;
import com.jb.mareu.service.ReunionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    public static ReunionService reunionService;
    FloatingActionButton addButton;

    public final static int REQUEST_CODE = 1;

    public static RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reunionService = new ReunionService();

        // TEST AJOUT DE QUELQUES ELEMENTS AU PREALABLE
        /*
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Salle Z", "Peach", Collections.singletonList("jackendy@gmail.com")));

         */
        //


        addButton = findViewById(R.id.floatingActionButton_addMeeting);

        recyclerView = findViewById(R.id.activity_mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(reunionService.getListeDeRencontre(), MainActivity.this);
        recyclerView.setAdapter(adapter);

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
            //RÉCUPÈRE L'ENTIER QUI PERMET DE SAVOIR SI LA REUNION A ETE AJOUTÉE
            int valeur = data.getIntExtra("ajout", 0);

            //AFFICHE LE MESSAGE APPROPRIÉ A L'UTILISATEUR
            if(valeur == 1){
                Toast.makeText(getApplicationContext(), R.string.add_meeting_success, Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.add_meeting_failure, Toast.LENGTH_LONG).show();
            }
        }
    }

}