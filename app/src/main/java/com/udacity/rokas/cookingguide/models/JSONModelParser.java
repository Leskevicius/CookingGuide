package com.udacity.rokas.cookingguide.models;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rokas on 7/1/17.
 */

public class JSONModelParser {

    private static final String TAG = JSONModelParser.class.getCanonicalName();

    /**
     * Reads a {@link String} formatted as JSON and parses it into a {@link List} of {@link RecipeModel}s.
     * Can return null.
     *
     * @param response in a form of a String.
     * @return list of {@link RecipeModel}s.
     */
    @Nullable
    public static List<RecipeModel> parseRecipeModels(String response) {
        if (response == null) {
            return null;
        }

        List<RecipeModel> recipeList = new ArrayList<>();
        try {
            JSONArray recipesJSONArray = new JSONArray(response);
            // loop through the array of recipes, building a RecipeModel
            for (int i = 0; i < recipesJSONArray.length(); i++) {

                RecipeModel recipe = new RecipeModel();
                JSONObject recipeJSONObject = recipesJSONArray.getJSONObject(i);
                recipe.setId(recipeJSONObject.getInt("id"));
                recipe.setName(recipeJSONObject.getString("name"));
                recipe.setServings(recipeJSONObject.getInt("servings"));
                recipe.setImage(recipeJSONObject.getString("image"));

                JSONArray ingredientJSONArray = recipeJSONObject.getJSONArray("ingredients");
                List<IngredientModel> ingredientList = new ArrayList<>();
                // loop through the array of ingredients
                for (int j = 0; j < ingredientJSONArray.length(); j++) {
                    IngredientModel ingredient = new IngredientModel();

                    JSONObject ingredientJSONObject = ingredientJSONArray.getJSONObject(j);
                    ingredient.setQuantity(ingredientJSONObject.getInt("quantity"));
                    ingredient.setMeasure(ingredientJSONObject.getString("measure"));
                    ingredient.setIngredient(ingredientJSONObject.getString("ingredient"));

                    ingredientList.add(ingredient);
                }

                recipe.setIngredientList(ingredientList);

                JSONArray stepJSONArray = recipeJSONObject.getJSONArray("steps");
                List<StepModel> stepList = new ArrayList<>();
                //loop through the array of steps
                for (int j = 0; j < stepJSONArray.length(); j++) {
                    StepModel step = new StepModel();

                    JSONObject stepJSONObject = stepJSONArray.getJSONObject(j);
                    step.setId(stepJSONObject.getInt("id"));
                    step.setShortDescription(stepJSONObject.getString("shortDescription"));
                    step.setDescription(stepJSONObject.getString("description"));
                    step.setVideoURL(stepJSONObject.getString("videoURL"));
                    step.setThumbnailURL(stepJSONObject.getString("thumbnailURL"));

                    stepList.add(step);
                }

                recipe.setStepList(stepList);

                // now that a recipe is constructed, add it to the list of recipes
                recipeList.add(recipe);
            }

        } catch (JSONException e) {
            Log.w(TAG, e.toString());
            return null;
        }

        return recipeList;
    }
}