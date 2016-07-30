package com.tonilopezmr.androidtesting.got.app.activity;


import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import com.tonilopezmr.androidtesting.R;
import com.tonilopezmr.androidtesting.got.app.CharacterAdapter;
import com.tonilopezmr.androidtesting.got.di.CharacterInjector;
import com.tonilopezmr.androidtesting.got.model.CharacterRepository;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import com.tonilopezmr.androidtesting.list.recyclerview.viewassertion.RecyclerSortedViewAssertion;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
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
    sort_by_name_when_click_in_sort_menu_button_end_to_end() throws Exception {
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
    show_empty_case_when_there_are_not_characters() throws Exception {
        CharacterRepository repository = mock(CharacterRepository.class);
        CharacterInjector.config(repository);

        given(repository.getAll()).willReturn(Collections.<GoTCharacter>emptyList());

        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.content_loading_progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withText(getString(R.string.not_characters))).check(matches(isDisplayed()));
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