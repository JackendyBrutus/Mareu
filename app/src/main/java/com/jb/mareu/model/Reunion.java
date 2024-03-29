package com.jb.mareu.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Reunion implements Serializable {
    private LocalTime mHeureReunion;
    private LocalDate mDateReunion;
    private String mLieuReunion;
    private String mSujetReunion;
    private List<String> mListeParticipants;
    private String mCouleur;

    public Reunion(LocalTime heureReunion, LocalDate dateReunion, String lieuReunion, String sujetReunion, List<String> listeParticipants) {
        mHeureReunion = heureReunion;
        mDateReunion = dateReunion;
        mLieuReunion = lieuReunion;
        mSujetReunion = sujetReunion;
        mListeParticipants = listeParticipants;
        mCouleur = new Random().nextInt(10) % 2 == 0 ? "pearl" : "green";
    }

    public LocalTime getHeureReunion() {
        return mHeureReunion;
    }

    public void setHeureReunion(LocalTime heureReunion) {
        mHeureReunion = heureReunion;
    }

    public LocalDate getDateReunion() {
        return mDateReunion;
    }

    public void setDateReunion(LocalDate dateReunion) {
        mDateReunion = dateReunion;
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

    public String getCouleur() {
        return mCouleur;
    }

    public void setCouleur(String couleur) {
        mCouleur = couleur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reunion reunion = (Reunion) o;
        return mHeureReunion.equals(reunion.mHeureReunion) && mDateReunion.equals(reunion.mDateReunion) && mLieuReunion.equals(reunion.mLieuReunion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mHeureReunion, mDateReunion, mLieuReunion);
    }

    @Override
    public String toString() {
        return "Reunion{" +
                "mHeureReunion=" + mHeureReunion +
                ", mDateReunion=" + mDateReunion +
                ", mLieuReunion='" + mLieuReunion + '\'' +
                ", mSujetReunion='" + mSujetReunion + '\'' +
                ", mListeParticipants=" + mListeParticipants +
                '}';
    }
}