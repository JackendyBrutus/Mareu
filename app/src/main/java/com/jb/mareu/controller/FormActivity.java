package com.jb.mareu.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jb.mareu.R;
import com.jb.mareu.model.Reunion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Reunion mUneReunion;

    EditText mSujetReunion;

    EditText mDateReunion;
    TextView mDateHint;

    EditText mHeureReunion;
    TextView mHeureHint;

    Spinner mSpinnerLieuReunion;

    EditText mListeParticipants;

    Button mBouton;

    String sujetDeReunion;
    LocalDate dateDeReunion;
    LocalTime heureDeReunion;
    String lieuDeReunion;
    List<String> listeDeParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mSujetReunion = findViewById(R.id.activity_formEditTextMeetingSubject);

        mDateReunion = findViewById(R.id.activity_formEditTextDate);
        mDateHint = findViewById(R.id.activity_formTextViewDate);

        mHeureReunion = findViewById(R.id.activity_formEditTextTime);
        mHeureHint = findViewById(R.id.activity_formTextViewTime);

        mSpinnerLieuReunion = findViewById(R.id.activity_formSpinnerPlace);

        mListeParticipants = findViewById(R.id.activity_formEditTextParticipantList);

        mBouton = findViewById(R.id.activity_formButton);

        //AJOUT DE TEXTE DANS SPINNER
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_meetingPlace, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerLieuReunion.setAdapter(adapter);

        mSpinnerLieuReunion.setOnItemSelectedListener(this);

        mBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // GET DATA FROM WIDGETS AND CONSTRUCT A NEW  MEETING, THEN INITIALISE mUneReunion TO THE NEW MEETING

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!(parent.getItemAtPosition(position).toString().equals("Choisir salle") || parent.getItemAtPosition(position).toString().equals("Choose room"))){
            lieuDeReunion = parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(), lieuDeReunion, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}