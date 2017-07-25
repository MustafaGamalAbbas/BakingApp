package piso.android.bakingapp;

import android.app.Fragment;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import junit.framework.Assert;

import piso.android.bakingapp.Screens.Ingredient.IngredientsActivity;

/**
 * Created by pisoo on 7/19/2017.
 */

public class FragmentTestRule<F extends Fragment> extends ActivityTestRule<IngredientsActivity> {

    private final Class<F> mFragmentClass;
    private F mFragment;

    public FragmentTestRule(final Class<F> fragmentClass) {
        super(IngredientsActivity.class, true, false);
        mFragmentClass = fragmentClass;
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();


    }
    public F getFragment(){
        return mFragment;
    }
}