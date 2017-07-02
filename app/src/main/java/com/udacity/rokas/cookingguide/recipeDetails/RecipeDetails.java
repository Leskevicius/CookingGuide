package com.udacity.rokas.cookingguide.recipeDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by rokas on 7/1/17.
 */

public class RecipeDetails extends Fragment {

    public static RecipeDetails newInstance(Bundle bundle) {
        RecipeDetails recipeDetails = new RecipeDetails();
        recipeDetails.setArguments(bundle);
        return recipeDetails;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
