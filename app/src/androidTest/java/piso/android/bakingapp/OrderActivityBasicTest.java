package piso.android.bakingapp;

/**
 * Created by pisoo on 7/19/2017.
 */

import static android.content.Context.MODE_PRIVATE;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import piso.android.bakingapp.Screens.Detail.RecipeDetail;
import piso.android.bakingapp.Screens.Ingredient.IngredientsActivity;
import piso.android.bakingapp.Screens.Ingredient.IngredientsActivityFragment;

/**
 * This test demos a user clicking the decrement button and verifying that it properly decrease
 * the quantity the total cost.
 */

@RunWith(AndroidJUnit4.class)
public class OrderActivityBasicTest {

    @Rule
    public FragmentTestRule<IngredientsActivityFragment> mFragmentTestRule = new FragmentTestRule<>(IngredientsActivityFragment.class);

    @Test
    public void fragment_can_be_instantiated() {

        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);

        // Then use Espresso to test the Fragment
        onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));
    }}