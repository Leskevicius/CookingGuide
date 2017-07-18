package com.udacity.rokas.cookingguide;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.recipeDetails.RecipeDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/15/17.
 */

public class RecipeActivity extends AppCompatActivity {

    @BindView(R.id.recipe_details_fragment_container)
    FrameLayout detailsContainer;

    @BindView(R.id.recipe_step_fragment_container)
    FrameLayout stepContainer;

    private RecipeModel recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey(RecipeListFragment.RECIPE)) {
                recipe = savedInstanceState.getParcelable(RecipeListFragment.RECIPE);
            }
        }

        if (getSupportFragmentManager().findFragmentById(R.id.recipe_details_fragment_container) == null) {
            // start up the recipe list fragment
            Intent intent = getIntent();
            if (intent.hasExtra(RecipeListFragment.RECIPE)) {
                recipe = intent.getParcelableExtra(RecipeListFragment.RECIPE);
                setAppBarTitle(recipe.getName());
            }

            Bundle bundle = new Bundle();
            bundle.putParcelable(RecipeListFragment.RECIPE, recipe);

            RecipeDetailsFragment fragment = RecipeDetailsFragment.newInstance(bundle);

            getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_details_fragment_container, fragment)
                .commit();
        }

        String deviceType = getResources().getBoolean(R.bool.isTablet) ? "Tablet" : "Phone";
        Toast.makeText(this, deviceType, Toast.LENGTH_LONG).show();
    }

    public void setAppBarTitle(String title) {
        ActionBar appBar = getSupportActionBar();
        if (appBar != null) {
            appBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    finish();
                } else {
                    getSupportFragmentManager().popBackStack();
                    setAppBarTitle(recipe.getName());
                    showDetails();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment newFragment, @IdRes int containerId, String TAG, Fragment oldFragment) {
        getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(containerId, newFragment, TAG)
            .commit();
    }

    public void addFragment(Fragment newFragment, @IdRes int containerId, String TAG) {
        getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(containerId, newFragment, TAG)
            .addToBackStack(TAG)
            .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeListFragment.RECIPE, recipe);
    }

    public void showDetails() {
        detailsContainer.setVisibility(View.VISIBLE);
        stepContainer.setVisibility(View.GONE);
    }

    public void showStep() {
        detailsContainer.setVisibility(View.GONE);
        stepContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setAppBarTitle(recipe.getName());
        showDetails();
    }
}
