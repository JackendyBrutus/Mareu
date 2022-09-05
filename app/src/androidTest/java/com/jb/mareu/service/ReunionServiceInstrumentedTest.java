package com.jb.mareu.service;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions;
import com.adevinta.android.barista.interaction.BaristaListInteractions;
import com.jb.mareu.R;
import com.jb.mareu.controller.MainActivity;
import com.jb.mareu.controller.RecyclerViewAdapter;
import com.jb.mareu.model.Reunion;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
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
        //GIVEN
        onView(ViewMatchers.withId(R.id.floatingActionButton_addMeeting)).perform(click());
        onView(ViewMatchers.withId(R.id.activity_formEditTextMeetingSubject)).perform(ViewActions.typeText("Peach"));
        onView(ViewMatchers.withId(R.id.activity_formEditTextDate)).perform(ViewActions.typeText("25/09/2022"));
        onView(ViewMatchers.withId(R.id.activity_formEditTextTime)).perform(ViewActions.typeText("10:30"));
        onView(ViewMatchers.withId(R.id.activity_formSpinnerPlace)).perform(click());
        onData(CoreMatchers.allOf(is(instanceOf(String.class)))).atPosition(1).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.activity_formEditTextParticipantList)).perform(scrollTo(), ViewActions.typeText("jack@gmail.com, marie@gmail.com"), ViewActions.closeSoftKeyboard());

        //WHEN
        onView(ViewMatchers.withId(R.id.activity_formButton)).perform(scrollTo(), click());
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //THEN
        onView(withId(R.id.design_main_recyclerview_info_reunion)).check(matches(withText(
                Locale.getDefault().getLanguage().contentEquals("en") ? "Meeting A - 10h30 - Peach" : "Réunion A - 10h30 - Peach"
        )));
        onView(withId(R.id.design_main_recyclerview_email_reunion)).check(matches(withText("jack@gmail.com, marie@gmail.com")));
    }

    @Test
    public void testSupprimerReunion() throws Throwable {
        //GIVEN
        Reunion a = new Reunion(
                LocalTime.parse("10:30:00"),
                LocalDate.parse("2022-09-25"),
                "Salle A",
                "Peach",
                new ArrayList<String>(Arrays.asList("jack@gmail.com, marie@lamzone.com".split(",")))
        );

        Reunion b = new Reunion(
                LocalTime.parse("12:00:00"),
                LocalDate.parse("2022-12-20"),
                "Salle B",
                "Conference",
                new ArrayList<String>(Arrays.asList("jack@gmail.com, marie@lamzone.com".split(",")))
        );

        intentsTestRule.getActivity().reunionService.ajouterReunion(a);
        intentsTestRule.getActivity().reunionService.ajouterReunion(b);

        intentsTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                intentsTestRule.getActivity().adapter = new RecyclerViewAdapter(intentsTestRule.getActivity().reunionService.getListeDeRencontre(), intentsTestRule.getActivity());
                intentsTestRule.getActivity().recyclerView.setAdapter(intentsTestRule.getActivity().adapter);
            }
        });

        //WHEN
        BaristaListInteractions.clickListItemChild(R.id.activity_mainRecyclerView, 0, R.id.design_main_recyclerview_delete_reunion);
        onView(withText(R.string.delete_meeting_positive_button)).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //THEN
        assertTrue(intentsTestRule.getActivity().recyclerView.getAdapter().getItemCount() == 1);
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.activity_mainRecyclerView, 1);
        assertEquals(b, intentsTestRule.getActivity().reunionService.getListeDeRencontre().get(0));
    }

    @Test
    public void testFiltrerReunionParDate() throws Throwable {
        //GIVEN
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

        intentsTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                intentsTestRule.getActivity().adapter = new RecyclerViewAdapter(intentsTestRule.getActivity().reunionService.getListeDeRencontre(), intentsTestRule.getActivity());
                intentsTestRule.getActivity().recyclerView.setAdapter(intentsTestRule.getActivity().adapter);
            }
        });

        //WHEN
        onView(withId(R.id.filtering_menu)).perform(click());
        String hint = intentsTestRule.getActivity().getString(R.string.menu_filtering);
        onView(withHint(hint)).perform(ViewActions.typeText("17/08/2022"), ViewActions.closeSoftKeyboard());

        //THEN
        assertTrue(intentsTestRule.getActivity().recyclerView.getAdapter().getItemCount() == 1);
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.activity_mainRecyclerView, 1);
        onView(withId(R.id.design_main_recyclerview_info_reunion)).check(matches(withText(startsWith(intentsTestRule.getActivity().getString(R.string.form_title) + " B"))));
    }

    @Test
    public void testFiltrerReunionParLieu() throws Throwable {
        //GIVEN
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
                        "A",
                        "Brainstorming",
                        new ArrayList<String>(){{
                            add("jb@gmail.com");
                            add("mz@gmail.com");
                        }}
                )
        );

        intentsTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                intentsTestRule.getActivity().adapter = new RecyclerViewAdapter(intentsTestRule.getActivity().reunionService.getListeDeRencontre(), intentsTestRule.getActivity());
                intentsTestRule.getActivity().recyclerView.setAdapter(intentsTestRule.getActivity().adapter);
            }
        });

        //WHEN
        onView(withId(R.id.filtering_menu)).perform(click());
        String hint = intentsTestRule.getActivity().getString(R.string.menu_filtering);
        onView(withHint(hint)).perform(ViewActions.typeText("B"));

        //THEN
        assertTrue(intentsTestRule.getActivity().recyclerView.getAdapter().getItemCount() == 1);
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.activity_mainRecyclerView, 1);
        onView(withId(R.id.design_main_recyclerview_info_reunion)).check(matches(withText(startsWith(intentsTestRule.getActivity().getString(R.string.form_title) + " B"))));
    }
}
