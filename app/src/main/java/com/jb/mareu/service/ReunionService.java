package com.jb.mareu.service;

import com.jb.mareu.model.Reunion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReunionService {
    private List<Reunion> mListeDeRencontre = new ArrayList<>();

    /**
     * Ajoute une reunion dans la liste de rencontre.
     * @param reunion La reunion qui sera ajoutee dans la liste de rencontre.
     */
    public void ajouterReunion(Reunion reunion){
        mListeDeRencontre.add(reunion);
    }

    /**
     * Modifie une reunion dans la liste de rencontre en comparant les elements d'une reunion de la liste
     * de rencontre avec les elements d'une nouvelle reunion
     * @param indiceReunion L'indice de la reunion qui doit etre modifiee dans la liste de rencontre
     * @param reunion La nouvelle reunion
     */
    public void modifierReunion(int indiceReunion, Reunion reunion){
        if(!mListeDeRencontre.get(indiceReunion).getHeureEtDateReunion().equals(reunion.getHeureEtDateReunion())){
            mListeDeRencontre.get(indiceReunion).setHeureEtDateReunion(reunion.getHeureEtDateReunion());
        }

        if(!mListeDeRencontre.get(indiceReunion).getLieuReunion().equals(reunion.getLieuReunion())){
            mListeDeRencontre.get(indiceReunion).setLieuReunion(reunion.getLieuReunion());
        }

        if(!mListeDeRencontre.get(indiceReunion).getSujetReunion().equals(reunion.getSujetReunion())){
            mListeDeRencontre.get(indiceReunion).setSujetReunion(reunion.getSujetReunion());
        }

        if(mListeDeRencontre.get(indiceReunion).getListeParticipants() != reunion.getListeParticipants()){
            mListeDeRencontre.get(indiceReunion).setListeParticipants(reunion.getListeParticipants());
        }
    }

    /**
     * Supprime une reunion dans la liste de rencontre.
     * @param reunion La reunion qui sera supprimer de la liste de rencontre.
     */
    public void supprimerReunion(Reunion reunion){
        mListeDeRencontre.remove(reunion);
    }

    /**
     * Filtre des reunions par date et heure
     * @param heureEtDateReunion date et heure par lesquelles filtrer
     * @return Retounre une nouvelle liste de reunion filtree par date et heure
     */
    public List<Reunion> filtrerReunion(LocalDateTime heureEtDateReunion){
        List<Reunion> listeFiltree = new ArrayList<>();

        for(int i = 0; i < mListeDeRencontre.size(); i++){
            if(mListeDeRencontre.get(i).getHeureEtDateReunion().equals(heureEtDateReunion)){
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
}
