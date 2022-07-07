package com.jb.mareu.model;

import java.util.List;

public class Reunion {
    private Heure mHeureReunion;
    private String mLieuReunion;
    private String mSujetReunion;
    private List<String> mListeParticipants;

    public Reunion(Heure heureReunion, String lieuReunion, String sujetReunion, List<String> listeParticipants) {
        mHeureReunion = heureReunion;
        mLieuReunion = lieuReunion;
        mSujetReunion = sujetReunion;
        mListeParticipants = listeParticipants;
    }

    public Heure getHeureReunion() {
        return mHeureReunion;
    }

    public void setHeureReunion(Heure heureReunion) {
        mHeureReunion = heureReunion;
    }

    public String getLieuReunion() {
        return mLieuReunion;
    }

    public void setLieuReunion(String lieuReunion) {
        mLieuReunion = lieuReunion;
    }

    public String getSujetReunion() {
        return mSujetReunion;
    }

    public void setSujetReunion(String sujetReunion) {
        mSujetReunion = sujetReunion;
    }

    public List<String> getListeParticipants() {
        return mListeParticipants;
    }

    public void setListeParticipants(List<String> listeParticipants) {
        mListeParticipants = listeParticipants;
    }
}