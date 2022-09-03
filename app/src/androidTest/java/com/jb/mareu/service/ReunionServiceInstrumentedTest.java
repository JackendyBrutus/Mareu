package com.jb.mareu.service;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.jb.mareu.R;
import com.jb.mareu.controller.MainActivity;
import com.jb.mareu.controller.RecyclerViewAdapter;
import com.jb.mareu.model.Reunion;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;

import android.os.SystemClock;
import android.util.Log;
import android.widget.SearchView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;


/**
 * Test instrumentalisé pour les differentes fonctionnalités implementées dans la classe ReunionService
 */
@RunWith(AndroidJUnit4.class)
public class ReunionServiceInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> intentsTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAjouterReunion() {
        //LANCEMENT DE L'ACTIVITY_FORM
        onView(ViewMatchers.withId(R.id.floatingActionButton_addMeeting)).perform(click());

        //REMPLISSAGE DES CHAMPS DE L'ACTIVITY_FORM POUR AJOUTER UNE REUNION
        onView(ViewMatchers.withId(R.id.activity_formEditTextMeetingSubject)).perform(ViewActions.typeText("Peach"));
        onView(ViewMatchers.withId(R.id.activity_formEditTextDate)).perform(ViewActions.typeText("25/09/2022"));
        onView(ViewMatchers.withId(R.id.activity_formEditTextTime)).perform(ViewActions.typeText("10:30"));

        onView(ViewMatchers.withId(R.id.activity_formSpinnerPlace)).perform(click());
        onData(CoreMatchers.allOf(is(instanceOf(String.class)))).atPosition(1).perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.activity_formEditTextParticipantList)).perform(scrollTo(), ViewActions.typeText("jack@gmail.com, marie@gmail.com"));

        //VALIDATION DE LA REUNION
        onView(ViewMatchers.withId(R.id.activity_formButton)).perform(scrollTo(), click());

        //CONFIRMATION DE LA VALIDATION A TRAVERS LA BOITE DE DIALOGUE
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //VERIFIE QUE LA REUNION A BIEN ETE AJOUTEE DANS LA VUE
        onView(withId(R.id.design_main_recyclerview_info_reunion)).check(matches(withText(
                Locale.getDefault().getLanguage().contentEquals("en") ? "Meeting A - 10h30 - Peach" : "Réunion A - 10h30 - Peach"
        )));

        onView(withId(R.id.design_main_recyclerview_email_reunion)).check(matches(withText("jack@gmail.com, marie@gmail.com")));
    }

    @Test
    public void testSupprimerReunion() throws Throwable {
        //AJOUTER UNE REUNION DANS LA MAINACTIVITY
        intentsTestRule.getActivity().reunionService.ajouterReunion(
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-09-25"),
                        "Salle A",
                        "Peach",
                        new ArrayList<String>(Arrays.asList("jack@gmail.com, marie@lamzone.com".split(",")))
                )
        );

        //REINITIALISER LA VUE
        intentsTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                intentsTestRule.getActivity().adapter = new RecyclerViewAdapter(intentsTestRule.getActivity().reunionService.getListeDeRencontre(), intentsTestRule.getActivity());
                intentsTestRule.getActivity().recyclerView.setAdapter(intentsTestRule.getActivity().adapter);
            }
        });

        //CLICK SUR LE BOUTON SUPPRIMER
        onView(withId(R.id.design_main_recyclerview_delete_reunion)).perform(click());

        //CONFIRMER LA SUPPRESSION DANS LA BOITE DE DIALOGUE
        onView(withText(R.string.delete_meeting_positive_button)).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //VERIFIER QUE LA VUE NE CONTIENT PLUS DE REUNION
        assertTrue(intentsTestRule.getActivity().recyclerView.getAdapter().getItemCount() == 0);
    }

    @Test
    public void testFiltrerReunionParDate() throws Throwable {
        //AJOUT DE 3 REUNIONS DANS LA MAINACTIVITY
        Collections.addAll(intentsTestRule.getActivity().reunionService.getListeDeRencontre(),
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-07-30"),
                        "A",
                        "Peach",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                ),
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-08-17"),
                        "B",
                        "Revue de projet",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                ),
                new Reunion(
                        LocalTime.parse("10:30:00"),
                        LocalDate.parse("2022-07-30"),
                        "C",
                        "Brainstorming",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                )
        );

        //REINITIALISER LA VUE
        intentsTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                intentsTestRule.getActivity().adapter = new RecyclerViewAdapter(intentsTestRule.getActivity().reunionService.getListeDeRencontre(), intentsTestRule.getActivity());
                intentsTestRule.getActivity().recyclerView.setAdapter(intentsTestRule.getActivity().adapter);
            }
        });

        //FILTRER LES REUNIONS PRECEDEMMENT AJOUTEES PAR DATE
        onView(withId(R.id.filtering_menu)).perform(click());
        String hint = intentsTestRule.getActivity().getString(R.string.menu_filtering);
        onView(withHint(hint)).perform(ViewActions.typeText("30/07/2022"));

        //VERIFIER QUE LA VUE NE CONTIENT QUE 2 REUNIONS ET QUE CES 2 REUNION CORRESPONDENT AUX 2 REUNIONS DONT LEURS DATES CORRESPONDENT A LA DATE ENTREE
        assertTrue(intentsTestRule.getActivity().recyclerView.getAdapter().getItemCount() == 2);

        onView(withId(R.id.activity_mainRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.activity_formEditTextDate)).check(matches(withText("30/07/2022")));
    }

    @Test
    public void testFiltrerReunionParLieu() {

    }
}
