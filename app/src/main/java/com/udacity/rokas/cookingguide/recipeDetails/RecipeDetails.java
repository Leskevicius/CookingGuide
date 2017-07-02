package com.udacity.rokas.cookingguide.recipeDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by rokas on 7/1/17.
 */

public class RecipeDetails extends Fragment {

    public static RecipeDetails newInstance(Bundle bundle) {
        RecipeDetails recipeDetails = new RecipeDetails();
        recipeDetails.setArguments(bundle);
        return recipeDetails;
    }
}
