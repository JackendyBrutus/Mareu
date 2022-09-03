package com.jb.mareu.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    public static RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reunionService = new ReunionService();

        // TEST AJOUT DE QUELQUES ELEMENTS AU PREALABLE

        /*
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "A", "Anticonstitutionnellement", Collections.singletonList("jackendy@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "B", "Game", Collections.singletonList("brutus@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "C", "Mario", Collections.singletonList("patricia@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "N", "Luigi", Collections.singletonList("frederic@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "O", "Party", Collections.singletonList("josephine@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "P", "Abreviativement", Collections.singletonList("julia@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "Q", "Constante", Collections.singletonList("adrienne@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "R", "Constitution", Collections.singletonList("rachelle@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "S", "Constitutionnellement", Collections.singletonList("picasso@gmail.com")));
        reunionService.getListeDeRencontre().add(new Reunion(LocalTime.parse("10:00:00"), LocalDate.parse("2022-08-15"), "J", "Over", Collections.singletonList("alicia@gmail.com")));
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
                adapter = new RecyclerViewAdapter(reunionService.getListeDeRencontre(), MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.add_meeting_failure, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filtering_menu, menu);

        MenuItem menuFiltering = menu.findItem(R.id.filtering_menu);
        SearchView searchViewFiltering = (SearchView) menuFiltering.getActionView();
        searchViewFiltering.setMaxWidth(Integer.MAX_VALUE);
        searchViewFiltering.setQueryHint(getString(R.string.menu_filtering));

        Drawable icon = getResources().getDrawable(R.drawable.menu_icon);
        menuFiltering.setIcon(icon).setActionView(searchViewFiltering).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_WITH_TEXT
        );

        searchViewFiltering.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

}