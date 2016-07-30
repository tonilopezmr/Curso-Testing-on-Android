package com.tonilopezmr.androidtesting.list.listview.matchers;

import android.view.View;
import android.widget.ListView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher personalizado.
 *
 */
public class ListViewItemCounterMatcher extends TypeSafeMatcher<View> {

    private int itemSize;

    public ListViewItemCounterMatcher(int itemSize) {
        this.itemSize = itemSize;
    }

    @Override
    protected boolean matchesSafely(View item) {
        return ((ListView) item).getCount() == itemSize;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("list view does not contains " + itemSize + " items");
    }

    public static Matcher<View> withItemSize(int itemSize) {
        return new ListViewItemCounterMatcher(itemSize);
    }
}
