package piso.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
 import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import piso.android.bakingapp.Screens.Detail.RecipeDetail;

/**
 * Created by pisoo on 7/21/2017.
 */


@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    @Rule
    public IntentsTestRule<Home> mActivityRule = new IntentsTestRule<>(
            Home.class);
    @Test
    public  void tst (){
        onView(withId(R.id.rv_recipeList)).check(matches(isDisplayed()));
    }

    @Test
    public void ClickToOpenRecipeDetails() {

        onView(withId(R.id.rv_recipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
         onView(withId(R.id.rv_Steps)).check(matches(isDisplayed()));
         intended(hasComponent(RecipeDetail.class.getName()));
         intended(hasExtraWithKey("recipe"));
    }
}
