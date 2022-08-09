package com.jb.mareu.service;

import com.jb.mareu.model.Reunion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReunionService {
    private List<Reunion> mListeDeRencontre = new ArrayList<>();

    public List<Reunion> getListeDeRencontre() {
        return mListeDeRencontre;
    }

    public void setListeDeRencontre(List<Reunion> listeDeRencontre) {
        mListeDeRencontre = listeDeRencontre;
    }

    /**
     * Ajoute une reunion dans la liste de rencontre s'il n'y a pas deja une autre reunion de prevue
     * dans la liste dans la meme salle, a la meme date et a la meme heure.
     * @param reunion La reunion qui sera ajoutee dans la liste de rencontre.
     * @return Retourne une valeur qui indique si la reunion a ete ajoutee dans la liste de rencontre ou pas.
     */
    public boolean ajouterReunion(Reunion reunion){
        String lieuReunionListe;
        LocalDate dateReunionListe;
        LocalTime heureReunionListe;

        String lieuNouvelleReunion = reunion.getLieuReunion().substring(reunion.getLieuReunion().length() - 1);
        LocalDate dateNouvelleReunion = reunion.getDateReunion();
        LocalTime heureNouvelleReunion = reunion.getHeureReunion();

        for(int i = 0; i < mListeDeRencontre.size(); i++){
            lieuReunionListe = mListeDeRencontre.get(i).getLieuReunion().substring(mListeDeRencontre.get(i).getLieuReunion().length() - 1);
            dateReunionListe = mListeDeRencontre.get(i).getDateReunion();
            heureReunionListe = mListeDeRencontre.get(i).getHeureReunion();

            if(lieuReunionListe.equals(lieuNouvelleReunion) && dateReunionListe.equals(dateNouvelleReunion) && heureReunionListe.equals(heureNouvelleReunion)){
                return false;
            }
        }

        mListeDeRencontre.add(reunion);
        return true;
    }

    /**
     * Supprime une reunion dans la liste de rencontre.
     * @param reunion La reunion qui sera supprimer de la liste de rencontre.
     */
    public void supprimerReunion(Reunion reunion){
        mListeDeRencontre.remove(reunion);
    }

    /**
     * Filtre des reunions par date
     * @param DateReunion Date de reunion par laquelle filtrer
     * @return retourne une nouvelle liste de reunion filtree par date
     */
    public List<Reunion> filtrerReunion(LocalDate DateReunion){
        List<Reunion> listeFiltree = new ArrayList<>();

        for(int i = 0; i < mListeDeRencontre.size(); i++){
            if(mListeDeRencontre.get(i).getDateReunion().equals(DateReunion)){
                listeFiltree.add(mListeDeRencontre.get(i));
            }
        }

        return listeFiltree;
    }

    /**
     * Filtre des reunions par lieu
     * @param lieuReunion lieu de reunion par lequel filtrer
     * @return retourne une nouvelle liste de reunion filtree par lieu
     */
    public List<Reunion> filtrerReunion(String lieuReunion){
        List<Reunion> listeFiltree = new ArrayList<>();

        for(int i = 0; i < mListeDeRencontre.size(); i++){
            if(mListeDeRencontre.get(i).getLieuReunion().equals(lieuReunion)){
                listeFiltree.add(mListeDeRencontre.get(i));
            }
        }

        return listeFiltree;
    }

    @Override
    public String toString() {
        return "ReunionService{" +
                "mListeDeRencontre=" + mListeDeRencontre +
                '}';
    }

}
