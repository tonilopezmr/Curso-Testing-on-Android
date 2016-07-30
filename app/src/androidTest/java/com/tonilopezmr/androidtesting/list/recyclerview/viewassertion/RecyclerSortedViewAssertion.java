package com.tonilopezmr.androidtesting.list.recyclerview.viewassertion;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.core.deps.guava.collect.Ordering;
import android.support.test.espresso.util.HumanReadables;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecyclerSortedViewAssertion<T extends Comparable> implements ViewAssertion {

    private List<T> sortedList = new ArrayList<>();
    private WithAdapter<T> withAdapter;

    public RecyclerSortedViewAssertion(WithAdapter<T> withAdapter) {
        checkNotNull(withAdapter);
        this.withAdapter = withAdapter;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        RecyclerView recyclerView = (RecyclerView) view;
        sortedList = withAdapter.itemsToSort(recyclerView);

        checkIsNotEmpty(view);

        if (!Ordering.natural().<T>isOrdered(sortedList)){
            throw (new PerformException.Builder())
                    .withActionDescription(toString())
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(new Exception("the list is not sorted "+ sortedList))
                    .build();
        }
    }

    private void checkIsNotEmpty(View view) {
        if (sortedList.isEmpty()) {
            throw (new PerformException.Builder())
                    .withActionDescription(toString())
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(new IllegalStateException("The list is empty"))
                    .build();
        }
    }

    public static <T extends Comparable> RecyclerSortedViewAssertion isSorted(WithAdapter<T> adapter) {
        return new RecyclerSortedViewAssertion<T>(adapter);
    }

    public interface WithAdapter<T> {
        List<T> itemsToSort(RecyclerView recyclerView);
    }
}
