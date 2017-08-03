package com.udacity.rokas.cookingguide.utilities;

import com.udacity.rokas.cookingguide.models.IngredientModel;
import com.udacity.rokas.cookingguide.models.StepModel;

import java.util.List;

/**
 * Created by rokas on 7/2/17.
 */

public class TextUtils {

    public static String ingredientListToString(List<IngredientModel> ingredients) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            sb.append("- ");
            sb.append(formatIngredient(ingredients.get(i).getIngredient(), ingredients.get(i).getQuantity(), ingredients.get(i).getMeasure()));
            if (i < ingredients.size() - 1) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }

    public static String getStepTitle(StepModel step) {
        StringBuilder sb = new StringBuilder();
        sb.append("Step ");
        sb.append(step.getId());
        return sb.toString();
    }

    private static String formatIngredient(String ingredientName, int quantity, String measure) {
        StringBuilder sb = new StringBuilder();
        sb.append(formatIngredientAmount(quantity, measure));
        sb.append(" of ");
        sb.append(ingredientName);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public static String formatIngredientAmount(int quantity, String measure) {
        StringBuilder sb = new StringBuilder();
        sb.append(quantity);
        sb.append(" ");
        sb.append(measure);
        return sb.toString();
    }
}
