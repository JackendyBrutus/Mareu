package com.jb.mareu.model;

import java.time.LocalDateTime;
import java.util.List;

public class Reunion {
    private LocalDateTime mHeureEtDateReunion;
    private String mLieuReunion;
    private String mSujetReunion;
    private List<String> mListeParticipants;

    public Reunion(LocalDateTime heureEtDateReunion, String lieuReunion, String sujetReunion, List<String> listeParticipants) {
        mHeureEtDateReunion = heureEtDateReunion;
        mLieuReunion = lieuReunion;
        mSujetReunion = sujetReunion;
        mListeParticipants = listeParticipants;
    }

    public LocalDateTime getHeureEtDateReunion() {
        return mHeureEtDateReunion;
    }

    public void setHeureEtDateReunion(LocalDateTime heureEtDateReunion) {
        mHeureEtDateReunion = heureEtDateReunion;
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