package com.tonilopezmr.androidtesting.list.listview;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.tonilopezmr.androidtesting.R;
import com.tonilopezmr.androidtesting.list.listview.matchers.ListViewItemCounterMatcher;
import com.tonilopezmr.androidtesting.list.listview.matchers.ListViewSortedMatcher;
import com.tonilopezmr.androidtesting.list.view.ListViewActivity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Comparator;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListViewActivityTest {

    /**
     * ActivityTestRule es un JUnit Rule para lanzar la actividad bajo los test.
     *
     * Rules son interceptores que se ejecutan en cada metodo de test, por eso es importante construir
     * blockes independientes de test en cada metodo.
     */
    @Rule
    public ActivityTestRule<ListViewActivity> mainActivityTestRule =
            new ActivityTestRule<>(ListViewActivity.class);

    @Test
    public void
    display_name_in_textview_when_click_in_any_name() throws Exception {
        onData(withValue("Tyrion Lannister"))
                .inAdapterView(withId(R.id.list_view))
                .perform(click());

        onView(withId(R.id.name_textview))
                .check(matches(withText("Tyrion Lannister")));
    }

    @Test
    public void
    display_all_characters_in_list_view() throws Exception {
        //Las dos formas de comprobar lo mismo

        //Forma 1
        onData(withIntance(String.class))
                .inAdapterView(withId(R.id.list_view))
                .atPosition(17)
                .check(matches(isDisplayed()));

        //Forma 2 con un matcher personalizado pero aparte en una clase
        onView(withId(R.id.list_view))
                .check(matches(ListViewItemCounterMatcher.withItemSize(18)));
    }

    @Test
    public void
    sort_by_name_list_view() throws Exception {
        ListViewSortedMatcher<String> matcher = new ListViewSortedMatcher(String.class);
        onData(matcher)
                .atPosition(17)
                .check(matches(matcher.isSorted(new Comparator<String>() {
                    @Override
                    public int compare(String name, String name1) {
                        return name.compareTo(name1);     //En el comparator le digo como espero que está ordenado
                    }
                })));
    }


    /**
     * Matcher vacio que simplemente dice true a cada elemento.
     *
     * @param stringClass
     * @return
     */
    private Matcher<? extends Object> withIntance(Class<String> stringClass) {
        return new BoundedMatcher<Object, String>(stringClass) {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(String item) {
                return true; //Importante true
            }
        };
    }

    private Matcher<? extends Object> withValue(final String characterName) {
        return new BoundedMatcher<Object, String>(String.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("has value " + characterName);
            }

            @Override
            protected boolean matchesSafely(String nameOnList) {
                return nameOnList.equals(characterName);
            }
        };
    }

}