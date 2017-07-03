package com.udacity.rokas.cookingguide;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = RecipeListActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // start up the recipe list fragment
        if (savedInstanceState == null) {
            RecipeListFragment fragment = RecipeListFragment.newInstance(new Bundle());
            getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_list_container, fragment)
                .commit();
        }

    }

    public void setAppBarTitle(String title) {
        ActionBar appBar = getSupportActionBar();
        if (appBar != null) {
            appBar.setTitle(title);
        }
    }

    public void addFragment(Fragment fragment, @IdRes int containerView, String TAG, Fragment previousFragment) {
        getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            .add(containerView, fragment)
            .hide(previousFragment)
            .addToBackStack(TAG)
            .commit();
    }
}
