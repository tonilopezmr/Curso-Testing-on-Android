package com.tonilopezmr.androidtesting.list.listview.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Matcher para ListView que te dice si esta ordenado o no informandote donde no se ha ordenado.
 *
 * @param <T>
 */
public class ListViewSortedMatcher<T> extends BoundedMatcher<Object, T> {

    List<T> originalList;
    List<T> sortedList;

    public ListViewSortedMatcher(Class<? extends T> expectedType) {
        super(expectedType);
        this.originalList = new ArrayList<>();
        this.sortedList = new ArrayList<>();
    }


    @Override
    protected boolean matchesSafely(T item) {
        originalList.add(item);
        sortedList.add(item);
        return true;
    }

    @Override
    public void describeTo(Description description) {
    }

    public Matcher<? super View> isSorted(final Comparator<T> comparator) {
        return new TypeSafeMatcher<View>() {

            private T expected;
            private T original;

            @Override
            protected boolean matchesSafely(View item) {
                boolean result = true;
                Collections.sort(sortedList, comparator);

                for (int i = 0; i < sortedList.size() && result; i++) {
                    T expected = sortedList.get(i);
                    T original = originalList.get(i);

                    this.expected = expected;
                    this.original = original;
                    result = original.equals(expected);
                }
                return result;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("the list is not sorted, expected: <" + expected + "> but was <"+ original+">");
            }
        };
    }
}
