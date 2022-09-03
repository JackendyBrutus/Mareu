package com.jb.mareu.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Reunion mUneReunion;

    EditText mSujetReunion;
    TextView mSujetReunionHint;

    EditText mDateReunion;
    TextView mDateReunionHint;

    EditText mHeureReunion;
    TextView mHeureReunionHint;

    Spinner mSpinnerLieuReunion;
    TextView mLieuReunionHint;

    EditText mListeParticipants;
    TextView mListeParticipantsHint;

    Button mBouton;

    String sujetDeReunion;
    LocalDate dateDeReunion;
    LocalTime heureDeReunion;
    String lieuDeReunion;
    List<String> listeDeParticipants;

    int positionReunionAModifier;
    TextView mPageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mUneReunion = new Reunion(null, null, null, null, null);

        mSujetReunion = findViewById(R.id.activity_formEditTextMeetingSubject);
        mSujetReunionHint = findViewById(R.id.activity_formTextViewMeetingSubjectHint);

        mDateReunion = findViewById(R.id.activity_formEditTextDate);
        mDateReunionHint = findViewById(R.id.activity_formTextViewDateHint);

        mHeureReunion = findViewById(R.id.activity_formEditTextTime);
        mHeureReunionHint = findViewById(R.id.activity_formTextViewTimeHint);

        mSpinnerLieuReunion = findViewById(R.id.activity_formSpinnerPlace);
        mLieuReunionHint = findViewById(R.id.activity_formTextViewMeetingPlaceHint);

        mListeParticipants = findViewById(R.id.activity_formEditTextParticipantList);
        mListeParticipantsHint = findViewById(R.id.activity_formTextViewMeetingParticipantList);

        mBouton = findViewById(R.id.activity_formButton);

        mPageTitle = findViewById(R.id.activity_formTextViewPageTitle);

        //AJOUT DE TEXTE DANS SPINNER
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_meetingPlace, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerLieuReunion.setAdapter(adapter);

        mSpinnerLieuReunion.setOnItemSelectedListener(this);


        Intent intent = getIntent();
        positionReunionAModifier = intent.getIntExtra("position", -1);

        String couleurReunionAModifier = "green";
        if(positionReunionAModifier >= 0){
            //AJOUT DES DONNÉES RELATIVES A LA RÉUNION DANS LES DIFFERENTS COMPOSANTS
            mUneReunion = MainActivity.reunionService.getListeDeRencontre().get(positionReunionAModifier);
            couleurReunionAModifier = MainActivity.reunionService.getListeDeRencontre().get(positionReunionAModifier).getCouleur();
            mPageTitle.setText(R.string.tv_edit_meeting_title);

            mSujetReunion.setText(mUneReunion.getSujetReunion());
            mDateReunion.setText(mUneReunion.getDateReunion().format(DateTimeFormatter.ofPattern("d/MM/yyyy")));
            mHeureReunion.setText(mUneReunion.getHeureReunion().toString());
            mSpinnerLieuReunion.setSelection(((ArrayAdapter<String>)mSpinnerLieuReunion.getAdapter()).getPosition(getString(R.string.spinner_place) + " " + mUneReunion.getLieuReunion()));
            mListeParticipants.setText((mUneReunion.getListeParticipants().toString()).substring(1, mUneReunion.getListeParticipants().toString().length() - 1));
        }


        String finalCouleurReunionAModifier = couleurReunionAModifier;
        mBouton.setOnClickListener(new View.OnClickListener() {
            //COLLECTE LES DONNEES DES DIFFERENTS COMPOSANTS ET CREE UNE REUNION, PUIS RENVOIS LA REUNION A LA MAIN ACTIVITY
            @Override
            public void onClick(View v) {
                //COLLECTE LES DONNEES DU CHAMP SUJET DE LA REUNION
                if(mSujetReunion.getText().length() > 0){
                    String text = mSujetReunion.getText().toString();
                    //ENLEVE LES CARACTERES ESPACES AU DEBUT ET A LA FIN S'IL EN A
                    if(text.charAt(0) == ' ' || text.charAt(text.length() - 1) == ' '){
                        text = text.trim();
                    }

                    //COLLECTE LES DONNEES S'IL Y EN A, SINON AFFICHE LE MESSAGE HINT
                    if(text.length() > 0){
                        sujetDeReunion = text;
                        mSujetReunionHint.setText("");
                    }
                    else{
                        sujetDeReunion = null;
                        mSujetReunionHint.setText(R.string.tv_mistakeHint_meetingSubject);
                    }
                }
                else{
                    sujetDeReunion = null;
                    mSujetReunionHint.setText(R.string.tv_mistakeHint_meetingSubject);
                }

                //COLLECTE LES DONNEES DU CHAMPS DATE DE LA REUNION
                if(mDateReunion.getText().length() > 0){
                    //SI LA DATE ENTREE EST VALIDE, INITIE LA VARIABLE DE TYPE LOCALDATE A LA DATE ENTRÉE ET ENLÈVE LE MESSAGE HINT
                    try {
                        dateDeReunion = LocalDate.parse(mDateReunion.getText().toString(), DateTimeFormatter.ofPattern("d/MM/yyyy"));
                        mDateReunionHint.setText("");
                    }
                    catch (Exception e){
                        dateDeReunion = null;
                        mDateReunionHint.setText(R.string.tv_hint_meetingDate);
                    }
                }
                else{
                    dateDeReunion = null;
                    mDateReunionHint.setText(R.string.tv_hint_meetingDate);
                }

                //COLLECTE LES DONNEES DU CHAMP HEURE DE LA REUNION
                if(mHeureReunion.getText().length() > 0){
                    //SI L'HEURE ENTREE EST VALIDE, INITIE LA VARIABLE DE TYPE LOCALTIME A L'HEURE ENTRÉE ET ENLÈVE LE MESSAGE HINT
                    try {
                        heureDeReunion = LocalTime.parse(mHeureReunion.getText().toString() + ":00", DateTimeFormatter.ofPattern("H:mm:ss"));
                        mHeureReunionHint.setText("");
                    }
                    catch (Exception e){
                        heureDeReunion = null;
                        mHeureReunionHint.setText(R.string.tv_hint_meetingTime);
                    }
                }
                else{
                    heureDeReunion = null;
                    mHeureReunionHint.setText(R.string.tv_hint_meetingTime);
                }

                //AFFICHE LE MESSAGE HINT SI L'UTILISATEUR N'A PAS FAIT CHOIX D'UNE SALLE
                if(lieuDeReunion == null){
                    mLieuReunionHint.setText(R.string.tv_hint_meetingPlace);
                }

                //COLLECTE LES DONNEES DE LA LISTE DES PARTICIPANTS SI LA LISTE EST VALIDE
                //SI L'UTILISATEUR ENTRE UNE CHAINE QUI CONTIENT AU MOINS 3 CARACTERES
                if(mListeParticipants.getText().toString().length() >= 3){
                    listeDeParticipants = new ArrayList<>();
                    String text = mListeParticipants.getText().toString();
                    StringBuilder adresseMail = new StringBuilder();

                    //PARCOURS LA CHAINE ENTREE PAR L'UTILISATEUR
                    for(int i = 0; i < text.length(); i++){
                        //CONTITUER UNE ADRESSE MAIL ET L'AJOUTER A LA LISTE DE PARTICIPANTS
                        if(text.charAt(i) != ' ' && text.charAt(i) != ','){
                            adresseMail.append(text.charAt(i));
                        }
                        else if(adresseMail.length() >= 3){
                            listeDeParticipants.add(adresseMail.toString());
                            adresseMail = new StringBuilder();
                        }

                        if(i == text.length() - 1 && adresseMail.length() >= 3){
                            listeDeParticipants.add(adresseMail.toString());
                            adresseMail = new StringBuilder();
                        }
                    }

                    //S'IL Y A DES PARTICIPANTS DANS LA LISTE, ENLEVE LE HINT, SINON AFFICHE LE HINT
                    if(!listeDeParticipants.isEmpty()){
                        mListeParticipantsHint.setText("");
                    }
                    else{
                        mListeParticipantsHint.setText(R.string.tv_hint_meetingParticipants);
                    }
                }
                else{
                    //INITIE LA LISTE DE PARTICIPANTS A UNE LISTE VIDE ET AFFICHE LE MESSAGE HINT
                    listeDeParticipants = new ArrayList<>();
                    mListeParticipantsHint.setText(R.string.tv_hint_meetingParticipants);
                }

                //INITIALISE LA REUNION AVEC LES VALEURS ENTREES PAR L'UTILISATEUR ET UNE COULEUR ALEATOIRE, AFFICHE UNE BOITE DE DIALOGUE, PUIS RENVOIE LA REUNION A LA MAIN ACTIVITY
                if(sujetDeReunion != null && dateDeReunion != null && heureDeReunion != null && lieuDeReunion != null && !listeDeParticipants.isEmpty()){
                    mUneReunion = new Reunion(heureDeReunion, dateDeReunion, lieuDeReunion, sujetDeReunion, listeDeParticipants);

                    //AFFICHE BOITE DE DIALOGUE AVEC LES INFORMATION DE LA REUNION
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);

                    builder.setTitle(R.string.form_title)
                            .setMessage(FormActivity.this.getString(
                                    R.string.edt_hint_meetingSubject) + " : " + mUneReunion.getSujetReunion() + "\n" +
                                    FormActivity.this.getString(R.string.tv_meetingDate) + " " + mUneReunion.getDateReunion().format(DateTimeFormatter.ofPattern("d/MM/yyyy")) + "\n" +
                                    FormActivity.this.getString(R.string.tv_meetingHour) + " " + mUneReunion.getHeureReunion() + "\n" +
                                    FormActivity.this.getString(R.string.tv_meetingPlace) + " " + getString(R.string.spinner_place) + " " + mUneReunion.getLieuReunion() + "\n" +
                                    FormActivity.this.getString(R.string.tv_meetingParticipants) + " " + mUneReunion.getListeParticipants().size()
                            )
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //RENVOIE UNE VALEUR A LA MAINACTIVITY QUI INDIQUE SI LA RÉUNION A ÉTÉ AJOUTÉE
                                    Intent intent = new Intent();
                                    if(positionReunionAModifier >= 0){
                                        //MODIFIER UNE REUNION EXISTANTE
                                        mUneReunion.setCouleur(finalCouleurReunionAModifier);
                                        MainActivity.reunionService.getListeDeRencontre().set(positionReunionAModifier, mUneReunion);
                                        //REINITIALISE L'ADAPTER
                                        MainActivity.adapter = new RecyclerViewAdapter(MainActivity.reunionService.getListeDeRencontre(), MainActivity.adapter.context);
                                        MainActivity.recyclerView.setAdapter(MainActivity.adapter);
                                    }
                                    else{
                                        //AJOUTER UNE NOUVELLE REUNION
                                        if(MainActivity.reunionService.ajouterReunion(mUneReunion)){
                                            MainActivity.recyclerView.setAdapter(MainActivity.adapter);

                                            intent.putExtra("ajout", 1);
                                            setResult(Activity.RESULT_OK, intent);
                                        }
                                        else{
                                            intent.putExtra("ajout", 0);
                                            setResult(Activity.RESULT_OK, intent);
                                        }
                                    }

                                    //FERME L'ACTIVITÉ
                                    finish();
                                }
                            })
                            .create()
                            .show();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //COLLECTE LES DONNEES DE LA SALLE
        if(!(parent.getItemAtPosition(position).toString().equals("Choisir salle") || parent.getItemAtPosition(position).toString().equals("Choose room"))){
            lieuDeReunion = parent.getItemAtPosition(position).toString();
            lieuDeReunion = lieuDeReunion.charAt(lieuDeReunion.length() - 1) + "";
            mLieuReunionHint.setText("");
            Toast.makeText(getApplicationContext(), getString(R.string.spinner_place) + " " + lieuDeReunion, Toast.LENGTH_LONG).show();
        }
        else{
            lieuDeReunion = null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}