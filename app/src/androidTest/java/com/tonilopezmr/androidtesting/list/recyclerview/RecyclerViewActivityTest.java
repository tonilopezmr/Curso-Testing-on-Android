package com.tonilopezmr.androidtesting.list.recyclerview;


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.tonilopezmr.androidtesting.R;
import com.tonilopezmr.androidtesting.list.CharacterListProvider;
import com.tonilopezmr.androidtesting.list.recyclerview.matchers.RecyclerViewItemsCountMatcher;
import com.tonilopezmr.androidtesting.list.recyclerview.viewassertion.RecyclerSortedViewAssertion;
import com.tonilopezmr.androidtesting.list.recyclerview.viewassertion.RecyclerViewInteraction;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecyclerViewActivityTest {

    /**
     * ActivityTestRule es un JUnit Rule para lanzar la actividad bajo los test.
     * <p>
     * Rules son interceptores que se ejecutan en cada metodo de test, por eso es importante construir
     * blockes independientes de test en cada metodo.
     */
    @Rule
    public ActivityTestRule<RecyclerViewActivity> mainActivityTestRule =
            new ActivityTestRule<>(RecyclerViewActivity.class);

    @Test
    public void
    show_name_in_textview_when_click_in_a_name() throws Exception {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnHolderItem(withViewHolder("Tyrion Lannister"),
                        click()));

        onView(withId(R.id.name_textview))
                .check(matches(withText("Tyrion Lannister")));
    }

    @Test
    public void
    show_all_characters_size_in_list_view() throws Exception {
        onView(withId(R.id.recycler_view))
                .check(matches(RecyclerViewItemsCountMatcher.recyclerViewHasItemCount(18)));
    }

    @Test
    public void
    show_all_characters_in_list_view() throws Exception {
        List<String> characterNames = CharacterListProvider.characterNames();

        RecyclerViewInteraction.<String>onRecyclerView(withId(R.id.recycler_view))
                .withItems(characterNames)
                .check(new RecyclerViewInteraction.ItemViewAssertion<String>() {
                    @Override
                    public void check(String item, View view, NoMatchingViewException e) {
                        matches(withText(item)).check(view, e);
                    }
                });
    }

    @Test
    public void
    sort_by_name_recycler_view() throws Exception {
        RecyclerSortedViewAssertion.WithAdapter withAdapter = new RecyclerSortedViewAssertion.WithAdapter<String>() {
            @Override
            public List<String> itemsToSort(RecyclerView recyclerView) {
                SimpleAdapter adapter = (SimpleAdapter) recyclerView.getAdapter();
                return adapter.getItems();
            }
        };

        onView(withId(R.id.recycler_view))
                .check(RecyclerSortedViewAssertion.isSorted(withAdapter));
    }

    private Matcher<RecyclerView.ViewHolder> withViewHolder(final String name) {
        return new BoundedMatcher<RecyclerView.ViewHolder,
                SimpleAdapter.StringViewHolder>(SimpleAdapter.StringViewHolder.class) {
            @Override
            protected boolean matchesSafely(SimpleAdapter.StringViewHolder item) {
                return withText(name).matches(item.itemView);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has value " + name);
            }

        };
    }

}