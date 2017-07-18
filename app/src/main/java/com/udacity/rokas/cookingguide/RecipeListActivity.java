package com.udacity.rokas.cookingguide;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.udacity.rokas.cookingguide.recipeDetails.RecipeDetailsFragment;
// TODO: handle images that don't exist
// TODO: ESPRESSO TESTS!
// TODO: WIDGET

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = RecipeListActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        setAppBarTitle(getString(R.string.recipe_list_app_bar_title));

        // start up the recipe list fragment
        if (savedInstanceState == null) {
            RecipeListFragment fragment = RecipeListFragment.newInstance(new Bundle());
            getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_fragment_container, fragment)
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

    public void setUpAppBarBackButton(boolean visible) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        }
    }

    public void popFragmentBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                popFragmentBackStack();

                getSupportFragmentManager().popBackStackImmediate(RecipeDetailsFragment.TAG, 0);

                // if we are at the list, hide the back button.
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    setUpAppBarBackButton(false);
                    setAppBarTitle(getString(R.string.recipe_list_app_bar_title));
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
