package com.tonilopezmr.androidtesting.got.app.activity;


import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.tonilopezmr.androidtesting.R;
import com.tonilopezmr.androidtesting.got.app.CharacterAdapter;
import com.tonilopezmr.androidtesting.got.di.CharacterInjector;
import com.tonilopezmr.androidtesting.got.model.CharacterRepository;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import com.tonilopezmr.androidtesting.list.recyclerview.viewassertion.RecyclerSortedViewAssertion;
import com.tonilopezmr.androidtesting.list.recyclerview.viewassertion.RecyclerViewInteraction;
import com.tonilopezmr.androidtesting.list.recyclerview.viewassertion.RecyclerViewItemsCountViewAssertion;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {

    /**
     * ActivityTestRule es un JUnit Rule para lanzar la actividad bajo los test.
     *
     * Rules son interceptores que se ejecutan en cada metodo de test, por eso es importante construir
     * blockes independientes de test en cada metodo.
     */
    @Rule
    public ActivityTestRule<HomeActivity> mainActivityTestRule =
            new ActivityTestRule<>(HomeActivity.class,
                    true,               //Touch mode
                    false);             //False para que no se inicie la activity en cada metodo de test, hay que hacerlo manual

    @Test
    public void
    sort_by_name_when_click_in_sort_menu_button_when_end_to_end() throws Exception {
        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.sort_by_name))
                .perform(click());

        RecyclerSortedViewAssertion.WithAdapter withAdapter = new RecyclerSortedViewAssertion.WithAdapter<String>() {
            @Override
            public List<String> itemsToSort(RecyclerView recyclerView) {
                CharacterAdapter adapter = (CharacterAdapter) recyclerView.getAdapter();
                List<String> characterName = getCharacterNames(adapter.getItems());
                return characterName;
            }
        };

        //Al ser un test end-to-end puede que no funcione por otras causas que no sea que no se ha ordenado
        //por eso compruebo que antes no ha habido ningun error, para no confundirme si es error de ordenación o no.
        onView(withText(getString(R.string.error_on_load_characters))).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_view))
                .check(RecyclerSortedViewAssertion.isSorted(withAdapter));
    }

    @Test
    public void
    sort_by_name_when_click_in_sort_menu_button_with_mock() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willReturn(getCharacters());
        given(repository.getSortByName()).willReturn(getCharacterListSorted());

        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.sort_by_name))
                .perform(click());

        RecyclerSortedViewAssertion.WithAdapter withAdapter = new RecyclerSortedViewAssertion.WithAdapter<String>() {
            @Override
            public List<String> itemsToSort(RecyclerView recyclerView) {
                CharacterAdapter adapter = (CharacterAdapter) recyclerView.getAdapter();
                List<String> characterName = getCharacterNames(adapter.getItems());
                return characterName;
            }
        };

        onView(withId(R.id.recycler_view))
                .check(RecyclerSortedViewAssertion.isSorted(withAdapter));
    }

    @Test
    public void
    show_characters_name_when_there_are_characters() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        List<GoTCharacter> characters = getCharacters();
        given(repository.getAll()).willReturn(characters);

        mainActivityTestRule.launchActivity(null);

        RecyclerViewInteraction.<GoTCharacter>onRecyclerView(withId(R.id.recycler_view))
                .withItems(characters)
                .check(new RecyclerViewInteraction.ItemViewAssertion<GoTCharacter>() {
                    @Override
                    public void check(GoTCharacter item, View view, NoMatchingViewException e) {
                        matches(hasDescendant(withText(item.getName()))).check(view, e);
                    }
                });
    }

    @Test
    public void
    show_exact_number_of_characters_when_there_are() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        List<GoTCharacter> characters = getCharacters();
        given(repository.getAll()).willReturn(characters);

        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.recycler_view))
                .check(RecyclerViewItemsCountViewAssertion.withItemCount(characters.size()));
    }

    @Test
    public void
    show_empty_case_when_there_are_not_characters() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willReturn(Collections.<GoTCharacter>emptyList());

        mainActivityTestRule.launchActivity(null);

        onView(withText(getString(R.string.not_characters))).check(matches(isDisplayed()));
    }

    @Test
    public void
    not_show_empty_case_when_there_are_characters() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willReturn(getCharacters());

        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.empty_and_error_case)).check(matches(not(isDisplayed())));
    }

    @Test
    public void
    does_not_show_progress_bar_when_there_are_not_characters() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willReturn(Collections.<GoTCharacter>emptyList());

        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.content_loading_progress_bar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void
    show_add_character_button_when_are_not_characters() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willReturn(Collections.<GoTCharacter>emptyList());

        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.fab_add_character)).check(matches(isDisplayed()));
    }

    @Test
    public void
    show_error_message_when_there_are_any_error() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willThrow(new Exception());

        mainActivityTestRule.launchActivity(null);

        onView(withText(getString(R.string.error_on_load_characters))).check(matches(isDisplayed()));
    }

    @Test
    public void
    not_show_error_message_when_there_are_not_any_error() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willReturn(getCharacters());

        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.empty_and_error_case)).check(matches(not(isDisplayed())));
    }

    @Test
    public void
    does_not_show_progress_bar_when_there_are_any_error() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willThrow(new Exception());

        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.content_loading_progress_bar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void
    show_add_character_button_when_there_are_any_error() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willThrow(new Exception());

        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.fab_add_character)).check(matches(isDisplayed()));
    }

    @Test
    public void
    open_create_character_when_click_in_create_button() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willReturn(getCharacters());

        Intents.init();  //Inicia los intents (no haria falta si ponemos un IntentsTestRule)
        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.fab_add_character))
                .perform(click());
        intended(hasComponent(CreateCharacterActivity.class.getCanonicalName()));
        Intents.release(); //Limpia los intents
    }

    private String getString(@StringRes int stringResource) {
        return InstrumentationRegistry.getTargetContext().getString(stringResource);
    }


    private List<String> getCharacterNames(List<GoTCharacter> items) {
        ArrayList arrayList = new ArrayList();
        for (GoTCharacter item : items) {
            arrayList.add(item.getName());
        }
        return arrayList;
    }

    public List<GoTCharacter> getCharacters() {
        ArrayList<GoTCharacter> characters = new ArrayList<>();
        characters.add(new GoTCharacter("Arya Stark", "url", "desc", "stark"));
        characters.add(new GoTCharacter("Eddard Stark", "url", "desc", "Stark"));
        characters.add(new GoTCharacter("Bronn", "url", "desc", ""));
        characters.add(new GoTCharacter("Daenerys Targaryen", "url", "desc", "Targaryen"));
        characters.add(new GoTCharacter("Cersei Lannister", "url", "desc", "Lannister"));
        return characters;
    }

    public List<GoTCharacter> getCharacterListSorted() {
        ArrayList<GoTCharacter> characters = new ArrayList<>();
        characters.add(new GoTCharacter("Arya Stark", "url", "desc", "stark"));
        characters.add(new GoTCharacter("Bronn", "url", "desc", ""));
        characters.add(new GoTCharacter("Cersei Lannister", "url", "desc", "Lannister"));
        characters.add(new GoTCharacter("Daenerys Targaryen", "url", "desc", "Targaryen"));
        characters.add(new GoTCharacter("Eddard Stark", "url", "desc", "Stark"));
        return characters;
    }
}