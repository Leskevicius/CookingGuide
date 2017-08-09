package com.udacity.rokas.cookingguide;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void RecipeListFirstItemCorrect() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(new RecyclerViewMatcher(R.id.recipe_recycler_view).atPosition(0))
            .check(matches(hasDescendant(withText("Nutella Pie"))));

        onView(new RecyclerViewMatcher(R.id.recipe_recycler_view).atPosition(0))
            .check(matches(hasDescendant(withText("Servings: 8"))));

    }

    @Test
    public void RecipeListGetToDetailsView() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView = onView(
            allOf(withId(R.id.recipe_recycler_view),
                withParent(allOf(withId(R.id.recipe_list_fragment_container),
                    withParent(withId(R.id.recipe_fragment_container)))),
                isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
            allOf(withId(R.id.recipe_details_ingredients_title), withText("Ingredients"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                        0),
                    0),
                isDisplayed()));
        textView.check(matches(withText("Ingredients")));

        ViewInteraction textView2 = onView(
            allOf(withId(R.id.recipe_details_steps_title), withText("Steps"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                        0),
                    0),
                isDisplayed()));
        textView2.check(matches(withText("Steps")));

    }

    @Test
    public void RecipeTestApplicationTraversal() {
        ViewInteraction recyclerView = onView(
            allOf(withId(R.id.recipe_recycler_view),
                withParent(allOf(withId(R.id.recipe_list_fragment_container),
                    withParent(withId(R.id.recipe_fragment_container)))),
                isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatTextView = onView(
            allOf(withId(R.id.recipe_details_steps_description), withText("Recipe Introduction"), isDisplayed()));
        appCompatTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
            allOf(withId(R.id.recipe_step_next), withText(">")));
        appCompatButton.perform(scrollTo(), click());


        ViewInteraction appCompatButton2 = onView(
            allOf(withId(R.id.recipe_step_next), withText(">")));
        appCompatButton2.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
            allOf(withContentDescription("Navigate up"),
                withParent(allOf(withId(R.id.action_bar),
                    withParent(withId(R.id.action_bar_container)))),
                isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
            allOf(withContentDescription("Navigate up"),
                withParent(allOf(withId(R.id.action_bar),
                    withParent(withId(R.id.action_bar_container)))),
                isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction textView4 = onView(
            allOf(withText("Baking Time"),
                childAtPosition(
                    allOf(withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0)),
                    0),
                isDisplayed()));
        textView4.check(matches(withText("Baking Time")));
    }

    private static Matcher<View> childAtPosition(
        final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                    && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
