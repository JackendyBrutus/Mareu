package com.jb.mareu;

import org.junit.Test;

import static org.junit.Assert.*;

import com.jb.mareu.model.Reunion;
import com.jb.mareu.service.ReunionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Test unitaire local pour les differentes fonctionnalités implementées dans la classe ReunionService
 */
public class ReunionServiceUnitTest {
    @Test
    public void testAjouterReunion() {
        //Créer une nouvelle instance de la classe ReunionService
        ReunionService reunionService = new ReunionService();

        // Créer et initialiser une liste de participant a une réunion
        List<String> listeParticipants = new ArrayList<String>(){{
            add("jb@gmail.com");
            add("mz@gmail.com");
        }};

        // Créer une nouvelle instance de la classe Reunion
        Reunion uneReunion = new Reunion(
                LocalTime.parse("10:30:00"),
                LocalDate.parse("2022-07-30"),
                "Salle A",
                "Peach",
                listeParticipants
        );

        // Ajouter la réunion précédemment créer a la liste de rencontre qui se trouve dans reunionService
        reunionService.ajouterReunion(uneReunion);

        // Vérifier que la liste de l'instance reunionService contient la réunion qui a été ajoutée
        assertEquals(uneReunion, reunionService.getListeDeRencontre().get(0));
    }

    @Test
    public void testSupprimerReunion() {
        //Créer une nouvelle instance de la classe ReunionService
        ReunionService reunionService = new ReunionService();

        // Créer une nouvelle instance de la classe Reunion
        Reunion uneReunion = new Reunion(
                LocalTime.parse("10:30:00"),
                LocalDate.parse("2022-07-30"),
                "Salle A",
                "Peach",
                new ArrayList<String>(){{
                    add("jb@gmail.com");
                    add("mz@gmail.com");
                }}
        );

        // Ajouter la nouvelle instance de la classe Reunion dans la liste de rencontre de la nouvelle instance de la classe ReunionService
        reunionService.getListeDeRencontre().add(uneReunion);

        // Supprimer l'instance de la classe réunion qui vient d'etre ajoutée
        reunionService.supprimerReunion(uneReunion);

        // Vérifier que la liste de rencontre de l'instance reunionService ne contient plus la réunion qui a été supprimée
        assertTrue(reunionService.getListeDeRencontre().isEmpty());
    }

    @Test
    public void testFiltrerReunionParDate() {
        // Créer une nouvelle instance de la classe ReunionService
        ReunionService reunionService = new ReunionService();

        // Ajouter 3 réunions dans la liste de rencontre de la nouvelle instance de la classe ReunionService
        Collections.addAll(reunionService.getListeDeRencontre(),
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-07-30"),
                        "Salle A",
                        "Peach",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                ),
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-08-17"),
                        "Salle B",
                        "Revue de projet",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                ),
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-07-30"),
                        "Salle C",
                        "Brainstorming",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                ));

        // Filtrer les réunions précédemment ajoutées par date
        List<Reunion> listeFiltree = reunionService.filtrerReunion(DateTimeFormatter.ofPattern("dd/MM/YYYY").format(LocalDate.parse("2022-07-30")));

        /* Verifier que la liste filtree ne contient que 2 elements et que ces 2 elements correspondent
         aux 2 elements de la liste de rencontre de l'instance de la classe reunionService dont leurs dates
         correspondent bien a la date passée en paramètre a la méthode filtrerReunion */
        assertTrue(listeFiltree.size() == 2 &&
                listeFiltree.get(0).equals(reunionService.getListeDeRencontre().get(0)) &&
                listeFiltree.get(1).equals(reunionService.getListeDeRencontre().get(2)));
    }

    @Test
    public void filtrerReunionParLieu() {
        // Créer une nouvelle instance de la classe ReunionService
        ReunionService reunionService = new ReunionService();

        // Ajouter 3 réunions dans la liste de rencontre de la nouvelle instance de la classe ReunionService
        Collections.addAll(reunionService.getListeDeRencontre(),
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-07-30"),
                        "Salle A",
                        "Peach",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                ),
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-08-17"),
                        "Salle B",
                        "Revue de projet",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                ),
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-09-14"),
                        "Salle A",
                        "Brainstorming",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                ));

        // Filtrer les réunions précédemment ajoutées par lieu
        List<Reunion> listeFiltree = reunionService.filtrerReunion("Salle A");

        /* Verifier que la liste filtree ne contient que 2 elements et que ces 2 elements correspondent
         aux 2 elements de la liste de rencontre de l'instance de la classe reunionService dont leurs lieux
         correspondent bien au lieu passé en paramètre a la méthode filtrerReunion */
        assertTrue(listeFiltree.size() == 2 &&
                listeFiltree.get(0).equals(reunionService.getListeDeRencontre().get(0)) &&
                listeFiltree.get(1).equals(reunionService.getListeDeRencontre().get(2)));
    }
}