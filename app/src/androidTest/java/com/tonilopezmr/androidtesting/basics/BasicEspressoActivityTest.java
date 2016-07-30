package com.tonilopezmr.androidtesting.basics;

import android.support.design.widget.Snackbar;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.InputType;
import com.tonilopezmr.androidtesting.BasicEspressoActivity;
import com.tonilopezmr.androidtesting.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BasicEspressoActivityTest {

    /**
     * ActivityTestRule es un JUnit Rule para lanzar la actividad bajo los test.
     *
     * Rules son interceptores que se ejecutan en cada metodo de test, por eso es importante construir
     * blockes independientes de test en cada metodo.
     */
    @Rule
    public ActivityTestRule<BasicEspressoActivity> mainActivityTestRule =
            new ActivityTestRule<>(BasicEspressoActivity.class);


    @Test
    public void
    show_hello_world_first_time() {
        onView(ViewMatchers.withId(R.id.welcome_edittext))
                .check(matches(ViewMatchers.withInputType(InputType.TYPE_NULL)));
        onView(withId(R.id.welcome_edittext))
                .check(matches(withText("Hello World!")));
    }

    @Test
    public void
    change_text_when_click_update_button() {
        onView(withId(R.id.fab))
                .perform(click());

        onView(withId(R.id.welcome_edittext))
                .check(matches(ViewMatchers.withInputType(InputType.TYPE_CLASS_TEXT)));

        onView(withId(R.id.welcome_edittext))
                .perform(replaceText("I'm Toni"))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.fab))
                .perform(click());


        onView(withId(R.id.welcome_edittext))
                .check(matches(ViewMatchers.withInputType(InputType.TYPE_NULL)));
        onView(withId(R.id.welcome_edittext))
                .check(matches(withText("I'm Toni")));
    }

    @Test
    public void
    undo_text_when_click_snackbar_action_button() {
        onView(withId(R.id.fab))
                .perform(click());

        onView(withId(R.id.welcome_edittext))
                .perform(replaceText("Other text"))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.fab))
                .perform(click());
//        onView(withId(R.id.snackbar_action))
//                .perform(click());
        onView(allOf(
                isDescendantOfA(isAssignableFrom(Snackbar.SnackbarLayout.class)), withText("UNDO")))
                .perform(click());

        onView(withId(R.id.welcome_edittext))
                .check(matches(withText("Hello World!")));
    }
}
