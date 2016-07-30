/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tonilopezmr.androidtesting.list.recyclerview.matchers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RecyclerViewItemsCountMatcher extends TypeSafeMatcher<View> {

  private final int expectedItemCount;

  public RecyclerViewItemsCountMatcher(int expectedItemCount) {
    this.expectedItemCount = expectedItemCount;
  }

  @Override
  protected boolean matchesSafely(View item) {
    RecyclerView recyclerView = (RecyclerView) item;
    return recyclerView.getAdapter().getItemCount() == expectedItemCount;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("recycler view does not contains " + expectedItemCount + " items");
  }

  public static Matcher<View> recyclerViewHasItemCount(int itemCount) {
    return new RecyclerViewItemsCountMatcher(itemCount);
  }
}


