package com.tonilopezmr.androidtesting.list.recyclerview.viewassertion;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.hamcrest.StringDescription;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Versión de un ItemsCount para recyclerView hecho con ViewAssertion normales de hamcrest.
 *
 */
public class RecyclerViewItemsCountViewAssertion implements ViewAssertion {

    private int expectedCount;

    public RecyclerViewItemsCountViewAssertion(int expectedCount) {
        this.expectedCount = expectedCount;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewException) {
        StringDescription description = new StringDescription();
        checkIsNull(noViewException, description);

        RecyclerView recyclerView = (RecyclerView) view;
        int itemCount = recyclerView.getAdapter().getItemCount();
        description.appendText("expected item count size: " + expectedCount + ", but was: " + itemCount);
        assertThat(description.toString(), itemCount, is(expectedCount));
    }

    private void checkIsNull(NoMatchingViewException noViewException, StringDescription description) {
        if (noViewException != null) {
            description.appendText(String.format(
                    "' check could not be performed because view '%s' was not found.\n",
                    noViewException.getViewMatcherDescription()));
            throw noViewException;
        }
    }

    public static ViewAssertion withItemCount(int itemCount) {
        return new RecyclerViewItemsCountViewAssertion(itemCount);
    }
}
